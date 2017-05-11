package PageFactory;

import entities.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by Aleksandr on 11.05.2017.
 */
public class UserTablePage {
    /**
     * All WebElements are identified by @FindBy annotation
     */
    WebDriver driver;
    @FindBy(xpath = "//table[@id='VIPs']/tbody/tr")
    List<WebElement> userList;
    private List<WebElement> users;

    public UserTablePage(WebDriver driver) {

        this.driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);
    }

    public int getUserCount() {
        return userList.size();
    }

    public List<WebElement> getUserList() {
        List<WebElement> allUsers = driver.findElements(By.xpath("//table[@id='VIPs']/tbody/tr"));
        return allUsers;
    }

    public void selectUserByFirstName(String firstName) {
        users = getUserList();
        for (WebElement we : users) {
            String actualFirstName = we.findElement(By.cssSelector("td:nth-child(2)")).getText();
            if (actualFirstName.equals(firstName)) {
                we.findElement(By.id("VIP")).click();
            }
        }
    }

    public void selectUserByLastName(String lastName) {
        users = getUserList();
        for (WebElement we : users) {
            String actualLastName = we.findElement(By.cssSelector("td:nth-child(3)")).getText();
            if (actualLastName.equals(lastName)) {
                we.findElement(By.id("VIP")).click();
            }
        }
    }

/*

#VIPs > tbody > tr:nth-child(2) > td:nth-child(2) First Name
        #VIPs > tbody > tr:nth-child(2) > td:nth-child(3) Last Name
        #VIPs > tbody > tr:nth-child(2) > td:nth-child(4) Gender
        #VIPs > tbody > tr:nth-child(2) > td:nth-child(5) Category


        #VIPs > tbody > tr:nth-child(7) > td:nth-child(5)
        #VIPs > tbody > tr:nth-child(7) > td:nth-child(2)*/

/*

        int rowCount=driver.findElements(By.xpath("//table[@id='VIPs']/tbody/tr")).size();
        int columnCount=driver.findElements(By.xpath("//table[@id='VIPs']/tbody/tr/td")).size();
*/

    public boolean findUser(User user) {
        users = getUserList();
        for (WebElement we : users) {
            String actualLastName = we.findElement(By.cssSelector("td:nth-child(3)")).getText();
            String actualFirstName = we.findElement(By.cssSelector("td:nth-child(2)")).getText();
            String actualCategory = we.findElement(By.cssSelector("td:nth-child(3)")).getText();
            String actualGender = we.findElement(By.cssSelector("td:nth-child(3)")).getText();
            if (user.getUserFirstName().equals(actualFirstName) &&
                    user.getUserLastName().equals(actualLastName) &&
                    user.getUserCategory().equals(actualCategory) &&
                    user.getUserSex().equals(actualGender)) {
                return true;
            }
        }
        return false;
    }
}

