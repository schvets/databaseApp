import PageFactory.AlertPage;
import PageFactory.MainPage;
import PageFactory.UserTablePage;
import entities.User;
import org.testng.Assert;
import org.testng.annotations.*;
import tools.TestConfig;
import tools.WebDriverUtils;

/**
 * Created by Aleksandr on 12.05.2017.
 */
public class LoadUserTableTestCase {

    @Test
    public void LoadUserTableWOConnection() {
        MainPage mainPage = new MainPage();
        mainPage.clickConnectButton();
        Assert.assertFalse(mainPage.isLoadButtonClickable());
    }

    @Test
    public void LoadUserTableWithConnection() {
        MainPage mainPage = new MainPage();
        UserTablePage userTablePage = new UserTablePage();
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
        MainPage mainPage = new MainPage();
        UserTablePage userTablePage = new UserTablePage();
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        mainPage.createNewUser(user);
        while (!new WebDriverUtils().getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo();
        }
        new AlertPage().clickOkButtonAlert();
        webDriverUtils.PageSwitchTo();
        mainPage.clickLoadButton();
        Assert.assertTrue(userTablePage.isUserPresentedOnPage(user));
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
