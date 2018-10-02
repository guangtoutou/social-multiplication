package microservices.book.multiplicationservice.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {

	private final String alias;

	//Empty constructor for JSON (de)serialization
	protected User(){
		alias = null;
	}
}
