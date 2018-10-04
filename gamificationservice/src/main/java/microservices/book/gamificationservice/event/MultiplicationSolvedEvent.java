package microservices.book.gamificationservice.event;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MultiplicationSolvedEvent implements Serializable {
	private Long multiplicationResultAttemptId;
	private Long userId;
	private boolean correct;
}
