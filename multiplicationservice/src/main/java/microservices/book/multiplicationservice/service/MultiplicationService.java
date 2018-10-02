package microservices.book.multiplicationservice.service;

import microservices.book.multiplicationservice.domain.Multiplication;
import microservices.book.multiplicationservice.domain.MultiplicationResultAttempt;

public interface MultiplicationService {
	Multiplication createRandomMultiplication();

	boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);
}
