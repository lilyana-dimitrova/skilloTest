package APITests.Posts;

import DTOs.APIDTOs.LoginPOJO;
import DTOs.APIDTOs.PostPOJO;
import DTOs.APIDTOs.SignupPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Posting {

    @BeforeClass
    public void setup() throws JsonProcessingException {
        RestAssured.baseURI = "http://training.skillo-bg.com:3100";
        RestAssured.basePath ="/users";
        createFakeUser();
        //createFakePost();
    }

    static SignupPOJO user = new SignupPOJO();
    static LoginPOJO login = new LoginPOJO();
    //static ActionsPOJO post = new ActionsPOJO();
   // static PostPOJO post = createFakePost();
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

    //http://training.skillo-bg.com:3100/users/2856/posts?postStatus=public&take=5&skip=0
    @Test(testName = "should get 0 user posts", priority = 2)
    public void get0Posts() {
        ///users/{userId}/posts
        Response postsResponse = given()
                .queryParam("postStatus", "public")
                .queryParam("take", 5)
                .queryParam("skip", 0)
                .header("Content-Type", "application/json")
                .pathParam("userId", user.getUserID())
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get(baseURI + basePath + "/{userId}" + "/posts" );

        postsResponse
                .then()
                .body("", Matchers.empty())
                .statusCode(200);
    }

    @Test(testName = "should make a post", priority = 3)
    public void makeAPost() {
        PostPOJO post = new PostPOJO("My new post", "\"https://i.imgur.com/HUdmONP.jpg", "public");
       // String convertedPost = objectMapper.writeValueAsString(post);


        Response postsResponse = given()

                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(post)
                .when()
                .post(baseURI + "/posts" );

        postsResponse
                .then()
                .statusCode(201);
    }

    @Test(testName = "should get 1 user post", priority = 4)
    public void get1Post() {
        ///users/{userId}/posts
        Response postsResponse = given()
                .queryParam("postStatus", "public")
                .queryParam("take", 5)
                .queryParam("skip", 0)
                .header("Content-Type", "application/json")
                .pathParam("userId", user.getUserID())
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get(baseURI + basePath + "/{userId}" + "/posts" );

        postsResponse
                .then()
                .body("", Matchers.hasSize(1))
                .statusCode(200);
    }

    @Test(testName = "should make a 2nd post", priority = 5)
    public void makeASecondPost() {
        PostPOJO post = new PostPOJO("My new post 2", "\"https://i.imgur.com/HUdmONP.jpg", "public");
        // String convertedPost = objectMapper.writeValueAsString(post);


        Response postsResponse = given()

                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(post)
                .when()
                .post(baseURI + "/posts" );

        postsResponse
                .then()
                .statusCode(201);
    }

    @Test(testName = "should get 2 user posts", priority = 6)
    public void get2Posts() {
        ///users/{userId}/posts
        Response postsResponse = given()
                .queryParam("postStatus", "public")
                .queryParam("take", 5)
                .queryParam("skip", 0)
                .header("Content-Type", "application/json")
                .pathParam("userId", user.getUserID())
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get(baseURI + basePath + "/{userId}" + "/posts" );

        postsResponse
                .then()
                .body("", Matchers.hasSize(2))
                .statusCode(200);
    }



    // Helper methods for setup
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
