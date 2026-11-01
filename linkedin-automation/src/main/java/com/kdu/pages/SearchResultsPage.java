package com.kdu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultsPage extends BasePage {

    // Locators for search results container and result cards
    private final By resultsList = By.cssSelector("div.search-results-container, ul.reusable-search__entity-result-list");
    private final By firstResultTitle = By.xpath("(//ul[contains(@class,'reusable-search__entity-result-list')]//a[contains(@class,'app-aware-link')])[1]");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean areResultsDisplayed() {
        try {
            return waitForVisibility(resultsList).isDisplayed();
        } catch (Exception e) {
            // Fallback: check if the page loaded search results via URL or main container
            return driver.getCurrentUrl().contains("search/results");
        }
    }

    public void clickFirstResult() {
        try {
            WebElement element = waitForElementToBeClickable(firstResultTitle);
            element.click();
        } catch (Exception e) {
            // JS click fallback in case of intercepting overlays
            WebElement element = driver.findElement(firstResultTitle);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }
}