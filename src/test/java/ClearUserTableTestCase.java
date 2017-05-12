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
public class ClearUserTableTestCase {
    WebDriver driver;

    @Test
    public void ClearUserTableWOConnectionEmptyUserTable() {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickConnectButton();
        mainPage.clickClearButton();
        userTablePage.getUserCount();
        Assert.assertEquals(userTablePage.getUserCount(), 0);
    }

    @DataProvider
    public Object[][] validUser() {
        return new Object[][]{
                {new User("Ivanov", "Ivan", "Other", "Male")},
                {new User("Petrov", "Petr", "Movie", "Female")}
        };
    }

    @Test(dataProvider = "validUser")
    public void ClearUserTableWOConnectionNotEmptyUserTable(User user) {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickConnectButton();
        mainPage.createNewUser(user);
        mainPage.clickClearButton();
        userTablePage.getUserCount();
        Assert.assertEquals(userTablePage.getUserCount(), 0);
    }

    @Test(dataProvider = "validUser")
    public void ClearUserTableWithConnectionNotEmptyUserTable(User user) {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        webDriverUtils.PageSwitchTo(driver);
        mainPage.createNewUser(user);
        while (!driver.getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo(driver);
        }
        new AlertPage(driver).clickOkButtonAlert();
        webDriverUtils.PageSwitchTo(driver);
        mainPage.clickClearButton();
        userTablePage.getUserCount();
        Assert.assertEquals(userTablePage.getUserCount(), 0);
    }

    @Test
    public void ClearUserTableWithConnectionLoadedUserTable() {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoadButton();
        mainPage.clickClearButton();
        userTablePage.getUserCount();
        Assert.assertEquals(userTablePage.getUserCount(), 0);
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
