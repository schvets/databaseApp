package PageFactory;

import entities.User;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;


/**
 * Created by Aleksandr on 11.05.2017.
 */
public class MainPage {
    /**
     * All WebElements are identified by @FindBy annotation
     */
    final WebDriver driver;

    @FindBy(id = "connect")
    WebElement connectButton;

    @FindBy(id = "FirstName")
    WebElement textInputFirstName;

    @FindBy(id = "LastName")
    WebElement textInputLastName;

    @FindBy(css = "#gender > table > tbody > tr:nth-child(2) > td")
    WebElement radioButtonFemale;

    @FindBy(css = "#gender > table > tbody > tr:nth-child(3) > td")
    WebElement radioButtonMale;

    @FindBy(id = "Gender")
    List<WebElement> radioButtonList;

    @FindBy(id = "Add")
    WebElement addButton;

    @FindBy(id = "Delete")
    WebElement deleteButton;

    @FindBy(id = "Category")
    WebElement dropDownCategory;

    @FindBy(id = "Load")
    WebElement loadButton;

    @FindBy(id = "Save")
    WebElement saveButton;

    @FindBy(id = "Clear")
    WebElement clearButton;

    @FindBy(id = "connection")
    WebElement connectionLabel;

    @FindBy(id = "count")
    WebElement countLabel;

    @FindBy(css = "#VIPs > tbody > tr")
    List<WebElement> userList;

    public MainPage(WebDriver driver) {

        this.driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);
    }


    public void clickConnectButton() {
        connectButton.click();
    }

    public void clickAddButton() {
        addButton.click();
    }

    public void clickClearButton() {
        clearButton.click();
    }

    public String getTextConnectionLabel() {
        return connectionLabel.getText();
    }

    public String getTextCountLabel() {
        return countLabel.getText();
    }

    public void clickDeleteButton() {
        deleteButton.click();
    }

    public void clickLoadButton() {
        loadButton.click();
    }

    public void clickRadioButtonFemale() {
        radioButtonFemale.click();
    }

    public void clickRadioButtonMale() {
        radioButtonMale.click();
    }

    public void clickSaveButton() {
        saveButton.click();
    }

    public void typeClearFirstName(String firstName) {
        textInputFirstName.clear();
        textInputFirstName.sendKeys(firstName);
    }

    public void typeClearLastName(String lastName) {
        textInputLastName.clear();
        textInputLastName.sendKeys(lastName);
    }

    public void selectDropdownByName(String category) {
        Select select = new Select(dropDownCategory);
        select.selectByValue(category);
    }

    public void selectDropdownByNumber(int number) {
        Select select = new Select(dropDownCategory);
        select.selectByIndex(number);
    }

    public void selectRadioButton(String sex) {
        if (sex.equals("Female")) {
            clickRadioButtonFemale();
        } else if (sex.equals("Male")) {
            clickRadioButtonMale();
        } else {
            throw new NoSuchElementException(sex);
        }

    }

    public void createNewUser(User user) {
        typeClearFirstName(user.getUserFirstName());
        typeClearLastName(user.getUserLastName());
        selectDropdownByName(user.getUserCategory());
        selectRadioButton(user.getUserSex());
        clickAddButton();
        clickSaveButton();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        AlertPage alertPage = new AlertPage(driver);
        alertPage.clickOkButtonAlert();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    public WebElement getConnectionLabelWebElement() {
        return driver.findElement(By.id("connection"));
    }


    public int getTableSize() {
        return userList.size();
    }

/*    public User getUserByName(String name){
        for(int i= 0 ; i>=(getTableSize() - 1); i++){

        }
    }*/
}