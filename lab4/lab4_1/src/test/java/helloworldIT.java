import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;


public class helloworldIT {
    static final Logger log = getLogger(lookup().lookupClass());
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void test() {
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        Actions actions = new Actions(driver);
        driver.get(sutUrl);

        WebElement slowCalc = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.linkText("Slow calculator")));
        actions.moveToElement(slowCalc).click().perform();
        
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html"));

        String title = driver.getCurrentUrl();
        assertThat(title).isEqualTo("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");
    }

}
