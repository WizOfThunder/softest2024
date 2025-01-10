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

/**
 * Unit test for simple App.
 */
public class App04FlightTest
{
    private AppProject app;
    private static WebDriver driver;

     @BeforeClass
     @Description("Setup browser and open application using url")
    public void setUp(){
        app = new AppProject();
        app.openURL("https://trip.com/");
        driver = app.getDriver();
    }

    @Test
    @Feature("TC011 Success Search Flight")
    public void TC011_testSuccessSearchFlight(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        navigateToFlightPage();
        clickOneWay();
        clearLeavingFromTextField();
        clickLeavingFromTextField();
        selectLeavingFrom("Jakarta");
        goingToTextField();
        selectGoingTo("Bali");
        searchFlightButton();
        verifySuccessSearchFlight();
        takeScreenshot("TC011_testSuccessSearchFlight");
        resetFlightPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC012 Failed Search Flight (Empty Field)")
    public void TC012_testFailedSearchFlight(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickOneWay();
        clearLeavingFromTextField();
        searchFlightButton();
        verifyFailedSearchFlight();
        takeScreenshot("TC012_testFailedSearchFlight");
        resetFlightPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC013 Failed Search Flight (Same Destination)")
    public void TC013_testFailedSearchFlightSameDestination(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickOneWay();
        clearLeavingFromTextField();
        clickLeavingFromTextField();
        selectLeavingFrom("Jakarta");
        goingToTextField();
        selectGoingTo("Jakarta");
        searchFlightButton();
        verifyFailedSearchFlightSameDestination();
        takeScreenshot("TC013_testFailedSearchFlightSameDestination");
        resetFlightPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Step("Navigate to flight page")
    private void navigateToFlightPage(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement flightPage = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#header_action_nav_flights")));
        flightPage.click();
    }
    
    @Step("Click one way option")
    private void clickOneWay(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement oneWayOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#main > div.new-index-container > div.top-wrapper > div.inner-wrapper > div.searchForm-wrapper > div > div > div > form > div > div.nh_filterPanel > div > div:nth-child(2) > i")));
        oneWayOption.click();
    }
    
    @Step("Click button search flight")
    private void searchFlightButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#main > div.new-index-container > div.top-wrapper > div.inner-wrapper > div.searchForm-wrapper > div > div > div > form > div > div:nth-child(2) > div.nh_searchPanel > div.nh_sp-btn2")));
        searchButton.click();
    }
    
    @Step("Clear leaving from text field")
    private void clearLeavingFromTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement clearLeavingFromField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#main > div.new-index-container > div.top-wrapper > div.inner-wrapper > div.searchForm-wrapper > div > div > div > form > div > div:nth-child(2) > div.m-searchForm__wrapper.m-searchForm__single-wrapper.m-searchForm__new-version > div > div > ul > li.m-searchForm__item.segment-city.flex > div.segment-info-wrapper.flex > div:nth-child(1) > div > div > div > div > span > i")));
        clearLeavingFromField.click();
    }

    @Step("Click leaving from text field")
    private void clickLeavingFromTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement leavingFromField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#main > div.new-index-container > div.top-wrapper > div.inner-wrapper > div.searchForm-wrapper > div > div > div > form > div > div:nth-child(2) > div.m-searchForm__wrapper.m-searchForm__single-wrapper.m-searchForm__new-version.m-searchForm__oneway-wrapper > div > div > ul > li.m-searchForm__item.segment-city.flex > div.segment-info-wrapper.flex > div:nth-child(1) > div > div > div")));
        leavingFromField.click();
    }

    @Step("Click Leaving From choice")
    private void selectLeavingFrom(String city) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement leavingFrom = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(/html/body/div[4]//*[contains(text(), '" + city + "')])[1]")));
        leavingFrom.click();
    }

    @Step("Click going to text field")
    private void goingToTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement goingToField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#main > div.new-index-container > div.top-wrapper > div.inner-wrapper > div.searchForm-wrapper > div > div > div > form > div > div:nth-child(2) > div.m-searchForm__wrapper.m-searchForm__single-wrapper.m-searchForm__new-version.m-searchForm__oneway-wrapper > div > div > ul > li.m-searchForm__item.segment-city.flex > div.segment-info-wrapper.flex > div:nth-child(3) > div > div > div > div")));
        goingToField.click();
    }

    @Step("Click Going To choice")
    private void selectGoingTo(String city) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement goingTo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(/html/body/div[4]//*[contains(text(), '" + city + "')])[1]")));
        goingTo.click();
    }

    // @Step("Click the same destination for Going To")
    // private void selectFailGoingTo() {
    //     WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
    //     // Ensure the dropdown options are clickable
    //     WebElement goingTo = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.m-flight-poi-wrapper.mt-4.nh_poi-container > div.nh_poi-hotcities > div.nh_poi-popular-container > div:nth-child(1) > div.nh_poi-city-items > div:nth-child(1) > div > div > div > span")));
    //     goingTo.click();
    // }

    // @Step("Click search bar")
    // private void search(){
    //     WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
    //     WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibuHeaderSearch > div > div > div > div.gccpoi__TripSearchBox-content > input")));
    //     searchBar.click();
    // }
    
    @Step("Input search with Input:{input}")
    private void searchFill(String input){
        WebElement emailField=app.getDriver().findElement(By.cssSelector("#ibuHeaderSearch > div > div > div > div.gccpoi__TripSearchBox-content > input"));
        emailField.sendKeys(input);
    }

    @Step("Verify success search flight")
    private void verifySuccessSearchFlight() {
        driver = app.getDriver();
        
        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    
        By successElementSelector = By.cssSelector(".m-common-modal-toast__withIcon.is-center");
        
        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success message or profile element not displayed!");
    }

    @Step("Verify failed search flight (empty field)")
    private void verifyFailedSearchFlight() {
        driver = app.getDriver();
        // Wait for the page or element to appear
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector("#main > div.new-index-container > div.top-wrapper > div.inner-wrapper > div.searchForm-wrapper > div > div > div > form > div > div:nth-child(2) > div.m-searchForm__wrapper.m-searchForm__single-wrapper.m-searchForm__new-version.m-searchForm__oneway-wrapper > div > div > ul > li.m-searchForm__item.segment-city.flex > div.segment-info-wrapper.flex > div.m-searchForm__module.err-msg.nh_fixWidth");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
    
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success message or profile element not displayed!");
    }
    
    @Step("Verify failed search flight (Same Destination)")
    private void verifyFailedSearchFlightSameDestination() {
        driver = app.getDriver();
        // Wait for the page or element to appear
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector("#main > div.new-index-container > div.top-wrapper > div.inner-wrapper > div.searchForm-wrapper > div > div > div > form > div > div:nth-child(2) > div.m-searchForm__wrapper.m-searchForm__single-wrapper.m-searchForm__new-version.m-searchForm__oneway-wrapper > div > div > ul > li.m-searchForm__item.segment-city.flex > div.segment-info-wrapper.flex > div.m-searchForm__module.err-msg.nh_fixWidth");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
    
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success message or profile element not displayed!");
    }

    @Step("Reset Flight Page")
    private void resetFlightPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/flights/?locale=en-ID&curr=IDR");
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

    @AfterClass
    @Description("Close Browser")
    public void tearDown(){
        app.closBrowser();
    }
}
