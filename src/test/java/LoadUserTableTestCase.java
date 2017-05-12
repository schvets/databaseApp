import PageFactory.AlertPage;
import PageFactory.MainPage;
import PageFactory.UserTablePage;
import entities.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aleksandr on 12.05.2017.
 */
public class LoadUserTableTestCase {
    static WebDriver driver;

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

    @BeforeTest(dependsOnMethods = "setUp")
    public void setUpBasic() throws AWTException, InterruptedException {
        Robot rb = new Robot();
        StringSelection user = new StringSelection("dn060289sas1");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(user, null); //User which replace with entered user name
        Thread.sleep(500);

        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_TAB);
        rb.keyRelease(KeyEvent.VK_TAB);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StringSelection passwd = new StringSelection("06021989qwe");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(passwd, null); //passwd which replace with entered password
        Thread.sleep(500);
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
    }

    @Test
    public void LoadUserTableWOConnection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickConnectButton();
        Assert.assertFalse(mainPage.isLoadButtonClickable());
    }

    @Test
    public void LoadUserTableWithConnection() {
        MainPage mainPage = new MainPage(driver);
        UserTablePage userTablePage = new UserTablePage(driver);
        int startUserTable = userTablePage.getUserCount();
        mainPage.clickLoadButton();
        Assert.assertNotEquals(userTablePage.getUserCount(), startUserTable);
    }

    @DataProvider
    public Object[][] validUser() {
        return new Object[][]{
                {new User("Ivanov", "Ivan", "Other", "Male")},
                {new User("Petrov", "Petr", "Movie", "Female")}
        };
    }

    @Test(dataProvider = "validUser")
    public void LoadUserTableAfterNewUserSaveWithConnection(User user) {
        MainPage mainPage = new MainPage(driver);
        UserTablePage userTablePage = new UserTablePage(driver);
        mainPage.createNewUser(user);
        while (!driver.getTitle().equals("VIP Database")) {
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
        }
        AlertPage alertPage = new AlertPage(driver);
        alertPage.clickOkButtonAlert();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        mainPage.clickLoadButton();
        Assert.assertTrue(userTablePage.isUserPresentedOnPage(user));
    }
}
