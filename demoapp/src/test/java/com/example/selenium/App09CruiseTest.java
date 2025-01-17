package com.example.selenium;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Feature;

import java.nio.file.Files;

public class App09CruiseTest {
    private AppProject app;
    private static WebDriver driver;

    @BeforeClass
    @Description("Setup browser and open application using url")
    public void setUp() {
        app = new AppProject();
        app.openURL("https://trip.com/");
        driver = app.getDriver();
    }
    
    @Test
    @Feature("TC047 Success Search Cruise (with Input)")
    public void TC047_testSuccessSearchCruiseWithInput() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        navigateToCruisePage();
        clickDepartFrom();
        selectDepartFrom();
        //clickSailFor();
        //selectSailFor();
        clickMonthOfDeparture();
        selectMonthOfDeparture();
        searchCruiseButton();
        verifySuccessSearchCruiseWithInput();
        takeScreenshot("TC047_testSuccessSearchCruiseWithInput");
        resetCruisePage();
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC048 Success Search Cruise No Input")
    public void TC048_testSuccessSearchCruiseNoInput() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        searchCruiseButton();
        verifySuccessSearchCruiseNoInput();
        takeScreenshot("TC048_testSuccessSearchCruiseNoInput");
        resetCruisePage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Step("Navigate to cruise page")
    private void navigateToCruisePage() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement cruisePage = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#header_action_nav_cruises")));
        cruisePage.click();
    }

    @Step("Click Depart From")
    private void clickDepartFrom() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(100));
        WebElement departFrom = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibucru-10650039176-selectionBox-DEPARTURE_CITY_IDS > div.input-box-component_base_input_container.blur > div")));
        departFrom.click();
        delay(1000);
    }

    @Step("Select Depart From")
    private void selectDepartFrom() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(100));
        WebElement departFrom = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.cruise-container > div.home-head > div.cruise-content.top-banner > div > div > ul > li:nth-child(1) > div.select-container > div.drop-container > div > div:nth-child(4) > span")));
        departFrom.click();
        delay(1000);
    }

    @Step("Click Sail For")
    private void clickSailFor() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(100));
        WebElement sailFor = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='cru-form-select-text'])[2]")));
        sailFor.click();
        delay(1000);
    }

    @Step("Select Sail For")
    private void selectSailFor() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(100));
        WebElement sailFor = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.cruise-container > div.home-head > div.cruise-content.top-banner > div > div > ul > li:nth-child(2) > div.select-container > div.drop-container > div > div:nth-child(2) > span")));
        sailFor.click();
        delay(1000);
    }

    @Step("Click Month of Departure")
    private void clickMonthOfDeparture() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(100));
        WebElement monthOfDeparture = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class=\"cru-form-select-text\"])[3]")));
        monthOfDeparture.click();
        delay(1000);
    }

    @Step("Select Month of Departure")
    private void selectMonthOfDeparture() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement monthOfDeparture = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "#__next > div.cruise-container > div.home-head > div.cruise-content.top-banner > div > div > ul > li:nth-child(3) > div.select-container > div.drop-container > div > div:nth-child(3) > span")));
        monthOfDeparture.click();
        delay(1000);
    }
    
    @Step("Click button search Cruise")
    private void searchCruiseButton() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "#__next > div.cruise-container > div.home-head > div.cruise-content.top-banner > div > div > ul > li.search-form-btn > button")));
        searchButton.click();
    }

    @Step("Take Screenshot")
    private void takeScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        // Define destination path without timestamp
        String destination = "screenshots/" + testName + ".png";

        // Ensure the "screenshots" directory exists
        File screenshotDir = new File("screenshots");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }

        File destinationFile = new File(destination);

        try {
            // If a file with the same name exists, overwrite it
            FileUtils.copyFile(source, destinationFile);
            System.out.println("Screenshot saved to: " + destination);

            // Attach screenshot to Allure report
            attachScreenshot(destinationFile);
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }
    }
    
    @Step("Reset Cruise Page")
    private void resetCruisePage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/cruises?locale=en-ID&curr=IDR");
    }

    @Attachment(value = "{description}", type = "text/plain")
    public String attachTimestamp(String description, String timestamp) {
        return timestamp;
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] attachScreenshot(File screenshotFile) {
        try {
            return Files.readAllBytes(screenshotFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Step("Verify Success Search Cruise With Input")
    private void verifySuccessSearchCruiseWithInput() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div.cruise-container > div.cruise-list-page > div.cruise-content > div > div.list-main > div.filter-result-box > div.filter-result > div.result-count-container > div > span");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Search Cruise No Input")
    private void verifySuccessSearchCruiseNoInput() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div.cruise-container > div.cruise-list-page > div.cruise-content > div > div.list-main > div.filter-result-box > div.filter-result > div > div > span");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    @Description("Close Browser")
    public void tearDown(){
        app.closBrowser();
    }
}
