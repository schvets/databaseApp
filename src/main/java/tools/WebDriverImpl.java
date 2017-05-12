package tools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverImpl {

    private static WebDriver driver;

    private WebDriverImpl() {
    }

    public static WebDriver takeDriver() {
        if (driver == null) {
            try {
                driver = new RemoteWebDriver(new URL("http://localhost:9515"), DesiredCapabilities.chrome());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return driver;
    }
}
