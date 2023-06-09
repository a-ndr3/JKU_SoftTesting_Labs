package at.jku.swtesting.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {
	
	protected WebDriver driver;

	@FindBy(name = "q") WebElement searchField;
	@FindBy(css = "#L2AGLb") WebElement confirmButton;
	
	public SearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public ResultsPage searchFor(String searchText) {
		searchField.clear();
		searchField.sendKeys(searchText);
		searchField.submit();
		return new ResultsPage(driver);
	}
	
	public void confirmTerms() {
		confirmButton.click();
	}
	
}