package com.app.cameraIq;

import com.app.cameraIq.model.Organization;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {RestTemplate.class})
public class CreateOrganizationAPITest {

    @Autowired
    private RestTemplate restTemplate;

    private Faker faker = new Faker();

    private String baseUrl = "http://localhost:8080/orgs";


    @Test
    public void testCreateOrganizationHappyPath() {
        Organization request = createOrganization();
        ResponseEntity result = restTemplate.postForEntity(baseUrl, request, Organization.class);
        assertEquals(result.getStatusCode().value(), HTTP_CREATED);
        Organization response = (Organization)result.getBody();
        assertOrgsEqual(request, response);
    }

    @Test
    public void testCreateSameOrganizationTwice() {
        Organization request = createOrganization();
        restTemplate.postForEntity(baseUrl, request, Organization.class);
        try {
            ResponseEntity result = restTemplate.postForEntity(baseUrl, request, Organization.class);
        } catch (HttpClientErrorException ex) {
            assertEquals(ex.getRawStatusCode(), HTTP_BAD_REQUEST);
        }
    }

    @Test
    public void testCreateOrganizationRequiredName() {
        Organization request = createOrganization();
        request.setName(null);
        try {
            ResponseEntity result = restTemplate.postForEntity(baseUrl, request, Organization.class);
        } catch (HttpClientErrorException ex) {
            assertEquals(ex.getRawStatusCode(), HTTP_BAD_REQUEST);
        }
    }

    @Test
    public void testCreateOrganizationRequiredAddress() {
        Organization request = createOrganization();
        request.setAddress(null);
        ResponseEntity result = restTemplate.postForEntity(baseUrl, request, Organization.class);
        assertEquals(result.getStatusCode().value(), HTTP_CREATED);
        Organization response = (Organization)result.getBody();
        assertOrgsEqual(request, response);
    }

    @Test
    public void testCreateOrganizationRequiredPhone() {
        Organization request = createOrganization();
        request.setPhone(null);
        ResponseEntity result = restTemplate.postForEntity(baseUrl, request, Organization.class);
        assertEquals(result.getStatusCode().value(), HTTP_CREATED);
        Organization response = (Organization)result.getBody();
        assertOrgsEqual(request, response);
    }

    private Organization createOrganization() {
        Organization organization = new Organization();
        organization.setName(faker.name().firstName());
        organization.setAddress(faker.address().fullAddress());
        organization.setPhone(faker.phoneNumber().phoneNumber());
        return organization;
    }

    private void assertOrgsEqual(Organization org, Organization org2) {
        assertEquals(org.getName(), org2.getName());
        assertEquals(org.getAddress(), org2.getAddress());
        assertEquals(org.getPhone(), org2.getPhone());
    }

}
