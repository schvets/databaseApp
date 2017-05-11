package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/**
 * Created by 485 on 12.05.2017.
 */
public class AlertPage {

    final WebDriver driver;

    @FindBy(css = "#alertOK > center > button")
    WebElement okButton;

    public AlertPage(WebDriver driver) {

        this.driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);
    }

    public void clickOkButtonAlert() {
        okButton.click();
    }
}


