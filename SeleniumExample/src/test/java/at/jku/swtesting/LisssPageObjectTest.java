package at.jku.swtesting;

import at.jku.swtesting.pageobjects.SimpleResultsPage;
import at.jku.swtesting.pageobjects.SimpleSearchPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class LisssPageObjectTest {
    private static WebDriver driver;
    private static SimpleSearchPage simpleSearchPage;
    private static SimpleResultsPage resultsPage;
    private static final String BASE_URL = "https://lisss.jku.at/primo-explore/search?vid=ULI&lang=en_US";
    private static final String SEARCH_TEXT = "software testing";
    private static final DriverManagerType DRIVER_TYPE = DriverManagerType.CHROME;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.getInstance(DRIVER_TYPE).setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(BASE_URL);
        simpleSearchPage = new SimpleSearchPage(driver,"searchBar");
        resultsPage = new SimpleResultsPage(driver);
    }

    @AfterAll
    public static void tearDown() {
        driver.close();
        driver.quit();
    }

    @Test
    public void testLisss() {
        simpleSearchPage.searchFor(SEARCH_TEXT);
        Assertions.assertTrue(resultsPage.getTitle().contains(SEARCH_TEXT), "Page title does not contain the search text");
    }

    @Test
    public void testLisss2() {
        String numberOfResults = resultsPage.getNumberOfResults();
        Assertions.assertEquals("4282", numberOfResults, "Failed to find results");
    }
}
