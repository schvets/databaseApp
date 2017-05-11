import PageFactory.MainPage;
import PageFactory.PromptPage;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tools.WebDriverUtils;

import java.util.Set;

/**
 * Created by Aleksandr on 11.05.2017.
 */
public class ConnectionToDBTestCase {
    @BeforeTest
    public void setUp(){
        WebDriverUtils.OpenUrl("http://www.ranorex.com/web-testing-examples/vip/");
    }

    @AfterTest
    public void tearDown() {
        WebDriverUtils.stop();
    }

    @BeforeMethod
    public void setUpBeforeMethod() {
        WebDriverUtils.OpenUrl("http://www.ranorex.com/web-testing-examples/vip/");
    }

    @Test
    private void disconnectFromDB() {
        MainPage mainPage = new MainPage(WebDriverUtils.getWebDriver());
        if (mainPage.getTextConnectionLabel().equals("Online")){
            mainPage.clickConnectButton();
        }
        String expText = mainPage.getTextConnectionLabel();
        Assert.assertTrue(expText.equals("Offline"));
    }

    @Test
    private void reconnectToDB() {
        Set<String> oldWindowsSet = WebDriverUtils.getWebDriver().getWindowHandles();
        MainPage mainPage = new MainPage(WebDriverUtils.getWebDriver());
        if (mainPage.getTextConnectionLabel().equals("Online")){
            mainPage.clickConnectButton();
            mainPage.clickConnectButton();
            Set<String> newWindowsSet =  WebDriverUtils.getWebDriver().getWindowHandles();
            newWindowsSet.removeAll(oldWindowsSet);
            String newWindowHandle = newWindowsSet.iterator().next();
            WebDriverUtils.getWebDriver().switchTo().window(newWindowHandle);
            PromptPage promptPage = new PromptPage(WebDriverUtils.getWebDriver());
            promptPage.clickOkButton();
            Set<String> restoredWindowsSet = WebDriverUtils.getWebDriver().getWindowHandles();
            String restoredWindowHandle = restoredWindowsSet.iterator().next();
            WebDriverUtils.getWebDriver().switchTo().window(restoredWindowHandle);
        }

        String expText = mainPage.getTextConnectionLabel();
        System.out.println(expText);
        Assert.assertTrue(expText.equals("Online"));
    }
}
