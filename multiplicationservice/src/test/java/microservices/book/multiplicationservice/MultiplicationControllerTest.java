package microservices.book.multiplicationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.multiplicationservice.controller.MutiplicationController;
import microservices.book.multiplicationservice.controller.MutiplicationController.ResultResponse;

import microservices.book.multiplicationservice.domain.Multiplication;
import microservices.book.multiplicationservice.domain.MultiplicationResultAttempt;
import microservices.book.multiplicationservice.domain.User;
import microservices.book.multiplicationservice.service.MultiplicationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(MutiplicationController.class)
public class MultiplicationControllerTest {
	@MockBean
	private MultiplicationService multiplicationService;

	@Autowired
	private MockMvc mvc;

	private JacksonTester<Multiplication> json;

	private JacksonTester<MultiplicationResultAttempt> jsonAttempt;

	private JacksonTester<ResultResponse> jsonResponse;


	@Before
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void getRandomMultiplicationTest() throws Exception {
		//given
		given(multiplicationService.createRandomMultiplication()).willReturn(new Multiplication(70, 20, 1400));

		//when
		MockHttpServletResponse response = mvc.perform(get("/multiplication/random").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(json.write(new Multiplication(70, 20, 1400)).getJson());
	}

	@Test
	public void correctMultiplicationAttemptTest() throws Exception {
		//given
		given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class))).willReturn(true);
		User user = new User ("James Ni");
		Multiplication multiplication = new Multiplication(50,70,3500);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication,3500);

		//when
		MockHttpServletResponse response = mvc.perform(post("/multiplication/result").contentType(MediaType.APPLICATION_JSON).content(jsonAttempt.write(attempt).getJson())).andReturn().getResponse();

		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonResponse.write(new ResultResponse(true)).getJson());
	}

}
