import PageFactory.MainPage;
import PageFactory.PromptPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tools.TestConfig;
import tools.WebDriverUtils;

/**
 * Created by Aleksandr on 11.05.2017.
 */
public class ConnectionToDBTestCase {
    WebDriver driver;

    @Test
    public void disconnectFromDB() {
        MainPage mainPage = new MainPage();
        while (mainPage.connectionChecher()) {
            mainPage.clickConnectButton();
        }
        String expText = mainPage.getTextConnectionLabel();
        Assert.assertTrue(expText.equals("Offline"));
    }

    @Test
    public void reconnectToDB() {
        MainPage mainPage = new MainPage();
        while (mainPage.connectionChecher()) {
            mainPage.clickConnectButton();
        }
        mainPage.clickConnectButton();
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        webDriverUtils.PageSwitchTo();
        PromptPage promptPage = new PromptPage();
        promptPage.clickOkButton();
        webDriverUtils.PageSwitchTo();
        new WebDriverWait(new WebDriverUtils().getDriver(), 10)
                .until(ExpectedConditions
                        .textToBePresentInElement(mainPage.getConnectionLabelWebElement(), "Online"));
        String expText = mainPage.getTextConnectionLabel();
        Assert.assertTrue(expText.equals("Online"));
    }

    @Test
    public void cancelReconnectToDB() {
        MainPage mainPage = new MainPage();
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        while (mainPage.connectionChecher()) {
            mainPage.clickConnectButton();
        }
        mainPage.clickConnectButton();
        webDriverUtils.PageSwitchTo();
        PromptPage promptPage = new PromptPage();
        promptPage.clickCancelButton();
        webDriverUtils.PageSwitchTo();
        String expText = mainPage.getTextConnectionLabel();
        Assert.assertTrue(expText.equals("Offline"));
    }

    @BeforeMethod
    public void refresh() {
        new WebDriverUtils().refresh();
    }
    @BeforeTest
    public void setUp() {
        new WebDriverUtils().load(new TestConfig().getSystemUnderTestBaseUrl());
    }

    @AfterTest
    public void tearDown() {
        new WebDriverUtils().stop();
    }

}
