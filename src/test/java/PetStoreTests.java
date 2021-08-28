import com.google.gson.JsonParser;
import endpoints.EndPoints;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.swagger.petstore.ApiResponse;
import io.swagger.petstore.Order;
import io.swagger.petstore.User;
import org.apache.http.HttpStatus;

import org.junit.jupiter.api.*;
import utils.api.Specification;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.junit.jupiter.api.Assertions.*;
import static schemas.Schemas.*;
import static utils.api.JsonConverter.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests for https://petstore.swagger.io/")
public class PetStoreTests extends SetupPetStoreTests {

    private static final RequestSpecification SPECIFICATION = Specification.getRequestSpecification();

    @Test
    @DisplayName("Get request with status code")
    void getRequestStatusCodeTest() {

        String statusParams = "status=sold";

        given().spec(SPECIFICATION)
                .param(statusParams)
                .when()
                .get(EndPoints.PET + EndPoints.STATUS)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Get request matching response body")
    void getRequestMatchingResponseBodyTest() {

        User user = new User();
        user.setUsername("string");

        given().spec(SPECIFICATION)
                .when()
                .get(EndPoints.USER + "/" + user.getUsername())
                .then()
                .assertThat()
                .body(matchesJsonSchema(USER_JSON_SCHEMA));
    }

    @Test
    @DisplayName("Post request")
    void postRequestTest() {

        Order order = new Order(53, 17, 3,
                "2021-08-21T14:24:48.819Z", "placed", true);

        given().spec(SPECIFICATION)
                .body(convertObjectToJson(order))
                .when()
                .post(EndPoints.STORE + EndPoints.ORDER)
                .then()
                .assertThat()
                .body(matchesJsonSchema(ORDER_JSON_SCHEMA));
    }

    @Test
    @DisplayName("Put request")
    void putRequestTest() {

        ApiResponse apiResponse = new ApiResponse();
        User user = new User(8, "DougNewman", "Doug", "Newman",
                "dNewman@mail.com", "password", "+375290000000", 0);

        Response response = given().spec(SPECIFICATION)
                .body(convertObjectToJson(user))
                .when()
                .put(EndPoints.USER + "/" + user.getUsername());

        String responseBody = response.getBody().asString();

        apiResponse.setCode(JsonParser.parseString(responseBody).getAsJsonObject().get("code").getAsInt());

        int expectedCode = 200;
        int actualCode = apiResponse.getCode();

        assertAll("Should return response data",
                () -> response
                        .then()
                        .assertThat()
                        .body(matchesJsonSchema(API_RESPONSE_JSON_SCHEMA)),
                () -> assertEquals(expectedCode, actualCode, "Put request error."));
    }

    @Test
    @DisplayName("Delete request")
    void deleteRequestTest() {

        ApiResponse apiResponse = new ApiResponse();
        User user = new User();
        user.setUsername("string");

        Response response = given().spec(SPECIFICATION)
                .when()
                .delete(EndPoints.USER + "/" + user.getUsername());

        String responseBody = response.getBody().asString();

        apiResponse.setMessage(JsonParser.parseString(responseBody).getAsJsonObject().get("message").getAsString());

        String expectedMessage = user.getUsername();
        String actualMessage = apiResponse.getMessage();

        assertAll("Should return response data",
                () -> response
                        .then()
                        .assertThat()
                        .body(matchesJsonSchema(API_RESPONSE_JSON_SCHEMA)),
                () -> assertEquals(expectedMessage, actualMessage, "Delete request error."));
    }
}
