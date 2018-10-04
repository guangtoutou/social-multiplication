package microservices.book.gamificationservice.service;

import microservices.book.gamificationservice.domain.GameStats;
import microservices.book.gamificationservice.domain.LeaderBoardRow;

import java.util.List;

public interface GameService {

	//Process a new attempt from a given user
	GameStats newAttemptForUser (Long userId,Long attemptId, boolean correct);

	//Get the game statistics for a given user
	GameStats retrieveStatesForUser(Long userId);

	//Get the most updated LeaderBoard with top score users
	List<LeaderBoardRow> getCurrentLeaderBoard();
}
