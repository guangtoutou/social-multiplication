package microservices.book.multiplicationservice.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import microservices.book.multiplicationservice.domain.Multiplication;
import microservices.book.multiplicationservice.domain.MultiplicationResultAttempt;
import microservices.book.multiplicationservice.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/multiplication")
@RestController
public class MutiplicationController {

	private final MultiplicationService multiplicationService;

	@Autowired
	public MutiplicationController(MultiplicationService multiplicationService) {
		this.multiplicationService = multiplicationService;
	}

	@GetMapping("/random")
	public Multiplication getRandomMultiplicaiton() {
		return multiplicationService.createRandomMultiplication();
	}

	@PostMapping("/result")
	ResponseEntity<ResultResponse> attemptMultiplication(@RequestBody MultiplicationResultAttempt attempt) {
		return ResponseEntity.ok(new ResultResponse(multiplicationService.checkAttempt(attempt)));
	}

	@GetMapping("/result")
	ResponseEntity<List<MultiplicationResultAttempt>> getStat(@RequestParam("alias") String alias) {
		return ResponseEntity.ok(multiplicationService.getStatsForUser(alias));
	}

	@GetMapping("/result/{resultId}")
	ResponseEntity<MultiplicationResultAttempt> getAttempt(@PathVariable("resultId") Long resultId) {
		return ResponseEntity.ok(multiplicationService.getResultById(resultId));
	}

	@RequiredArgsConstructor
	@NoArgsConstructor(force = true)
	@Getter
	public static final class ResultResponse {
		private final boolean correct;
	}
}
