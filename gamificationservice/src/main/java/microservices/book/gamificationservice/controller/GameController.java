package microservices.book.gamificationservice.controller;

import microservices.book.gamificationservice.domain.GameStats;
import microservices.book.gamificationservice.domain.LeaderBoardRow;
import microservices.book.gamificationservice.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {
	private final GameService gameService;

	@Autowired
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@GetMapping("/stats")
	public GameStats getStatesForUser(@RequestParam("userId") Long userId){
		return gameService.retrieveStatesForUser(userId);
	}

	@GetMapping("/leaderboard")
	public List<LeaderBoardRow> getLeaderBoard(){
		return gameService.getCurrentLeaderBoard();
	}
}
