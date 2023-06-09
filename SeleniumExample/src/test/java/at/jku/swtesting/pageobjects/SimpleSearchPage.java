package at.jku.swtesting.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SimpleSearchPage {
    private final WebDriverWait wait;
    private final String SEARCH_BAR_ID;

    public SimpleSearchPage(WebDriver driver, String SEARCH_BAR_ID) {
        this.SEARCH_BAR_ID = SEARCH_BAR_ID;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void searchFor(String searchText) {
        WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(SEARCH_BAR_ID)));
        searchField.sendKeys(searchText);
        searchField.submit();
    }
}
