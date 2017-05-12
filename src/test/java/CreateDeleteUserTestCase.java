import PageFactory.AlertPage;
import PageFactory.MainPage;
import PageFactory.UserTablePage;
import entities.User;
import org.testng.Assert;
import org.testng.annotations.*;
import tools.TestConfig;
import tools.WebDriverUtils;

/**
 * Created by 485 on 11.05.2017.
 */
public class CreateDeleteUserTestCase {

    @DataProvider
    public Object[][] validUser() {
        return new Object[][]{
                {new User("Ivanov", "Ivan", "Other", "Male")},
                {new User("Petrov", "Petr", "Movie", "Female")}
        };
    }

    @Test(dataProvider = "validUser")
    public void CreateValidUserDBStateConnect(User user) {
        UserTablePage userTablePage = new UserTablePage();
        MainPage mainPage = new MainPage();
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        mainPage.createNewUser(user);
        while (!new WebDriverUtils().getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo();
        }
        new AlertPage().clickOkButtonAlert();
        webDriverUtils.PageSwitchTo();
        Assert.assertEquals(userTablePage.isUserPresentedOnPage(user), true);
    }

    @Test(dataProvider = "validUser")
    public void CreateValidUserDBStateDisconnect(User user) {
        UserTablePage userTablePage = new UserTablePage();
        MainPage mainPage = new MainPage();
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
        UserTablePage userTablePage = new UserTablePage();
        MainPage mainPage = new MainPage();
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        mainPage.createNewUser(user);
        while (!new WebDriverUtils().getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo();
        }
        new AlertPage().clickOkButtonAlert();
        webDriverUtils.PageSwitchTo();
        Assert.assertEquals(userTablePage.isUserPresentedOnPage(user), false);
    }

    @Test(dataProvider = "invalidUser")
    public void CreateInvalidUserDBStateDisconnect(User user) {
        UserTablePage userTablePage = new UserTablePage();
        MainPage mainPage = new MainPage();
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
        UserTablePage userTablePage = new UserTablePage();
        MainPage mainPage = new MainPage();
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        mainPage.createNewUser(user1);
        while (!new WebDriverUtils().getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo();
        }
        new AlertPage().clickOkButtonAlert();
        for (String winHandle : new WebDriverUtils().getDriver().getWindowHandles()) {
            new WebDriverUtils().getDriver().switchTo().window(winHandle);
        }
        int userCount = userTablePage.getUserCount();
        mainPage.createNewUser(user2);
        while (!new WebDriverUtils().getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo();
        }
        new AlertPage().clickOkButtonAlert();
        webDriverUtils.PageSwitchTo();
        int newUserCount = userTablePage.getUserCount();
        Assert.assertEquals(newUserCount + " user", userCount + " user");
    }

    @Test(dataProvider = "validUser")
    public void DeleteValidUserDBStateConnect(User user) {
        UserTablePage userTablePage = new UserTablePage();
        MainPage mainPage = new MainPage();
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        mainPage.createNewUser(user);
        while (!new WebDriverUtils().getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo();
        }
        new AlertPage().clickOkButtonAlert();
        webDriverUtils.PageSwitchTo();
        mainPage.clickDeleteButton();
        userTablePage.isUserPresentedOnPage(user);
        Assert.assertEquals(userTablePage.isUserPresentedOnPage(user), false);
    }

    @Test
    public void DeleteValidLoadedUserDBStateConnect() {
        UserTablePage userTablePage = new UserTablePage();
        MainPage mainPage = new MainPage();
        mainPage.clickLoadButton();
        User user = userTablePage.selectGetRandomUser();
        mainPage.clickDeleteButton();
        userTablePage.isUserPresentedOnPage(user);
        Assert.assertEquals(userTablePage.isUserPresentedOnPage(user), false);
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

