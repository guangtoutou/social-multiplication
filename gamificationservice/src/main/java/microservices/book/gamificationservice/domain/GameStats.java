package microservices.book.gamificationservice.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class GameStats {
	private Long userId;
	private int score;
	private List<Badge> badges;

	//Empty constructors for JSON/JPA
	public GameStats() {
		this.userId = null;
		this.score = 0;
		this.badges = new ArrayList<>();
	}

	public GameStats(Long userId, int score, List<Badge> badges) {
		this.userId = userId;
		this.score = score;
		this.badges = badges;
	}

	//Factory method to build an empty instance (0 points and no badges)
	public static GameStats emptyStats(Long userId){
		return new GameStats(userId,0, Collections.emptyList());
	}

}
