package tools;

import org.openqa.selenium.WebDriver;

/**
 * Created by stas on 2/14/17.
 */
public class WebDriverUtils {

    public void PageSwitchTo(WebDriver driver) {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }
}
