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

/**
 * Unit test for simple App.
 */
public class App5TrainsTest
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
    @Feature("TC018 Success Search Trains")
    public void TC018_testSuccessSearchTrains(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        navigateToTrainsPage();
        clickLeavingFromTextField();
        selectLeavingFrom();
        selectGoingTo();
        clickHighSpeedCheckbox();
        searchTrainsButton();
        verifySuccessSearchTrains();
        takeScreenshot("TC018_testSuccessSearchTrains");
        resetTrainsPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC019 Failed Search Trains (Empty Field)")
    public void TC019_testFailedSearchTrains(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickLeavingFromTextField();
        emptyLeavingFromTextField();
        clickGoingToTextField();
        emptyGoingToTextField();
        searchTrainsButton();
        clickLeavingFromTextField();
        emptyLeavingFromTextField();
        searchTrainsButton();
        verifyFailedSearchTrains();
        takeScreenshot("TC019_testFailedSearchTrains");
        resetTrainsPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC020 Failed Search Trains (Same Destination)")
    public void TC020_testFailedSearchTrainsSameDestination(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickLeavingFromTextField();
        selectLeavingFrom();
        selectFailGoingTo();
        clickHighSpeedCheckbox();
        searchTrainsButton();
        verifyFailedSearchTrainsSameDestination();
        takeScreenshot("TC020_testFailedSearchTrainsSameDestination");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Step("Navigate to trains page")
    private void navigateToTrainsPage(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement trainsPage = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#header_action_nav_trains")));
        trainsPage.click();
    }
    
    @Step("Click checkbox high speed option")
    private void clickHighSpeedCheckbox(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement highSpeedCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.HighSpeedCheckbox_container__Ru9AV.high-speed > i")));
        highSpeedCheckbox.click();
    }
    
    @Step("Click button search trains")
    private void searchTrainsButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.SearchBtn_container__vR1Vw.search-btn.touchhl-prima")));
        searchButton.click();
    }
    
    // @Step("Clear going to recent searches")
    // private void clearGoingToRecentSearches(){
    //     WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
    //     WebElement clearGoingToRecentSearches = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.station > div:nth-child(3) > div.open-component.trip-seo-search-box-open-component.online > div > div > div > div > div:nth-child(1) > i")));
    //     clearGoingToRecentSearches.click();
    // }

    @Step("Click leaving from text field")
    private void clickLeavingFromTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement leavingFromField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.station > div.CityPicker_container___pdTr.CityPicker_divider__nc3eJ > div > span > input")));
        leavingFromField.click();
    }

    @Step("Empty Leaving From text field")
    private void emptyLeavingFromTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement leavingFromField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.station > div.CityPicker_container___pdTr.CityPicker_divider__nc3eJ > div.open-component.trip-seo-search-box-open-component.online > div > div > input")));
        leavingFromField.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Click Leaving From choice")
    private void selectLeavingFrom() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement leavingFrom = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.station > div.CityPicker_container___pdTr.CityPicker_divider__nc3eJ > div.open-component.trip-seo-search-box-open-component.online > div > div > div > div > ul:nth-child(2) > li:nth-child(1) > span")));
        leavingFrom.click();
    }

    @Step("Click going to text field")
    private void clickGoingToTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement goingToField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.station > div:nth-child(3) > div > span > input")));
        goingToField.click();
    }

    @Step("Empty going to text field")
    private void emptyGoingToTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement goingToField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.station > div:nth-child(3) > div.open-component.trip-seo-search-box-open-component.online > div > div > input")));
        goingToField.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Click Going To choice")
    private void selectGoingTo() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement goingTo = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.station > div:nth-child(3) > div.open-component.trip-seo-search-box-open-component.online > div > div > div > div > ul:nth-child(4) > li:nth-child(2) > span")));
        goingTo.click();
    }

    @Step("Click Same Destination for Going To")
    private void selectFailGoingTo() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement goingTo = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.station > div:nth-child(3) > div.open-component.trip-seo-search-box-open-component.online > div > div > div > div > ul:nth-child(2) > li > span")));
        goingTo.click();
    }

    // @Step("Click search bar")
    // private void search(){
    //     WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
    //     WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibuHeaderSearch > div > div > div > div.gccpoi__TripSearchBox-content > input")));
    //     searchBar.click();
    // }
    
    // @Step("Input search with Input:{input}")
    // private void searchFill(String input){
    //     WebElement emailField=app.getDriver().findElement(By.cssSelector("#ibuHeaderSearch > div > div > div > div.gccpoi__TripSearchBox-content > input"));
    //     emailField.sendKeys(input);
    // }

    @Step("Verify success search trains")
    private void verifySuccessSearchTrains() {
        driver = app.getDriver();
        
        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    
        By successElementSelector = By.cssSelector(
                "#__next > div.pv > div.train-wrapper.train-flex.list-v2 > div.train-content > ul > li:nth-child(1) > h5");
        
        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify failed search trains (empty field)")
    private void verifyFailedSearchTrains() {
        driver = app.getDriver();
        // Wait for the page or element to appear
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.station > div.CityPicker_container___pdTr.CityPicker_divider__nc3eJ > div.CityPicker_msg__N8KHY.CityPicker_warning__ryIVF > span");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
    
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify failed search trains (Same Destination)")
    private void verifyFailedSearchTrainsSameDestination() {
        driver = app.getDriver();
        // Wait for the page or element to appear
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector("body > div.open-component.trip-seo-search-box-open-component.online > div > div > div.Message_header__uKhdm");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
    
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Reset Trains Page")
    private void resetTrainsPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/trains/?locale=en-ID&curr=IDR");
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
