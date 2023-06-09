package at.jku.swtesting;

import org.junit.jupiter.api.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LisssTest
{
    private static WebDriver driver;
    private static final String BASE_URL = "https://lisss.jku.at/primo-explore/search?vid=ULI&lang=en_US";
    private static final String SEARCH_TEXT = "software testing";
    private static final DriverManagerType DRIVER_TYPE = DriverManagerType.CHROME;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.getInstance(DRIVER_TYPE).setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GetConnection();
        Search();
    }
    @AfterAll
    public static void tearDown() {
        driver.close();
        driver.quit();
    }
    private static void GetConnection(){
        driver.get(BASE_URL);
    }
    private static void Search(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchBar")));
        searchField.sendKeys(SEARCH_TEXT);
        searchField.submit();
    }

    @Test
    public void testLisss() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var titleAwaiter = wait.until(ExpectedConditions.titleContains(SEARCH_TEXT));
        if (!titleAwaiter) {
            Assertions.fail("Page title does not contain the search text");
        }
        else{
            Assertions.assertTrue(driver.getTitle().contains(SEARCH_TEXT));
        }
    }
    @Test
    public void testLisss2() throws TimeoutException
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var resultElementAwaiter = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), '4,282')]")));

        if (resultElementAwaiter.getText().split(" ")[2].replace(",", "").equals("4282")) {
            Assertions.assertTrue(true);
        }
        else{
            Assertions.fail("Failed to find results");
        }
    }
}
