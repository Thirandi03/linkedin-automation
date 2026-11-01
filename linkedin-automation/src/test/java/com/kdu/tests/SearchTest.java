package com.kdu.tests;

import com.kdu.pages.FeedPage;
import com.kdu.pages.LoginPage;
import com.kdu.pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    private String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

    private FeedPage performLogin() {
        LoginPage loginPage = new LoginPage(driver);
        String user = getEnvOrDefault("LINKEDIN_USER", "thirandiperera10@gmail.com");
        String pass = getEnvOrDefault("LINKEDIN_PASS", "Chaya@1229");
        loginPage.login(user, pass);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {}

        String url = driver.getCurrentUrl();
        if (url.contains("checkpoint") || url.contains("challenge")) {
            throw new SkipException("Skipping search test: Anti-bot security checkpoint triggered at " + url);
        }

        return new FeedPage(driver);
    }

    @Test(priority = 1)
    public void testAutomateSearch() {
        FeedPage feedPage = performLogin();
        SearchResultsPage resultsPage = feedPage.searchFor("Software Testing");

        Assert.assertTrue(resultsPage.areResultsDisplayed(), 
                "Search results page should be displayed for the query.");
    }

    @Test(priority = 2)
    public void testClickSearchResult() {
        FeedPage feedPage = performLogin();
        SearchResultsPage resultsPage = feedPage.searchFor("Software Testing");

        Assert.assertTrue(resultsPage.areResultsDisplayed(), "Search results should appear before clicking.");
        resultsPage.clickFirstResult();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("in/") || currentUrl.contains("company/") || currentUrl.contains("search/results"), 
                "Clicking search result should navigate to relevant page. Current URL: " + currentUrl);
    }
}