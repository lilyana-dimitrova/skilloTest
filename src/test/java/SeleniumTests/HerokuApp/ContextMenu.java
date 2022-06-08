package SeleniumTests.HerokuApp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.baseURI;

public class ContextMenu {
    WebDriver chromeDriver;
    WebDriverWait wait;
    Actions actions;
    Alert alert;
    String alertText;
    String expectedText = "You selected a context menu";

    /*@FindBy(xpath = "//*[@id=\"hot-spot\"]")
    //@FindBy(how = How.ID, using = "hot-spot")
    WebElement contextBox;*/

    @FindBy(id = "hot-spot")
    WebElement contextBox;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        actions = new Actions(chromeDriver);
        wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
        baseURI = "https://the-internet.herokuapp.com/context_menu";
        PageFactory.initElements(chromeDriver, this);
    }

    @AfterClass
    public void tearDown() {
        chromeDriver.quit();
    }

    @Test(testName = "should click context box")
    public void rightClick() {
            //chromeDriver.wait();
            //contextBox = chromeDriver.findElement(By.id("hot-spot"));
            actions.contextClick(contextBox).perform();
            alert = chromeDriver.switchTo().alert();
            alertText = alert.getText();
            Assert.assertEquals(alertText, expectedText);
            alert.dismiss();

    }
}
