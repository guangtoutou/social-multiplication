package microservices.book.multiplicationservice.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MultiplicationResultAttempt {
	private final User user;
	private final Multiplication multiplication;
	private final int resultAttempt;

	//Empty constructor for JSON (de)serialization

	public MultiplicationResultAttempt() {
		user = null;
		multiplication = null;
		resultAttempt = -1;
	}
}
