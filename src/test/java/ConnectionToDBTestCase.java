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
import tools.WebDriverUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by Aleksandr on 11.05.2017.
 */
public class ConnectionToDBTestCase {
    WebDriver driver;

    @Test
    public void disconnectFromDB() {
        MainPage mainPage = new MainPage(driver);
        while (mainPage.connectionChecher()) {
            mainPage.clickConnectButton();
        }
        String expText = mainPage.getTextConnectionLabel();
        Assert.assertTrue(expText.equals("Offline"));
    }

    @Test
    public void reconnectToDB() {
        MainPage mainPage = new MainPage(driver);
        while (mainPage.connectionChecher()) {
            mainPage.clickConnectButton();
        }
        mainPage.clickConnectButton();
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        webDriverUtils.PageSwitchTo(driver);
        PromptPage promptPage = new PromptPage(driver);
        promptPage.clickOkButton();
        webDriverUtils.PageSwitchTo(driver);
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .textToBePresentInElement(mainPage.getConnectionLabelWebElement(), "Online"));
        String expText = mainPage.getTextConnectionLabel();
        Assert.assertTrue(expText.equals("Online"));
    }

    @Test
    public void cancelReconnectToDB() {
        MainPage mainPage = new MainPage(driver);
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        while (mainPage.connectionChecher()) {
            mainPage.clickConnectButton();
        }
        mainPage.clickConnectButton();
        webDriverUtils.PageSwitchTo(driver);
        PromptPage promptPage = new PromptPage(driver);
        promptPage.clickCancelButton();
        webDriverUtils.PageSwitchTo(driver);
        String expText = mainPage.getTextConnectionLabel();
        Assert.assertTrue(expText.equals("Offline"));
    }

    @BeforeTest
    public void setUp() {
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

}
