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
                {new User("AAAAAAAAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAAAAA", "Sport", "Female")},
                {new User("123456789", "123456789", "Sport", "Female")},
                {new User("$^%&#!@%", "$^%&#!@%", "Sport", "Female")},
                {new User("", "", "Sport", "Female")}
        };
    }

    @Test(dataProvider = "invalidUser")
    public void CreateInvalidUserDBStateConnect(User user) {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
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
        mainPage.createNewUser(user1);
        while (!driver.getTitle().equals("VIP Database")) {
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
        }
        AlertPage alertPage1 = new AlertPage(driver);
        alertPage1.clickOkButtonAlert();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        int userCount = userTablePage.getUserCount();
        mainPage.createNewUser(user2);
        while (!driver.getTitle().equals("VIP Database")) {
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
        }
        AlertPage alertPage2 = new AlertPage(driver);
        alertPage2.clickOkButtonAlert();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        int newUserCount = userTablePage.getUserCount();
        Assert.assertEquals(newUserCount + " user", userCount + " user");
    }

    @Test(dataProvider = "validUser")
    public void DeleteValidUserDBStateConnect(User user) {
        UserTablePage userTablePage = new UserTablePage(driver);
        MainPage mainPage = new MainPage(driver);
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


}

