package microservices.book.gamificationservice.repository;

import microservices.book.gamificationservice.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {
	List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(Long userId);
}
