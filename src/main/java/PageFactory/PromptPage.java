package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Aleksandr on 11.05.2017.
 */
public class PromptPage {
    WebDriver driver;

    @FindBy(css="#alertOKCancel > center > button:nth-child(3)")
    WebElement okButton;

    @FindBy(css="#alertOKCancel > center > button:nth-child(4)")
    WebElement cancelButton;

    public void clickOkButton(){
        okButton.click();
    }

    public PromptPage(WebDriver driver){

        this.driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);
    }

    public void clickCancelButton(){
        cancelButton.click();
    }
}

