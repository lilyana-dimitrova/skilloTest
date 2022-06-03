package SeleniumTests;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class HomePageTesting {

    static ChromeDriver chromeDriver;
    private static final String homePageTitle = "ISkillo";
    private static final String homeButtonText = "Home";
    private static final String loginButtonText = "Login";

    @FindBy(id = "nav-link-home")
    private WebElement homeButton;


    @FindBy(id = "nav-link-login")
    private WebElement loginButton;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://training.skillo-bg.com";
        RestAssured.basePath = "/users/login";
        //createFakeUser();
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        PageFactory.initElements(chromeDriver, this);
    }


    @Test(testName = "should have correct page title", priority = 1)
    public void getHomePageTitle() {
        chromeDriver.get(baseURI);
        chromeDriver.manage().window().maximize();
        Assert.assertEquals(homePageTitle, chromeDriver.getTitle());
    }


    @Test(testName = "should have home button", priority = 2)
    public void getHomeButton() {

        chromeDriver.get(baseURI);
        chromeDriver.manage().window().maximize();
        boolean buttonExists = !chromeDriver.findElements(By.id("nav-link-home")).isEmpty();
        Assert.assertTrue(buttonExists);

    }

    @Test(testName = "should have correct home button title", priority = 3)
    public void getHomeButtonName() {

        chromeDriver.get(baseURI);
        chromeDriver.manage().window().maximize();
        Assert.assertEquals(homeButton.getText(), homeButtonText);

    }


    @Test(testName = "should have login button", priority = 4)
    public void getLoginButton() {

        chromeDriver.get(baseURI);
        chromeDriver.manage().window().maximize();
        boolean buttonExists = !chromeDriver.findElements(By.id("nav-link-login")).isEmpty();
        Assert.assertTrue(buttonExists);

    }

    @Test(testName = "should have correct login button title", priority = 5)
    public void getLoginButtonName() {

        chromeDriver.get(baseURI);
        chromeDriver.manage().window().maximize();
        Assert.assertEquals(loginButton.getText(), loginButtonText);

    }


}

