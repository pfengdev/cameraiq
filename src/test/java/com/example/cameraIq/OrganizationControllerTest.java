package com.example.cameraIq;

import com.example.cameraIq.model.Organization;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {RestTemplate.class})
public class OrganizationControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    private Faker faker;

    private String baseUrl = "http://localhost:8080/orgs";

    public void beforeClass() {
        faker = new Faker();
    }


    @Test
    public void testCreateOrganizationHappyPath() {
        Organization request = createOrganization();
        ResponseEntity result = restTemplate.postForEntity(baseUrl, request, Organization.class);
        assertEquals(result.getStatusCode(), HTTP_CREATED);
        Organization response = (Organization)result.getBody();
//        assertEquals(request, response);
    }

    @Test
    public void testCreateSameOrganizationTwice() {
        Organization organization = createOrganization();
        restTemplate.postForEntity(baseUrl, organization, Organization.class);
        ResponseEntity result = restTemplate.postForEntity(baseUrl, organization, Organization.class);
//        assertEquals(result.getStatusCode(), HTTP_BAD_REQUEST);
    }

    @Test
    public void testCreateOrganizationForDifferentOrg() {

    }

    @Test
    public void testCreateOrganizationRequiredName() {
        Organization request = createOrganization();
        request.setName(null);
        restTemplate.postForEntity(baseUrl, request, Organization.class);
    }

    @Test
    public void testCreateOrganizationRequiredAddress() {
        Organization request = createOrganization();
        request.setAddress(null);
        restTemplate.postForEntity(baseUrl, request, Organization.class);
    }

    @Test
    public void testCreateOrganizationRequiredPhone() {
        Organization request = createOrganization();
        request.setPhone(null);
        restTemplate.postForEntity(baseUrl, request, Organization.class);
    }

    @Test
    public void testInvalidPhoneNumberFormat() {

    }

    private Organization createOrganization() {
        Organization organization = new Organization();
        faker = new Faker();
        organization.setName(faker.name().firstName());
        return organization;
    }

}
