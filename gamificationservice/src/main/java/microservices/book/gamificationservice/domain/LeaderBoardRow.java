package microservices.book.gamificationservice.domain;

import lombok.Data;

@Data
public class LeaderBoardRow {

	private Long userId;
	private Long totalScore;

	//Empty constructor for JSON/JPA
	public LeaderBoardRow() {
		this.userId = 0L;
		this.totalScore = 0L;
	}

	public LeaderBoardRow(Long userId, Long totalScore) {
		this.userId = userId;
		this.totalScore = totalScore;
	}
}
