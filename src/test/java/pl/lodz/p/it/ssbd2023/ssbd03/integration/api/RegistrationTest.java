package pl.lodz.p.it.ssbd2023.ssbd03.integration.api;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import pl.lodz.p.it.ssbd2023.ssbd03.dto.request.CreateOwnerDTO;
import pl.lodz.p.it.ssbd2023.ssbd03.integration.config.BasicIntegrationConfigTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest extends BasicIntegrationConfigTest {
    private static final CreateOwnerDTO initalizedOwner = new CreateOwnerDTO(
            "Bartosz",
            "Miszczak",
            "miszczu2137",
            "mailik2111@fakemail.com",
            "Password$123",
            "Password$123",
            "PL",
            "997654321"
    );

    @BeforeClass
    public static void prepareTest() {
        int statusCode = sendRequestAndGetResponse(Method.POST, "/accounts/register", initalizedOwner, ContentType.JSON)
                .getStatusCode();
        assertEquals(201, statusCode);
        logger.info("DATA PREPARED!");
    }

    @Test
    public void registerOwnerTest() {
        CreateOwnerDTO owner = new CreateOwnerDTO(
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10) + "@fakemailik.com",
                "Password$123",
                "Password$123",
                "PL",
                RandomStringUtils.randomNumeric(9)
        );

        int statusCode = sendRequestAndGetResponse(Method.POST, "/accounts/register", owner, ContentType.JSON)
                .getStatusCode();
        assertEquals(201, statusCode);
        logger.info("PASSED!");
    }

    @Test
    public void validationDataTest() {
        Arrays.asList("firstName", "surname", "username", "email", "password", "language", "phoneNumber").forEach(field -> {
            int statusCode = sendRequestAndGetResponse(Method.POST, "/accounts/register", getInvalidOwnerData(field), ContentType.JSON)
                    .getStatusCode();
            assertEquals(400, statusCode, "Field: " + field + " validation.");
            logger.info("PASSED!");
        });
    }

    @Test
    public void tryCreateOwnerWithNotUniqueDataTest() {
        Arrays.asList("username", "email", "phoneNumber").forEach(field -> {
            int statusCode = sendRequestAndGetResponse(Method.POST, "/accounts/register", createNotUniqueOwner(field), ContentType.JSON)
                    .getStatusCode();
            assertEquals(409, statusCode, "Field: " + field + " unique validation.");
            logger.info("PASSED!");
        });
    }

    private CreateOwnerDTO getInvalidOwnerData(String field) {
        CreateOwnerDTO owner = new CreateOwnerDTO(
                initalizedOwner.getFirstName(),
                initalizedOwner.getSurname(),
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10) + "@fakemail.com",
                "Password$123",
                "Password$123",
                "PL",
                RandomStringUtils.randomNumeric(9));

        switch (field) {
            case "firstName" -> owner.setFirstName(RandomStringUtils.randomAlphanumeric(33));
            case "surname" -> owner.setSurname(RandomStringUtils.randomAlphanumeric(33));
            case "username" -> owner.setUsername(RandomStringUtils.randomAlphanumeric(20));
            case "email" -> owner.setEmail(RandomStringUtils.randomAlphanumeric(10));
            case "password" -> {
                owner.setPassword(RandomStringUtils.randomAlphanumeric(5));
                owner.setRepeatedPassword(RandomStringUtils.randomAlphanumeric(5));
            }
            case "language" -> owner.setLanguage("X");
            case "phoneNumber" -> owner.setPhoneNumber(RandomStringUtils.randomAlphanumeric(20));
        }

        return owner;
    }

    private CreateOwnerDTO createNotUniqueOwner(String field) {
        CreateOwnerDTO owner = new CreateOwnerDTO(
                initalizedOwner.getFirstName(),
                initalizedOwner.getSurname(),
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10) + "@fakemail.com",
                "Password$123",
                "Password$123",
                "PL",
                RandomStringUtils.randomNumeric(9));

        switch (field) {
            case "username" -> owner.setUsername(initalizedOwner.getUsername());
            case "email" -> owner.setEmail(initalizedOwner.getEmail());
            case "phoneNumber" -> owner.setPhoneNumber(initalizedOwner.getPhoneNumber());
        }

        return owner;
    }
}
