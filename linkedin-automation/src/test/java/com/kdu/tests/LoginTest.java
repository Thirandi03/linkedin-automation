package com.kdu.tests;

import com.kdu.pages.FeedPage;
import com.kdu.pages.LoginPage;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    // Helper method to retrieve environment variables or fallback values
    private String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

@Test(priority = 1)
public void testPositiveLogin() {
    LoginPage loginPage = new LoginPage(driver);

    String user = getEnvOrDefault("LINKEDIN_USER", "thirandiperera10@gmail.com");
    String pass = getEnvOrDefault("LINKEDIN_PASS", "Chaya@1229");

    loginPage.login(user, pass);

    // Wait a brief moment for the post-login redirect to finalize
    try {
        Thread.sleep(3000);
    } catch (InterruptedException ignored) {}

    String currentUrl = driver.getCurrentUrl();

    // 1. If LinkedIn shows an anti-bot challenge/checkpoint, skip gracefully
    if (currentUrl.contains("checkpoint") || currentUrl.contains("challenge") || currentUrl.contains("signup")) {
        throw new SkipException("LinkedIn triggered an anti-bot security checkpoint: " + currentUrl);
    }

    // 2. Flexible feed verification
    FeedPage feedPage = new FeedPage(driver);
    boolean isFeed = currentUrl.contains("feed") || feedPage.isNavBarDisplayed();

    Assert.assertTrue(isFeed, "Login should redirect to the feed or display global navigation. Current URL: " + currentUrl);
}

    @Test(priority = 2)
    public void testNegativeLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid_user@example.com", "WrongPassword123!");

        String error = loginPage.getErrorMessageText();
        Assert.assertFalse(error.isEmpty(),
                "Error message should be displayed for invalid credentials. URL: "
                        + driver.getCurrentUrl());
        Assert.assertFalse(new FeedPage(driver).isOnFeed(),
                "User must not reach the feed with invalid credentials.");
    }
}