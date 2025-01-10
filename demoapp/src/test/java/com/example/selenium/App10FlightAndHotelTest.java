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
public class App10FlightAndHotelTest
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
    @Feature("TC052 Search Flight & Hotel (Round-Trip)")
    public void TC052_testSuccessSearchFlightAndHotelRoundTrip(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        navigateToFlightAndHotelPage();
        clickFlightOptionDropdown();
        selectRoundTripOption();
        clickGoingFromTextField();
        emptyGoingFromTextField();
        selectGoingFromCity();
        emptyGoingToTextField();
        selectGoingToCity();
        clickDestinationTextField();
        emptyDestinationTextField();
        selectDestinationCity();
        clickDepartTextField();
        selectDepartTime();
        selectReturnTime();
        clickCheckInTextField();
        selectCheckInTime();
        selectCheckOutTime();
        searchFlightAndHotelButton();
        verifySuccessSearchFlightAndHotelRoundTrip();

        takeScreenshot("TC052_testSuccessSearchFlightAndHotelRoundTrip");
        resetFlightAndHotelPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    // @Test
    // @Feature("TC041 Success Search FlightAndHotels & Tours By FlightAndHotel Name")
    // public void TC041_testSuccessSearchFlightAndHotel(){
    //     String startTime = getCurrentTimestamp();
    //     attachTimestamp("Test Start Time", startTime);

    //     clickCityTextField();
    //     selectCity();
    //     clickFlightAndHotelTextField();
    //     selectFlightAndHotel();
    //     verifySuccessSearchFlightAndHotel();
    //     takeScreenshot("TC041_testSuccessSearchFlightAndHotel");
    //     //resetFlightAndHotelPage();

    //     String endTime = getCurrentTimestamp();
    //     attachTimestamp("Test End Time", endTime);
    // }

    @Step("Navigate to Flight & Hotel page")
    private void navigateToFlightAndHotelPage(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement flightAndHotelPage = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#header_action_nav_packages")));
        flightAndHotelPage.click();
    }

    @Step("Click button search Flight & Hotel")
    private void searchFlightAndHotelButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.ibu-bundle-container > div.top > div.search-bar > div > div.fh-searchForm-content > div.right > div.ic_button")));
        searchButton.click();
    }
    
    // @Step("Clear going to recent searches")
    // private void clearGoingToRecentSearches(){
    //     WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
    //     WebElement clearGoingToRecentSearches = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.station > div:nth-child(3) > div.open-component.trip-seo-search-box-open-component.online > div > div > div > div > div:nth-child(1) > i")));
    //     clearGoingToRecentSearches.click();
    // }

    @Step("Click Going From text field")
    private void clickGoingFromTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement CityTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#from")));
        CityTextField.click();
    }

    @Step("Empty Going From text field")
    private void emptyGoingFromTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement CityTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#from")));
        CityTextField.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Select Going From city")
    private void selectGoingFromCity() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement city = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.m-flight-poi-wrapper.overflow-scroll.mt-4.fh-bundle-poi.is-hotcity > ul > li:nth-child(1) > ul > li:nth-child(1) > span")));
        city.click();
    }

    @Step("Click Going To text field")
    private void clickGoingToTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement goingToTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#to")));
        goingToTextField.click();
    }

    @Step("empty Going To text field")
    private void emptyGoingToTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement goingToTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#to")));
        goingToTextField.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Select Going To city")
    private void selectGoingToCity() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement goingTo = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.m-flight-poi-wrapper.overflow-scroll.mt-4.fh-bundle-poi.is-hotcity > ul > li:nth-child(1) > ul > li:nth-child(2) > span")));
        goingTo.click();
    }

    @Step("Click Destination text field")
    private void clickDestinationTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement destinationTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hotelCity")));
        destinationTextField.click();
    }

    @Step("empty Destination text field")
    private void emptyDestinationTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement destinationTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#hotelCity")));
        destinationTextField.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Select Destination city")
    private void selectDestinationCity() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement destination = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.fhs-hotel-wrapper > div > div > div.group-content > div:nth-child(1)")));
        destination.click();
    }

    @Step("Click Depart text field")
    private void clickDepartTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement departTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#depart")));
        departTextField.click();
    }

    @Step("Select Depart Time")
    private void selectDepartTime() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement depart = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.c-calendar.fh-bundle-package-calendar > div.c-calendar__body > div:nth-child(2) > div.c-calendar-month__days > ul:nth-child(3) > li:nth-child(4) > span")));
        depart.click();
    }

    @Step("Select Return Time")
    private void selectReturnTime() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement depart = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.c-calendar.fh-bundle-package-calendar > div.c-calendar__body > div:nth-child(2) > div.c-calendar-month__days > ul:nth-child(3) > li:nth-child(6)")));
        depart.click();
    }

    @Step("Click Check-In text field")
    private void clickCheckInTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement checkInTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#checkIn")));
        checkInTextField.click();
    }

    @Step("Select Check-In Time")
    private void selectCheckInTime() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement checkIn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.c-calendar.fh-bundle-package-calendar > div.c-calendar__body > div:nth-child(1) > div.c-calendar-month__days > ul:nth-child(3) > li.is-selected.is-selected-first.is-allow-hover > span")));
        checkIn.click();
    }

    @Step("Select Check-Out Time")
    private void selectCheckOutTime() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement checkOut = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.c-calendar.fh-bundle-package-calendar > div.c-calendar__body > div:nth-child(1) > div.c-calendar-month__days > ul:nth-child(3) > li.is-selected.is-selected-last.is-allow-hover > span")));
        checkOut.click();
    }

    @Step("Click Flight Option Dropdown")
    private void clickFlightOptionDropdown() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement flightoption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.ibu-bundle-container > div.top > div.search-bar > div > div.fh-searchForm-header > div > div.fh-sf-trigger.fh-sf-drop-down.fh-sf-dropdown-trigger.fh-trip-way > div")));
        flightoption.click();
    }

    @Step("Select Round-Trip Option")
    private void selectRoundTripOption() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement roundTrip = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.fh-sf-trigger-popup > div > div.fh_dropdown_menu_option.checked > label")));
        roundTrip.click();
    }

    @Step("Select One-Way Option")
    private void selectOneWayOption() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement oneWay = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.fh-sf-trigger-popup > div > div:nth-child(1) > label")));
        oneWay.click();
    }

    @Step("Click people and room dropdown")
    private void clickPeopleAndRoomDropdown(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement peopleAndRoomDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.ibu-bundle-container > div.top > div.search-bar > div > div.fh-searchForm-header > div > div:nth-child(2) > div > div")));
        peopleAndRoomDropdown.click();
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

    @Step("Verify success search FlightAndHotels & Tours (Round-Trip)")
    private void verifySuccessSearchFlightAndHotelRoundTrip() {
        driver = app.getDriver();
        
        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    
        By successElementSelector = By.cssSelector(
                "#__next > div.jj-list-container > div:nth-child(5) > div > div.jj-right.pt16 > div.bg7.jj-flight.mb16.pt12 > div:nth-child(1) > div.bg7.jj-flight-item.plr24.pb12.pt12 > div > div > div > a:nth-child(2)");
        
        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    // @Step("Verify success search FlightAndHotels & Tours By FlightAndHotel Name")
    // private void verifySuccessSearchFlightAndHotelOneWay() {
    //     driver = app.getDriver();
        
    //     // Wait for the new tab or page to load
    //     WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
    //     // Switch to the newly opened tab if applicable
    //     for (String handle : driver.getWindowHandles()) {
    //         driver.switchTo().window(handle);
    //     }
    
    //     By successElementSelector = By.cssSelector(
    //             "#poi\\.detail\\.overview > div > div.TopBoxStyle-sc-2jpvue-4.cKCoNt > div.top-left > div.top-box > div > div.poi-page-title > h1");
        
    //     // Wait for the element to be visible
    //     WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        
    //     // Assert the element is displayed
    //     Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    // }

    @Step("Reset Flight & Hotel Page")
    private void resetFlightAndHotelPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/packages/?locale=en-ID&curr=IDR");
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
