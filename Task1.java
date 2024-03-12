import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;

public class Task1
{
    private WebDriver driver;
    private String username = "standard_user";
    private String passWord = "secret_sauce";

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);

        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(passWord);
        driver.findElement(By.id("login-button")).click();
    }

    @Test
    public void addItemToCart() {
        String itemName = "Sauce Labs Bike Light";
        String button="//div[contains(.,\"%s\")]//preceding::div/following-sibling::div/button";
        String itemButton = String.format(button, itemName);

        driver.findElement(By.xpath(itemButton)).click();
        //Can't use sleep without try and catch
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.xpath("//a[@class=\"shopping_cart_link\"]")).click();

        String xpathExpression = String.format("//div[@class=\"cart_item\"][contains(., \"%s\")]", itemName);
        List<WebElement> items = driver.findElements(By.xpath(xpathExpression));

        if (items.isEmpty())
        {
            System.out.println("Item not in here");
        }
        else
        {
            System.out.println("Item is here");
        }
    }
    @AfterTest
    public void quit(){
        driver.close();
    }
}
