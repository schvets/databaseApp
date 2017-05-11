import PageFactory.MainPage;
import PageFactory.PromptPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Aleksandr on 11.05.2017.
 */
public class ConnectionToDBTestCase {
    static WebDriver driver;

    @BeforeTest
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://www.ranorex.com/web-testing-examples/vip/");

    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }

    @BeforeMethod
    public void setUpBeforeMethod() {
        driver.get("http://www.ranorex.com/web-testing-examples/vip/");
    }

    @Test
    public void disconnectFromDB() {
        MainPage mainPage = new MainPage(driver);
        if (mainPage.getTextConnectionLabel().equals("Online")){
            mainPage.clickConnectButton();
        }
        String expText = mainPage.getTextConnectionLabel();
        Assert.assertTrue(expText.equals("Offline"));
    }

    @Test
    public void reconnectToDB() {
        MainPage mainPage = new MainPage(driver);
        if (mainPage.getTextConnectionLabel().equals("Online")){
            mainPage.clickConnectButton();
            mainPage.clickConnectButton();
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
            PromptPage promptPage = new PromptPage(driver);
            promptPage.clickOkButton();
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
        }
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .textToBePresentInElement(mainPage.getConnectionLabelWebElement(), "Online"));
        String expText = mainPage.getTextConnectionLabel();
        Assert.assertTrue(expText.equals("Online"));
    }

    @Test
    public void cancelReconnectToDB() {
        MainPage mainPage = new MainPage(driver);
        if (mainPage.getTextConnectionLabel().equals("Online")) {
            mainPage.clickConnectButton();
            mainPage.clickConnectButton();
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
            PromptPage promptPage = new PromptPage(driver);
            promptPage.clickCancelButton();
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
        }
        String expText = mainPage.getTextConnectionLabel();
        Assert.assertTrue(expText.equals("Offline"));
    }
}
