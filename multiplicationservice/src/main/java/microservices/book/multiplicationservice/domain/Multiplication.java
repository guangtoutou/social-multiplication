package microservices.book.multiplicationservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
public class Multiplication {

	@Id
	@GeneratedValue
	@Column(name = "MULTIPLICATION_ID")
	private long id;

	//Both factors
	private int factorA;
	private int factorB;

	//Empty constructor for JSON (de)serialization
	public Multiplication() {
		factorA = 0;
		factorB = 0;
	}

	public Multiplication(int factorA, int factorB) {
		this.factorA = factorA;
		this.factorB = factorB;
	}
}
