package microservices.book.multiplicationservice;

import microservices.book.multiplicationservice.domain.Multiplication;
import microservices.book.multiplicationservice.domain.MultiplicationResultAttempt;
import microservices.book.multiplicationservice.domain.User;
import microservices.book.multiplicationservice.event.EventDispatcher;
import microservices.book.multiplicationservice.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplicationservice.repository.UserRepository;
import microservices.book.multiplicationservice.service.MultiplicationServiceImpl;
import microservices.book.multiplicationservice.service.MultiplicationService;
import microservices.book.multiplicationservice.service.RandomGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


public class MulplicationServiceTest {

	@Mock
	private RandomGeneratorService randomGeneratorService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private MultiplicationResultAttemptRepository attemptRepository;

	private MultiplicationService multiplicationService;

	private EventDispatcher eventDispatcher;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		multiplicationService = new MultiplicationServiceImpl(randomGeneratorService, userRepository, attemptRepository, eventDispatcher);
	}

	@Test
	public void createRandomMultiplicationTest() {
		given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

		Multiplication multiplication = multiplicationService.createRandomMultiplication();

		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
	}

	@Test
	public void checkCorrectAttemptTest() {
		//given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("James Ni");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
		MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);
		given(userRepository.findByAlias("James Ni")).willReturn(Optional.empty());

		//when
		boolean attemptResult = multiplicationService.checkAttempt(attempt);

		//assert
		assertThat(attemptResult).isTrue();
		verify(attemptRepository).save(verifiedAttempt);
	}

	@Test
	public void checkWrongAttemptTest() {
		//given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("James Ni");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 2400, false);

		//when
		boolean attemptResult = multiplicationService.checkAttempt(attempt);

		//assert
		assertThat(attemptResult).isFalse();
	}
}
