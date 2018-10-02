package microservices.book.multiplicationservice.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Multiplication {
	//Both factors
	private final int factorA;
	private final int factorB;

	//Empty constructor for JSON (de)serialization
	public Multiplication(){
		factorA=0;
		factorB=0;
	}

}
