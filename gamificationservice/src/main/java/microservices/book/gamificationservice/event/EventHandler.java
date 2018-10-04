package microservices.book.gamificationservice.event;

import lombok.extern.slf4j.Slf4j;
import microservices.book.gamificationservice.service.GameService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventHandler {
	private GameService gameService;

	@Autowired
	public EventHandler(GameService gameService) {
		this.gameService = gameService;
	}

	@RabbitListener(queues = "${multiplication.queue}")
	void handleMultiplicationSolved(MultiplicationSolvedEvent event) {
		log.info("Multiplication Solved Event received {}", event.getMultiplicationResultAttemptId());
		try {
			gameService.newAttemptForUser(event.getUserId(), event.getMultiplicationResultAttemptId(), event.isCorrect());
		} catch (Exception e) {
			log.error("Error when trying to process MultiplicationSolvedEvent", e);
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}
}
