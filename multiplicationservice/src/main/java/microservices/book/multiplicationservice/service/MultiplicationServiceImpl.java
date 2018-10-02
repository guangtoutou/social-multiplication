package microservices.book.multiplicationservice.service;

import microservices.book.multiplicationservice.domain.Multiplication;
import microservices.book.multiplicationservice.domain.MultiplicationResultAttempt;
import microservices.book.multiplicationservice.domain.User;
import microservices.book.multiplicationservice.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplicationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

	private RandomGeneratorService randomGeneratorService;
	private UserRepository userRepository;
	private MultiplicationResultAttemptRepository attemptRepository;

	@Autowired
	public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService, UserRepository userRepository, MultiplicationResultAttemptRepository attemptRepository) {
		this.randomGeneratorService = randomGeneratorService;
		this.userRepository = userRepository;
		this.attemptRepository = attemptRepository;
	}

	@Override
	public Multiplication createRandomMultiplication() {
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);
	}

	@Override
	public boolean checkAttempt(final MultiplicationResultAttempt attemptMultiplication) {
		int factorA = attemptMultiplication.getMultiplication().getFactorA();
		int factorB = attemptMultiplication.getMultiplication().getFactorB();
		int attempt = attemptMultiplication.getResultAttempt();

		boolean isCorrect = factorA * factorB == attempt;

		//if user alias exists, then use it. Otherwise, use the user from request
		Optional<User> user = userRepository.findByAlias(attemptMultiplication.getUser().getAlias());
		MultiplicationResultAttempt response = new MultiplicationResultAttempt(user.orElse(attemptMultiplication.getUser()), attemptMultiplication.getMultiplication(),attempt,isCorrect);

		attemptRepository.save(response);
		return isCorrect;
	}


}
