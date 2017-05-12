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
public class ClearUserTableTestCase {

    @Test
    public void ClearUserTableWOConnectionEmptyUserTable() {
        UserTablePage userTablePage = new UserTablePage();
        MainPage mainPage = new MainPage();
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
        UserTablePage userTablePage = new UserTablePage();
        MainPage mainPage = new MainPage();
        mainPage.clickConnectButton();
        mainPage.createNewUser(user);
        mainPage.clickClearButton();
        userTablePage.getUserCount();
        Assert.assertEquals(userTablePage.getUserCount(), 0);
    }

    @Test(dataProvider = "validUser")
    public void ClearUserTableWithConnectionNotEmptyUserTable(User user) {
        UserTablePage userTablePage = new UserTablePage();
        MainPage mainPage = new MainPage();
        WebDriverUtils webDriverUtils = new WebDriverUtils();
        webDriverUtils.PageSwitchTo();
        mainPage.createNewUser(user);
        while (!new WebDriverUtils().getTitle().equals("VIP Database")) {
            webDriverUtils.PageSwitchTo();
        }
        new AlertPage().clickOkButtonAlert();
        webDriverUtils.PageSwitchTo();
        mainPage.clickClearButton();
        userTablePage.getUserCount();
        Assert.assertEquals(userTablePage.getUserCount(), 0);
    }

    @Test
    public void ClearUserTableWithConnectionLoadedUserTable() {
        UserTablePage userTablePage = new UserTablePage();
        MainPage mainPage = new MainPage();
        mainPage.clickLoadButton();
        mainPage.clickClearButton();
        userTablePage.getUserCount();
        Assert.assertEquals(userTablePage.getUserCount(), 0);
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
