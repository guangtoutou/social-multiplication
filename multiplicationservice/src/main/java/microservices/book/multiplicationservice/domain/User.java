package microservices.book.multiplicationservice.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private long id;

	@Column(unique = true)
	private String alias;

	//Empty constructor for JSON (de)serialization
	protected User(){
		alias = null;
	}

	public User(String alias) {
		this.alias = alias;
	}
}
