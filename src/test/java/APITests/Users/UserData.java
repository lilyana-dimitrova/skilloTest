package APITests.Users;

import DTOs.ActionsPOJO;
import DTOs.BanPOJO;
import DTOs.LoginPOJO;
import DTOs.SignupPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class UserData {

    @BeforeClass
    public void setup() throws JsonProcessingException {
        RestAssured.baseURI = "http://training.skillo-bg.com:3100";
        RestAssured.basePath ="/users";
        createFakeUser();
    }

    static SignupPOJO user = new SignupPOJO();
    static LoginPOJO login = new LoginPOJO();
    static BanPOJO ban = new BanPOJO();
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


    @Test(testName = "should get user profile", priority = 2)
    public void getUserData() {

        Response userProfile = given()
                .pathParam("userId", user.getUserID())
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .when()
                .get(baseURI + basePath + "/{userId}");

        userProfile
                .then()
                .body("id", equalTo(user.getUserID()))
                .statusCode(200);
    }


    @Test(testName = "should ban the user", priority = 3)
    public void banUser() throws JsonProcessingException {

        ban.setBanned(true);
        ban.setDescription("for no good reason");
        String convertedBan = objectMapper.writeValueAsString(ban);
        System.out.println(convertedBan);

        Response banResponse = given()
                .pathParam("userId", user.getUserID())
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .body(convertedBan)
                .when()
                .put(baseURI + basePath + "/{userId}" + "/banstatus");

        banResponse
                .then()
                .statusCode(200);

        String banResponseBody = banResponse.getBody().asString();
        boolean banStatus = JsonPath.parse(banResponseBody).read("$.isBanned");
        System.out.println("Is banned: " + banStatus);

    }

    @Test(testName = "should follow a user", priority = 3)
    public void followUser() {
        ActionsPOJO followUser = new ActionsPOJO();
        followUser.setAction("followUser");

            given()
                .pathParam("userId", 2222)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(followUser)
                .when()
                .patch(baseURI + basePath + "/{userId}")
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
