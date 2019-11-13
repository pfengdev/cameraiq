package com.app.cameraIq;

import com.app.cameraIq.model.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.web.client.RestTemplate;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {RestTemplate.class})
public class UserAPITest {

	@Autowired
	private RestTemplate restTemplate;

	private Faker faker;
	
	private String baseUrl = "http://localhost:8080/users/registration";

	public void beforeClass() {
		faker = new Faker();
	}

	@BeforeTestClass
	public void beforeAll() {
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
		assertEquals(result.getStatusCode(), HTTP_BAD_REQUEST);
	}

	@Test
	public void testCreateUserNull() {
		User request = null;
		ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
		assertEquals(result.getStatusCode(), HTTP_BAD_REQUEST);
	}

	@Test
	public void testCreateUserRequiredFirstName() {
		User request = createUser();
		request.setFirstName(null);
		ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
		assertEquals(result.getStatusCode(), HTTP_BAD_REQUEST);
	}

	@Test
	public void testCreateUserRequiredLastName() {
		User request = createUser();
		request.setLastName(null);
		ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
		assertEquals(result.getStatusCode(), HTTP_BAD_REQUEST);
	}

	@Test
	public void testCreateUserRequiredEmail() {
		User request = createUser();
		request.setEmail(null);
		ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
		assertEquals(result.getStatusCode(), HTTP_BAD_REQUEST);
	}

	@Test
	public void testCreateUserNotRequiredAddress() {
		User request = createUser();
		request.setAddress(null);
		restTemplate.postForEntity(baseUrl, request, User.class);
		ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
		assertEquals(result.getStatusCode(), HTTP_CREATED);
		User response = (User)result.getBody();
		assertEquals(request, response);
	}

	@Test
	public void testCreateUserNotRequiredPhone() {
		User request = createUser();
		request.setPhone(null);
		ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
		assertEquals(result.getStatusCode(), HTTP_CREATED);
		User response = (User)result.getBody();
		assertEquals(request, response);
	}

	private User createUser() {
		User user = new User();
		user.setFirstName(faker.name().firstName());
		user.setEmail(faker.internet().emailAddress());
		return user;
	}

}
