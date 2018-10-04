package microservices.book.gamificationservice.repository;

import microservices.book.gamificationservice.domain.LeaderBoardRow;
import microservices.book.gamificationservice.domain.ScoreCard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {

	//Get the total score for a given user, being the sum of the scores of all his ScoreCards
	@Query("SELECT SUM(s.score) FROM microservices.book.gamificationservice.domain.ScoreCard s WHERE s.userId=:userId GROUP BY s.userId")
	int getTotalScoreForUser(@Param("userId") Long userId);

	//Retrieve all the Scorecards for a given user, identified by his user id
	@Query("SELECT NEW microservices.book.gamificationservice.domain.LeaderBoardRow(s.userId, SUM(s.score))" + "FROM microservices.book.gamificationservice.domain.ScoreCard s "+ "GROUP BY s.userId ORDER BY SUM(s.score) DESC" )
	List<LeaderBoardRow> findLeaders(Pageable pageable);


	List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(Long userId);
}
