package PageFactory;

import entities.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

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
        return userList.size() - 1;
    }

    public List<WebElement> getUserList() {
        List<WebElement> allUsers = userList;
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

    public User selectGetRandomUser() {
        users = getUserList();
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(getUserCount());
        users.get(randomInt).findElement(By.id("VIP")).click();
        return new User(
                users.get(randomInt).findElement(By.cssSelector("td:nth-child(2)")).getText(),
                users.get(randomInt).findElement(By.cssSelector("td:nth-child(3)")).getText(),
                users.get(randomInt).findElement(By.cssSelector("td:nth-child(4)")).getText(),
                users.get(randomInt).findElement(By.cssSelector("td:nth-child(5)")).getText());
    }

    public boolean isUserPresentedOnPage(User user) {
        users = getUserList();
        for (WebElement we : users) {
            String actualLastName = we.findElement(By.cssSelector("td:nth-child(3)")).getText();
            String actualFirstName = we.findElement(By.cssSelector("td:nth-child(2)")).getText();
            String actualCategory = we.findElement(By.cssSelector("td:nth-child(5)")).getText();
            String actualGender = we.findElement(By.cssSelector("td:nth-child(4)")).getText();
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

