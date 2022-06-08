package SeleniumTests.HerokuApp;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;

public class AddRemoveElements {


    WebDriver chromeDriver;
    WebDriverWait wait;


    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        baseURI = "https://the-internet.herokuapp.com/add_remove_elements/";

    }

    @AfterClass
    public void tearDown() {
        chromeDriver.quit();
    }

    @Test(testName = "should not have any delete buttons")
    public void checkInitialState(){
        chromeDriver.get(baseURI);
        //List<WebElement> elementsContainer = chromeDriver.findElements(By.xpath("//div[@id='elements']/child"));
        List<WebElement> elementsContainer = chromeDriver.findElements(By.xpath("//div[@id='elements']/descendant::*"));
        Assert.assertTrue(elementsContainer.isEmpty());
    }

    @Test(testName = "should have 2 delete buttons")
    public void addElements() {
        basePath = "/add_remove_elements/";
        chromeDriver.get(baseURI);


    }


}

