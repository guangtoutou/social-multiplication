package microservices.book.gamificationservice.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class BadgeCard {
	@Id
	@GeneratedValue
	@Column(name = "BADGE_ID")
	private Long badgeId;

	private Long userId;
	private long badgeTimestamp;
	private Badge badge;

	//Empty constructor for JSON/JPA


	public BadgeCard() {
		this.badgeId = null;
		this.userId = null;
		this.badgeTimestamp = 0;
		this.badge = null;
	}

	public BadgeCard(Long userId, Badge badge) {
		this.userId = userId;
		this.badgeTimestamp = System.currentTimeMillis();
		this.badge = badge;
	}
}
