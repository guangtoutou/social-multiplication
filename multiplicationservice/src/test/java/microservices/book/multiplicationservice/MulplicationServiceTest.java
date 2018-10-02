package microservices.book.multiplicationservice;

import microservices.book.multiplicationservice.domain.Multiplication;
import microservices.book.multiplicationservice.domain.MultiplicationResultAttempt;
import microservices.book.multiplicationservice.domain.User;
import microservices.book.multiplicationservice.service.MultiplicationServiceImpl;
import microservices.book.multiplicationservice.service.MultiplicationService;
import microservices.book.multiplicationservice.service.RandomGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


public class MulplicationServiceTest {

	@Mock
	private RandomGeneratorService randomGeneratorService;

	private MultiplicationService multiplicationService;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		multiplicationService = new MultiplicationServiceImpl(randomGeneratorService);
	}

	@Test
	public void createRandomMultiplicationTest(){
		given(randomGeneratorService.generateRandomFactor()).willReturn(50,30);

		Multiplication multiplication = multiplicationService.createRandomMultiplication();

		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
	}

	@Test
	public void checkCorrectAttemptTest(){
		//given
		Multiplication multiplication = new Multiplication(50,60);
		User user = new User("James Ni");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication,3000);

		//when
		boolean attemptResult = multiplicationService.checkAttempt(attempt);

		//assert
		assertThat(attemptResult).isTrue();
	}

	@Test
	public void checkWrongAttemptTest(){
		//given
		Multiplication multiplication = new Multiplication(50,60);
		User user = new User("James Ni");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication,2400);

		//when
		boolean attemptResult = multiplicationService.checkAttempt(attempt);

		//assert
		assertThat(attemptResult).isFalse();
	}
}
