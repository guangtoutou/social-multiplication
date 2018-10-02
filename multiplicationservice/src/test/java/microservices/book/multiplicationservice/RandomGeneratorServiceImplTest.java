package microservices.book.multiplicationservice;

import microservices.book.multiplicationservice.service.RandomGeneratorService;
import microservices.book.multiplicationservice.service.RandomGeneratorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomGeneratorServiceImplTest {

	private RandomGeneratorService randomGeneratorService;

	@Before
	public void setUp(){
		randomGeneratorService = new RandomGeneratorServiceImpl();
	}

	@Test
	public void createRandomMultiplicationIsBetweenExpectedLimit() {
		List<Integer> randomFactors = IntStream.range(0, 1000).map(i -> randomGeneratorService.generateRandomFactor()).boxed().collect(Collectors.toList());

		assertThat(randomFactors).containsOnlyElementsOf(IntStream.range(11, 100).boxed().collect(Collectors.toList()));
	}
}
