package microservices.book.multiplicationservice.service;

public interface RandomGeneratorService {
	/**
	 * @return a random-generated factor. It's always a number between 11 and 99
	 */
	int generateRandomFactor();
}
