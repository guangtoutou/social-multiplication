package microservices.book.multiplicationservice.service;

import microservices.book.multiplicationservice.domain.Multiplication;
import microservices.book.multiplicationservice.domain.MultiplicationResultAttempt;
import microservices.book.multiplicationservice.domain.User;
import microservices.book.multiplicationservice.event.EventDispatcher;
import microservices.book.multiplicationservice.event.MultiplicationSolvedEvent;
import microservices.book.multiplicationservice.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplicationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

	private RandomGeneratorService randomGeneratorService;
	private UserRepository userRepository;
	private MultiplicationResultAttemptRepository attemptRepository;
	private EventDispatcher eventDispatcher;

	@Autowired
	public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService, UserRepository userRepository, MultiplicationResultAttemptRepository attemptRepository, EventDispatcher eventDispatcher) {
		this.randomGeneratorService = randomGeneratorService;
		this.userRepository = userRepository;
		this.attemptRepository = attemptRepository;
		this.eventDispatcher = eventDispatcher;
	}

	@Override
	public Multiplication createRandomMultiplication() {
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);
	}

	@Transactional
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

		eventDispatcher.send(new MultiplicationSolvedEvent(response.getId(),response.getUser().getId(),response.isCorrect()));

		return isCorrect;
	}

	@Override
	public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
		return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
	}

	@Override
	public MultiplicationResultAttempt getResultById(Long userId) {
		return attemptRepository.findById(userId).orElse(null);
	}


}
