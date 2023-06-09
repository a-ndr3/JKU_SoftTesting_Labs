package at.jku.swtesting.pageobjects;

import org.openqa.selenium.WebDriver;

public class ResultsPage {
	
	protected WebDriver driver;
	
	public ResultsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getTitle() {
		return driver.getTitle();
	}
	
}