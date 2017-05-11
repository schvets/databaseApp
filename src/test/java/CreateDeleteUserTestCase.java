import PageFactory.MainPage;
import PageFactory.UserTablePage;
import entities.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by 485 on 11.05.2017.
 */
public class CreateDeleteUserTestCase {
    static WebDriver driver;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://www.ranorex.com/web-testing-examples/vip/");
    }

/*    @AfterTest
    public void tearDown() {
        driver.close();
    }*/

    @BeforeMethod
    public void setUpBeforeMethod() {
        driver.get("http://www.ranorex.com/web-testing-examples/vip/");
    }

    @Test
    public void firsttest() {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoadButton();
        User user = new User("Ivanov", "Ivan", "Other", "Male");
        mainPage.createNewUser(user);
        Assert.assertEquals(userTablePage.findUser(user), true);
    }

}

