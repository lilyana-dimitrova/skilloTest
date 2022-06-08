package SeleniumTests.HerokuApp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;

public class DragAndDrop {
    WebDriver chromeDriver;
    WebDriverWait wait;

    @FindBy(xpath = "//div[@id='column-a']")
    WebElement elementA;

    @FindBy(xpath = "//div[@id='column-b']")
    WebElement elementB;


    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        baseURI = "https://the-internet.herokuapp.com/drag_and_drop";
        PageFactory.initElements(chromeDriver, this);
    }

    @AfterClass
    public void tearDown() {
        chromeDriver.quit();
    }

    @Test(testName = "drag n drop")
    public void dragAndDrop() {
        chromeDriver.get(baseURI);
        Actions actions = new Actions(chromeDriver);
       // actions.moveToElement(elementA).clickAndHold().moveToElement(elementB).release(elementB).build().perform();
        actions.dragAndDrop(elementA, elementB).build().perform();
    }

}
