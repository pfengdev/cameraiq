package com.app.cameraIq;

import com.app.cameraIq.model.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {RestTemplate.class})
public class CreateUserAPITest {

	@Autowired
	private RestTemplate restTemplate;

	private Faker faker = new Faker();
	
	private String baseUrl = "http://localhost:8080/users/registration";

	@Test
	public void testCreateUserHappyPath() {
	    User request = createUser();
	    ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
		assertEquals(result.getStatusCode().value(), HTTP_CREATED);
		User response = (User)result.getBody();
		assertUsersEqual(request, response);
	}

	@Test
	public void testCreateSameUserTwice() {
		User request = createUser();
		restTemplate.postForEntity(baseUrl, request, User.class);
        try {
            ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
        } catch (HttpClientErrorException ex) {
            assertEquals(ex.getRawStatusCode(), HTTP_BAD_REQUEST);
        }
	}

	@Test
	public void testCreateUserRequiredFirstName() {
		User request = createUser();
		request.setFirstName(null);
        try {
            ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
        } catch (HttpClientErrorException ex) {
            assertEquals(ex.getRawStatusCode(), HTTP_BAD_REQUEST);
        }
	}

	@Test
	public void testCreateUserRequiredLastName() {
		User request = createUser();
		request.setLastName(null);
        try {
            ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
        } catch (HttpClientErrorException ex) {
            assertEquals(ex.getRawStatusCode(), HTTP_BAD_REQUEST);
        }
	}

	@Test
	public void testCreateUserRequiredEmail() {
		User request = createUser();
		request.setEmail(null);
        try {
            ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
        } catch (HttpClientErrorException ex) {
            assertEquals(ex.getRawStatusCode(), HTTP_BAD_REQUEST);
        }
	}

	@Test
	public void testCreateUserNotRequiredAddress() {
		User request = createUser();
		request.setAddress(null);
		ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
		assertEquals(result.getStatusCode().value(), HTTP_CREATED);
		User response = (User)result.getBody();
		assertUsersEqual(request, response);
	}

	@Test
	public void testCreateUserNotRequiredPhone() {
		User request = createUser();
		request.setPhone(null);
		ResponseEntity result = restTemplate.postForEntity(baseUrl, request, User.class);
		assertEquals(result.getStatusCode().value(), HTTP_CREATED);
		User response = (User)result.getBody();
		assertUsersEqual(request, response);
	}

	private User createUser() {
		User user = new User();
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setEmail(faker.internet().emailAddress());
		user.setAddress(faker.address().fullAddress());
		user.setPhone(faker.phoneNumber().phoneNumber());
		return user;
	}

	private void assertUsersEqual(User user, User user2) {
		assertEquals(user.getFirstName(), user2.getFirstName());
		assertEquals(user.getLastName(), user2.getLastName());
		assertEquals(user.getEmail(), user2.getEmail());
		assertEquals(user.getAddress(), user2.getAddress());
		assertEquals(user.getPhone(), user2.getPhone());
	}

}
