package tools;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class WebDriverUtils {

    private static WebDriver driver;
    public final int ABSENCE_TIMEOUT = 5;
    private final long IMPLICITLY_WAIT_TIMEOUT = 4;

    public WebDriverUtils() {
    }

    public void PageSwitchTo() {
        for (String winHandle : new WebDriverUtils().getDriver().getWindowHandles()) {
            new WebDriverUtils().getDriver().switchTo().window(winHandle);
        }
    }

    public WebDriver getDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            try {
                driver = new RemoteWebDriver(new URL(new TestConfig().getLocalPort()), capabilities);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            driver.manage().timeouts().implicitlyWait(
                    getImplicitlyWaitTimeout(), TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
        return driver;
    }

    long getImplicitlyWaitTimeout() {
        return IMPLICITLY_WAIT_TIMEOUT;
    }

    public void load(final String path) {
        getDriver().navigate().to(path);
    }

    public void stop() {
        if (driver != null) {
            try {
                driver.manage().deleteAllCookies();
                driver.close();
            } catch (UnhandledAlertException e) {
                String errorMessage = e.getMessage();
                while (!errorMessage.equals("")) {
                    try {
                        driver.close();
                        errorMessage = "";
                    } catch (UnhandledAlertException e1) {
                        errorMessage = e1.getMessage();
                    }
                }
                driver = null;
                throw new UnhandledAlertException(e.getMessage());
            }
        }
        driver = null;
    }

    public void refresh() {
        getDriver().navigate().refresh();
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public String getTitle() {
        return getDriver().getTitle();
    }

}
