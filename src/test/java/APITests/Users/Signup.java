package APITests.Users;

import DTOs.APIDTOs.LoginPOJO;
import DTOs.APIDTOs.SignupPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

public class Signup {

    @BeforeClass
    public void setup() {
        // Setting BaseURI once
        RestAssured.baseURI = "http://training.skillo-bg.com:3100";
        // Setting BasePath once
        RestAssured.basePath ="/users";
    }

    static SignupPOJO user = new SignupPOJO();
    static LoginPOJO login = new LoginPOJO();
    static String authToken;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test(testName = "should sign up with a faker account")
    public void signUp() throws JsonProcessingException {
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

    @Test(testName = "should fail sign up with an existing  account")
    public void signUpAgain() throws JsonProcessingException {

        Response response = given()
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(user))
                .when()
                .post(baseURI + basePath);
        response
                .then()
                .statusCode(400);
    }


    // cleanup purposes only
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


}
