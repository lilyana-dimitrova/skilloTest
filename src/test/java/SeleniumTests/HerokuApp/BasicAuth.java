package SeleniumTests.HerokuApp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.annotation.Target;
import java.util.List;

import static io.restassured.RestAssured.baseURI;

public class BasicAuth {

    WebDriver chromeDriver;
    WebDriverWait wait;
    String successMessage = "Congratulations! You must have the proper credentials.";


    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        baseURI = "https://the-internet.herokuapp.com/basic_auth";
        HasAuthentication authentication =  (HasAuthentication) chromeDriver;
        authentication.register(() -> new UsernameAndPassword("admin", "admin"));
    }

    @AfterClass
    public void tearDown() {
        chromeDriver.quit();
    }

    @Test(testName = "should show Congrats message")
        public void shouldHaveCorrectMessage() {
        chromeDriver.get(baseURI);
            WebElement text = chromeDriver.findElement(By.xpath("//div[@class='example']/p"));
            Assert.assertEquals(text.getText(), successMessage);
        }



}
