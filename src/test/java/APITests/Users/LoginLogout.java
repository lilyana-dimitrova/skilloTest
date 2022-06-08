package APITests.Users;

import DTOs.APIDTOs.LoginPOJO;
import DTOs.APIDTOs.SignupPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class LoginLogout {

    @BeforeClass
    public void setup() throws JsonProcessingException {
        RestAssured.baseURI = "http://training.skillo-bg.com:3100";
        RestAssured.basePath ="/users";
        createFakeUser();
    }

    static SignupPOJO user = new SignupPOJO();
    static LoginPOJO login = new LoginPOJO();
    static String authToken;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test(testName = "should log in", priority = 1)
    public void login() throws JsonProcessingException {

        login.setUsernameOrEmail(user.getUsername());
        login.setPassword(user.getPassword());
        String convertedJson = objectMapper.writeValueAsString(login);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(convertedJson)
                .when()
                .post( baseURI + basePath + "/login");

        // convert body json to string
        String loginResponseBody = response.getBody().asString();
        authToken = JsonPath.parse(loginResponseBody).read("$.token");
        System.out.println("Saved token is: " + authToken);

        response
                .then()
                .statusCode(201);

    }

    @Test(testName = "should fail log in with wrong password", priority = 1)
    public void loginInvalidPass() throws JsonProcessingException {

        login.setUsernameOrEmail(user.getUsername());
        login.setPassword("123456");
        String convertedJson = objectMapper.writeValueAsString(login);

        given()
                .header("Content-Type", "application/json")
                .body(convertedJson)
                .when()
                .post( baseURI + basePath + "/login")
                .then()
                .statusCode(400);
    }

    @Test(testName = "should fail log in with wrong username", priority = 1)
    public void loginInvalidUser() throws JsonProcessingException {

        login.setUsernameOrEmail("nonexistinguser456");
        login.setPassword(user.getPassword());
        String convertedJson = objectMapper.writeValueAsString(login);

        given()
                .header("Content-Type", "application/json")
                .body(convertedJson)
                .when()
                .post( baseURI + basePath + "/login")
                .then()
                .statusCode(400);
    }


    @Test(testName = "should logout", priority = 2)
    public void logOut() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .when()
                .delete( baseURI + basePath + "/logout");
        response
                .then()
                .statusCode(200);
    }

    @AfterClass(description = "should clean up fake user")
    public void deleteUser() throws JsonProcessingException {

        // log in with the newly created user to get a token in order to delete
        login.setUsernameOrEmail(user.getUsername());
        login.setPassword(user.getPassword());
        String convertedJson = objectMapper.writeValueAsString(login);

        Response loginResponse = given()
                .header("Content-Type", "application/json")
                .body(convertedJson)
                .when()
                .post(baseURI + basePath + "/login");

        // get token
        String loginResponseBody = loginResponse.getBody().asString();
        authToken = JsonPath.parse(loginResponseBody).read("$.token");
        System.out.println("Token: " + authToken);
        loginResponse
                .then()
                .statusCode(201);

        // delete the user
        Response deleteResponse = given()
                .pathParam("userId", user.getUserID())
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .when()
                .delete(baseURI + basePath + "/{userId}");

        deleteResponse
                .then()
                .statusCode(200);
    }

    // Helper method for setup
    public void createFakeUser() throws JsonProcessingException {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(user))
                .when()
                .post(baseURI + basePath);
        response
                .then()
                .statusCode(201);

        String loginResponseBody = response.getBody().asString();
        int userID = JsonPath.parse(loginResponseBody).read("$.id");
        user.setUserID(userID);
        String profilePicURl = JsonPath.parse(loginResponseBody).read("$.profilePicUrl");
        user.setProfilePicUrl(profilePicURl);

    }

}
