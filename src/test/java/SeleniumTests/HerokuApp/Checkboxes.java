package SeleniumTests.HerokuApp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;

public class Checkboxes {

    WebDriver chromeDriver;
    boolean checkbox1State;
    boolean checkbox2State;


    @FindBy(xpath = "//form[@id='checkboxes']/input[1]")
    WebElement checkbox1;

    @FindBy(xpath = "//form[@id='checkboxes']/input[2]")
    WebElement checkbox2;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        baseURI = "https://the-internet.herokuapp.com/checkboxes";
        PageFactory.initElements(chromeDriver, this);
    }

    @AfterClass
    public void tearDown() {
        chromeDriver.quit();
    }

    @Test(testName = "check first box is not checked")
    public void checkInitialStateBox1() {
        chromeDriver.get(baseURI);
        checkbox1State = checkbox1.isSelected();
        Assert.assertFalse(checkbox1State);
    }

    @Test(testName = "check second box is checked")
    public void checkInitialStateBox2() {
        chromeDriver.get(baseURI);
        checkbox2State = checkbox2.isSelected();
        Assert.assertTrue(checkbox2State);
    }

    @Test(testName = "checkbox 1 state is changed")
    public void tickBox1() {
        checkbox1.click();
        checkbox1State = checkbox1.isSelected();
        Assert.assertTrue(checkbox1State);
    }

    @Test(testName = "checkbox 2 state is changed")
    public void tickBox2() {
        checkbox2.click();
        checkbox2State = checkbox2.isSelected();
        Assert.assertFalse(checkbox2State);
    }
}
