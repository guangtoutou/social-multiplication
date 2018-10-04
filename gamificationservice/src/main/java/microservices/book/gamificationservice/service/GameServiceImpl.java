package microservices.book.gamificationservice.service;

import lombok.extern.slf4j.Slf4j;
import microservices.book.gamificationservice.domain.*;
import microservices.book.gamificationservice.repository.BadgeCardRepository;
import microservices.book.gamificationservice.repository.ScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

	private ScoreCardRepository scoreCardRepository;
	private BadgeCardRepository badgeCardRepository;

	@Autowired
	public GameServiceImpl(ScoreCardRepository scoreCardRepository, BadgeCardRepository badgeCardRepository) {
		this.scoreCardRepository = scoreCardRepository;
		this.badgeCardRepository = badgeCardRepository;
	}

	@Override
	public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {
		if (correct) {
			ScoreCard scoreCard = new ScoreCard(userId, attemptId);
			scoreCardRepository.save(scoreCard);
			log.info("User with id {} scored {} points for attempt id {}", userId, scoreCard.getScore(), attemptId);

			List<BadgeCard> badgeCards = processForBadges(userId, attemptId);
			return new GameStats(userId, scoreCard.getScore(), badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));

		}
		return null;
	}

	@Override
	public GameStats retrieveStatesForUser(Long userId) {
		int score = scoreCardRepository.getTotalScoreForUser(userId);
		List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
		return new GameStats(userId, score, badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
	}

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}

	/*Checks the total score and the different score cards obtained to give new badges in case their conditions are met
	score 100 --> Bronze Badge
	score 500 --> Silver Badge
	score 1000 --> Gold Badge
	first attempt --> Freshman Badge
	first correct attempt -->
	 */
	private List<BadgeCard> processForBadges(Long userId, Long attemptId) {

		int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
		log.info("New score for user {} is {}", userId, totalScore);

		List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
		List<ScoreCard> scoreCards = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
		if (scoreCards.size() == 1 && !containBadges(badgeCards, Badge.FIRST_WON)) {
			badgeCards.add(giveBadgeToUser(Badge.FIRST_WON, userId));
		}

		//Badges depending on score
		checkAndGiveBadgeBasedOnScore(badgeCards, totalScore, userId).ifPresent(badgeCards::add);

		return badgeCards;
	}

	private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(List<BadgeCard> badgeCards, int totalScore, Long userId) {
		if (totalScore > 100 && !containBadges(badgeCards, Badge.BRONZE_MULTIPLICATOR)) {
			return Optional.of(giveBadgeToUser(Badge.BRONZE_MULTIPLICATOR, userId));
		}
		if (totalScore > 200 && !containBadges(badgeCards, Badge.SILVER_MULTIPLICATOR)) {
			return Optional.of(giveBadgeToUser(Badge.SILVER_MULTIPLICATOR, userId));
		}
		if (totalScore > 300 && !containBadges(badgeCards, Badge.GOLD_MULTIPLICATOR)) {
			return Optional.of(giveBadgeToUser(Badge.GOLD_MULTIPLICATOR, userId));
		}
		return Optional.empty();
	}

	private BadgeCard giveBadgeToUser(Badge badge, Long userId) {
		BadgeCard badgeCard = new BadgeCard(userId, badge);
		badgeCardRepository.save(badgeCard);
		return badgeCard;
	}

	private boolean containBadges(List<BadgeCard> badgeCards, Badge badge) {
		return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));

	}
}
