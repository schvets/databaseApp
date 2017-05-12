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
 * Created by 485 on 11.05.2017.
 */
public class CreateDeleteUserTestCase {
    WebDriver driver;

    @DataProvider
    public Object[][] validUser() {
        return new Object[][]{
                {new User("Ivanov", "Ivan", "Other", "Male")},
                {new User("Petrov", "Petr", "Movie", "Female")}
        };
    }

    @Test(dataProvider = "validUser")
    public void CreateValidUserDBStateConnect(User user) {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        mainPage.createNewUser(user);
        while (!driver.getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo(driver);
        }
        new AlertPage(driver).clickOkButtonAlert();
        webDriverUtils.PageSwitchTo(driver);
        Assert.assertEquals(userTablePage.isUserPresentedOnPage(user), true);
    }

    @Test(dataProvider = "validUser")
    public void CreateValidUserDBStateDisconnect(User user) {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickConnectButton();
        mainPage.createNewUser(user);
        Assert.assertEquals(userTablePage.isUserPresentedOnPage(user), true);
    }


    @DataProvider
    public Object[][] invalidUser() {
        return new Object[][]{
                {new User("AAAAAAAAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAAAAA",
                        "Sport", "Female")},
                {new User("123456789", "123456789", "Sport", "Female")},
                {new User("$^%&#!@%", "$^%&#!@%", "Sport", "Female")},
                {new User("", "", "Sport", "Female")}
        };
    }

    @Test(dataProvider = "invalidUser")
    public void CreateInvalidUserDBStateConnect(User user) {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        mainPage.createNewUser(user);
        while (!driver.getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo(driver);
        }
        new AlertPage(driver).clickOkButtonAlert();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        Assert.assertEquals(userTablePage.isUserPresentedOnPage(user), false);
    }

    @Test(dataProvider = "invalidUser")
    public void CreateInvalidUserDBStateDisconnect(User user) {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickConnectButton();
        mainPage.createNewUser(user);
        Assert.assertEquals(userTablePage.isUserPresentedOnPage(user), false);
    }

    @DataProvider
    public Object[][] duplicateUser() {
        return new Object[][]{
                {new User(" Ivanov", "Ivan", "Other", "Male"),
                        new User(" Ivanov", "Ivan", "Other", "Male")}
        };
    }

    @Test(dataProvider = "duplicateUser")
    public void CreateDuplicateUserDBStateDisconnect(User user1, User user2) {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        mainPage.createNewUser(user1);
        while (!driver.getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo(driver);
        }
        new AlertPage(driver).clickOkButtonAlert();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        int userCount = userTablePage.getUserCount();
        mainPage.createNewUser(user2);
        while (!driver.getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo(driver);
        }
        new AlertPage(driver).clickOkButtonAlert();
        webDriverUtils.PageSwitchTo(driver);
        int newUserCount = userTablePage.getUserCount();
        Assert.assertEquals(newUserCount + " user", userCount + " user");
    }

    @Test(dataProvider = "validUser")
    public void DeleteValidUserDBStateConnect(User user) {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        mainPage.createNewUser(user);
        while (!driver.getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo(driver);
        }
        new AlertPage(driver).clickOkButtonAlert();
        webDriverUtils.PageSwitchTo(driver);
        mainPage.clickDeleteButton();
        userTablePage.isUserPresentedOnPage(user);
        Assert.assertEquals(userTablePage.isUserPresentedOnPage(user), false);
    }

    @Test
    public void DeleteValidLoadedUserDBStateConnect() {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoadButton();
        User user = userTablePage.selectGetRandomUser();
        mainPage.clickDeleteButton();
        userTablePage.isUserPresentedOnPage(user);
        Assert.assertEquals(userTablePage.isUserPresentedOnPage(user), false);
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

