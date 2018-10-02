package microservices.book.multiplicationservice.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class MultiplicationResultAttempt {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "MULTIPLICATION_ID")
	private Multiplication multiplication;

	private int resultAttempt;
	private boolean correct;

	//Empty constructor for JSON (de)serialization
	public MultiplicationResultAttempt() {
		user = null;
		multiplication = null;
		resultAttempt = -1;
		correct = false;
	}

	public MultiplicationResultAttempt(User user, Multiplication multiplication, int resultAttempt, boolean correct) {
		this.user = user;
		this.multiplication = multiplication;
		this.resultAttempt = resultAttempt;
		this.correct = correct;
	}
}
