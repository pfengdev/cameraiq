package com.example.cameraIq;

import com.example.cameraIq.model.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {RestTemplate.class})
public class UserControllerTest {

	@Autowired
	private RestTemplate restTemplate;

	private Faker faker;
	
	private String baseUrl = "http://localhost:8080/users";

	public void beforeClass() {
		faker = new Faker();
	}


	@Test
	public void testCreateUserHappyPath() {
	    User request = createUser();
	    ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
		assertEquals(result.getStatusCode(), HTTP_CREATED);
		User response = (User)result.getBody();
		assertEquals(request, response);
	}

	@Test
	public void testCreateSameUserTwice() {
		User user = createUser();
		restTemplate.postForEntity(baseUrl, user, User.class);
		ResponseEntity result = restTemplate.postForEntity(baseUrl, user, User.class);
//		assertEquals(result.getStatusCode(), HTTP_BAD_REQUEST);
	}

	@Test
	public void testCreateUserForDifferentOrg() {

	}

	@Test
	public void testCreateUserNullFirstName() {
		User request = createUser();
		request.setFirstName(null);
		restTemplate.postForEntity(baseUrl, request, User.class);
	}

	@Test
	public void testCreateUserEmptyFirstName() {
		User request = createUser();
		request.setFirstName("");
		restTemplate.postForEntity(baseUrl, request, User.class);
	}

	@Test
	public void testCreateUserNullLastName() {
		User request = createUser();
		request.setLastName(null);
		restTemplate.postForEntity(baseUrl, request, User.class);
	}

	@Test
	public void testCreateUserEmptyLastName() {
		User request = createUser();
		request.setLastName("");
		restTemplate.postForEntity(baseUrl, request, User.class);
	}

	@Test
	public void testCreateUserNullEmail() {
		User request = createUser();
		request.setEmail(null);
		restTemplate.postForEntity(baseUrl, request, User.class);
	}

	@Test
	public void testCreateUserEmptyEmail() {
		User request = createUser();
		request.setEmail("");
		restTemplate.postForEntity(baseUrl, request, User.class);
	}

	@Test
	public void testCreateUserNullAddress() {
		User request = createUser();
		request.setAddress(null);
		restTemplate.postForEntity(baseUrl, request, User.class);
	}

	@Test
	public void testCreateUserEmptyAddress() {
		User request = createUser();
		request.setAddress("");
		restTemplate.postForEntity(baseUrl, request, User.class);
	}

	@Test
	public void testCreateUserNullPhone() {
		User request = createUser();
		request.setPhone(null);
		restTemplate.postForEntity(baseUrl, request, User.class);
	}

	@Test
	public void testCreateUserEmptyPhone() {
		User request = createUser();
		request.setPhone("");
		restTemplate.postForEntity(baseUrl, request, User.class);
	}

	@Test
	public void testInvalidPhoneNumberFormat() {

	}

	private User createUser() {
		User user = new User();
		faker = new Faker();
		user.setFirstName(faker.name().firstName());
		user.setEmail(faker.internet().emailAddress());
		return user;
	}

}
