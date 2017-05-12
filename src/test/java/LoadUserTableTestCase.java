import PageFactory.AlertPage;
import PageFactory.MainPage;
import PageFactory.UserTablePage;
import entities.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import tools.WebDriverUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by Aleksandr on 12.05.2017.
 */
public class LoadUserTableTestCase {
    WebDriver driver;

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
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        mainPage.createNewUser(user);
        while (!driver.getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo(driver);
        }
        new AlertPage(driver).clickOkButtonAlert();
        webDriverUtils.PageSwitchTo(driver);
        mainPage.clickLoadButton();
        Assert.assertTrue(userTablePage.isUserPresentedOnPage(user));
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
