package microservices.book.multiplicationservice.event;

import lombok.Data;

import java.io.Serializable;

@Data
public class MultiplicationSolvedEvent implements Serializable{
	private final Long multiplicationResultAttemptId;
	private final Long userId;
	private final boolean correct;
}
