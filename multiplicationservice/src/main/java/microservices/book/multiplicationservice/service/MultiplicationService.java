package microservices.book.multiplicationservice.service;

import microservices.book.multiplicationservice.domain.Multiplication;
import microservices.book.multiplicationservice.domain.MultiplicationResultAttempt;

import java.util.List;

public interface MultiplicationService {
	Multiplication createRandomMultiplication();

	boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

	List<MultiplicationResultAttempt> getStatsForUser(String userAlias);
}
