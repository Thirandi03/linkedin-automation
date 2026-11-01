package com.kdu.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private final By usernameField = By.cssSelector("input[type='email'][autocomplete*='username']");
    private final By passwordField = By.cssSelector("input[type='password'][autocomplete*='password']");
    private final By signInButton  = By.xpath("//button[normalize-space()='Sign in']");
    // Covers both inline field-level errors and the global alert banner LinkedIn shows
    private final By errorMessage  = By.cssSelector("[role='alert'], [id^='error-for'], .alert-content");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // The page contains a hidden duplicate of each control, so return the displayed one.
    private WebElement visible(By locator) {
        return wait.until(d -> {
            List<WebElement> els = d.findElements(locator);
            for (WebElement e : els) {
                if (e.isDisplayed()) {
                    return e;
                }
            }
            return null;
        });
    }

    public void enterUsername(String username) {
        WebElement el = visible(usernameField);
        el.click();
        el.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement el = visible(passwordField);
        el.click();
        el.sendKeys(password);
    }

    public void clickSignIn() {
        visible(signInButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignIn();
    }

    /**
     * Returns the text of the first visible error message, or an empty string if
     * none appears within the wait timeout. Never throws.
     */
    public String getErrorMessageText() {
        try {
            WebElement el = visible(errorMessage);
            return el == null ? "" : el.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
}
