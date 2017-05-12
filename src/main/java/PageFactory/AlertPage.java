package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tools.WebDriverUtils;


/**
 * Created by 485 on 12.05.2017.
 */
public class AlertPage {


    @FindBy(css = "#alertOK > center > button")
    WebElement okButton;

    public AlertPage() {

        //This initElements method will create all WebElements

        PageFactory.initElements(new WebDriverUtils().getDriver(), this);
    }

    public void clickOkButtonAlert() {
        okButton.click();
    }
}


