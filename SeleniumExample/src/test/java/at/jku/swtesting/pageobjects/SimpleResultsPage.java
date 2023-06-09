package at.jku.swtesting.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SimpleResultsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final String RESULTS_XPATH = "//span[contains(text(), '4,282')]";

    public SimpleResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getNumberOfResults() {
        WebElement resultElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(RESULTS_XPATH)));
        return resultElement.getText().split(" ")[2].replace(",", "");
    }

    public String getTitle(){
        return driver.getTitle();
    }
    public String getNumberOfResults(String xPath) {
        WebElement resultElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
        return resultElement.getText().split(" ")[2].replace(",", "");
    }
}
