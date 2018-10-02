package microservices.book.multiplicationservice.service;

import microservices.book.multiplicationservice.domain.Multiplication;
import microservices.book.multiplicationservice.domain.MultiplicationResultAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

	private RandomGeneratorService randomGeneratorService;

	@Autowired
	public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService) {
		this.randomGeneratorService = randomGeneratorService;
	}

	@Override
	public Multiplication createRandomMultiplication() {
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);
	}

	@Override
	public boolean checkAttempt(final MultiplicationResultAttempt resultAttempt) {
		return resultAttempt.getMultiplication().getFactorA() * resultAttempt.getMultiplication().getFactorB() == resultAttempt.getResultAttempt();
	}


}
