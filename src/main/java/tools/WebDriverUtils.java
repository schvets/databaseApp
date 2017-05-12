package tools;

import org.openqa.selenium.WebDriver;

/**
 * Created by stas on 2/14/17.
 */
public class WebDriverUtils {
    WebDriver driver;

/*
    private static final int implicitlyWaitTimeOut = 15;

    private WebDriverUtils() {
    }

    public static WebDriver getWebDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(implicitlyWaitTimeOut, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static int getImplicitlyWaitTimeOut() {
        return implicitlyWaitTimeOut;
    }

    public static void stop() {
        if (driver != null) {
            driver.close();
        }
        driver = null;
    }

    public static void OpenUrl(String url) {
        getWebDriver().get(url);
    }

    public static void refreshPage() {
        driver.navigate().refresh();
    }

    public static void AlertSwitchTo() {
        new WebDriverWait(driver, 3).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert();
    }
*/


    public void PageSwitchTo() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }
}
