package DTOs.PagesDTOs;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageDTO {

    private static final String homeButtonText = "Home";
    private static final String loginButtonText = "Login";

    @FindBy(id = "nav-link-home")
    private WebElement homeButton;

    @FindBy(id = "nav-link-login")
    private WebElement loginButton;


}
