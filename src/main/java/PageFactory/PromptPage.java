package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tools.WebDriverUtils;

/**
 * Created by Aleksandr on 11.05.2017.
 */
public class PromptPage {

    @FindBy(css="#alertOKCancel > center > button:nth-child(3)")
    WebElement okButton;

    @FindBy(css="#alertOKCancel > center > button:nth-child(4)")
    WebElement cancelButton;

    public PromptPage() {

        //This initElements method will create all WebElements

        PageFactory.initElements(new WebDriverUtils().getDriver(), this);
    }

    public void clickOkButton() {
        okButton.click();
    }

    public void clickCancelButton(){
        cancelButton.click();
    }
}

