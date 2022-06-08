package DTOs.APIDTOs;

import com.github.javafaker.Faker;

import java.util.Calendar;

public class SignupPOJO {

    // Java faker account that creates a fake user in the constructor

    private String username;
    private String email;
    private String birthDate;
    private String password;
    private String publicInfo;
    private int userID;
    private String profilePicUrl;
    private boolean isBanned;

    public SignupPOJO() {
        Faker faker = new Faker();

        this.username = faker.name().username();
        System.out.println(username);
        this.email = faker.internet().emailAddress().substring(0,20);
        System.out.println(email);
        this.birthDate = "31.08.1994";
        System.out.println(birthDate);
        this.password = faker.internet().password();
        System.out.println(password);
        this.publicInfo = "Fake account";
        System.out.println(publicInfo);
        isBanned = false;
    }

    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
    }


    public String getBirthDate() {
        return birthDate;
    }


    public String getPassword() {
        return password;
    }


    public String getPublicInfo() {
        return publicInfo;
    }

    public int getUserID() {
        return userID;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setUserID(int userID) {
        this.userID = userID;
        System.out.println(userID);
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
        System.out.println(profilePicUrl);
    }


}
