package microservices.book.gamificationservice.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ScoreCard {

	public static final int DEFAULT_SCORE = 10;

	@Id
	@GeneratedValue
	@Column(name = "CARD_ID")
	private Long cardId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "ATTEMPT_ID")
	private Long attemptId;

	@Column(name = "SCORE_TS")
	private long scoreTimestamp;

	@Column(name = "SCORE")
	private int score;

	//Empty constructor for JSON/JPA
	public ScoreCard() {
		this.cardId = null;
		this.userId = null;
		this.attemptId = null;
		this.scoreTimestamp = 0;
		this.score = 0;
	}

	public ScoreCard(Long userId, Long attemptId) {
		this.userId = userId;
		this.attemptId = attemptId;
		this.scoreTimestamp = System.currentTimeMillis();
		this.score = DEFAULT_SCORE;
	}
}
