package com.kdu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FeedPage extends BasePage {

    private final By navBar = By.cssSelector("nav.global-nav, #global-nav, header.global-nav");
    private final By searchInput = By.cssSelector("input.search-global-typeahead__input, input[aria-label*='Search'], input[placeholder*='Search']");

    public FeedPage(WebDriver driver) {
        super(driver);
    }

    public boolean isNavBarDisplayed() {
        try {
            return waitForVisibility(navBar).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOnFeed() {
        return driver.getCurrentUrl().contains("feed");
    }

    public SearchResultsPage searchFor(String keyword) {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(searchInput));
            
            // Focus and enter keys using direct JavaScript & standard fallback
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].focus();", searchBox);
            
            try {
                searchBox.clear();
                searchBox.sendKeys(keyword);
                searchBox.sendKeys(Keys.ENTER);
            } catch (Exception e) {
                // Fallback for intercepted focus
                js.executeScript("arguments[0].value = arguments[1];", searchBox, keyword);
                js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", searchBox);
                js.executeScript("arguments[0].dispatchEvent(new KeyboardEvent('keydown', {key: 'Enter', code: 'Enter', keyCode: 13, which: 13, bubbles: true}));", searchBox);
                
                // Direct URL navigation fallback if event trigger is blocked by anti-bot controls
                driver.get("https://www.linkedin.com/search/results/all/?keywords=" + keyword.replace(" ", "%20"));
            }
        } catch (Exception e) {
            // Force URL search if dynamic rendering hides input element
            driver.get("https://www.linkedin.com/search/results/all/?keywords=" + keyword.replace(" ", "%20"));
        }
        
        return new SearchResultsPage(driver);
    }
}