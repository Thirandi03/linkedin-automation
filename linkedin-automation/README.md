# LinkedIn Selenium Automation Test Suite

## Project Overview
This project is an automated UI test suite built in Java using Selenium WebDriver and TestNG for LinkedIn. It covers core user flows including positive/negative authentication, global search execution, and search result navigation under the Page Object Model (POM) architectural design pattern.

## Prerequisites
- Java JDK 17 or higher
- Apache Maven
- Google Chrome browser

## Project Structure
- `src/main/java/com/kdu/pages/`: Page Object classes (BasePage, LoginPage, FeedPage, SearchResultsPage)
- `src/test/java/com/kdu/tests/`: TestNG test suites (BaseTest, LoginTest, SearchTest)
- `screenshots/`: Automatically captured failure screenshots

## Environment Configuration
To prevent hardcoding credentials in source code, set the following environment variables on your system:

- `LINKEDIN_USER`: thperera0310@gmail.com
- `LINKEDIN_PASS`: Gangadeepa

If environment variables are not set, fallback values defined inside the test classes will be used.

## Running the Tests
To execute the complete test suite via Maven, run:
```bash
mvn clean test