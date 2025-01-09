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

public class App6CarRentalTest {
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
    @Feature("TC025 Success Search Car Rental")
    public void TC025_testSuccessSearchCarRental() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        navigateToCarRentalPage();
        selectCarRental();
        clickPickUpLocation();
        selectPickUpLocation();
        clickPickUpDate();
        selectPickUpDate();
        clickPickUpHour();
        selectPickUpHour();
        clickDropOffDate();
        selectDropOffDate();
        clickDropOffHour();
        selectDropOffHour();
        clickDriverLicenceCountry();
        selectDriverLicenceCountry();
        clickAge();
        selectAge();
        searchCarRentalButton();
        verifySuccessSearchCarRental();
        takeScreenshot("TC025_testSuccessSearchCarRental");
        resetCarRentalPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }
    
    @Test
    @Feature("TC026 Success Search Car Rental( Drop Off Different Location )")
    public void TC026_testSuccessSearchCarRentallDropOffDiffLocation() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickDropOffAtDiffLocation();
        clickPickUpLocation();
        selectPickUpLocation();
        clickDropOffLocation();
        selectDropOffLocation();
        clickPickUpDate();
        selectPickUpDate();
        clickPickUpHour();
        selectPickUpHour();
        clickDropOffDate();
        selectDropOffDate();
        clickDropOffHour();
        selectDropOffHour();
        clickDriverLicenceCountry();
        selectDriverLicenceCountry();
        clickAge();
        selectAge();
        searchCarRentalButton();
        verifySuccessSearchCarRentalDiffLocation();
        takeScreenshot("TC026_testSuccessSearchCarRentallDropOffDiffLocation");
        resetCarRentalPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC027 Failed Search Cars Rentals (dont do anything with the field)")
    public void TC027_testFailedSearchCarsRentals() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        searchCarRentalButton();
        verifyFailSearchCarRental();
        takeScreenshot("TC027_testFailedSearchCarsRentals");
        resetCarRentalPage();
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }
    
    @Step("Navigate to cars page")
    private void navigateToCarRentalPage() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement carsPage = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#header_action_nav_cars")));
        carsPage.click();
    }

    @Step("Select Car Rentals")
    private void selectCarRental() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement carRental = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#header_action_nav_Car\\ Rentals")));
        carRental.click();
    }

    @Step("Click Pick Up Location")
    private void clickPickUpLocation() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpLocation = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "#__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div.car-location-form-wrapper > div > div > div > div.car-select.location-picker > div > div")));
        pickUpLocation.click();
    }
      
    @Step("Select Pick Up Location")
    private void selectPickUpLocation() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpLocation = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        " #__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div.car-location-form-wrapper > div > div > div > div.adm-popup.car-dropdown-popup.location-select-item-wrapper > div > div > div > div > div.select-item-wrapper.margin-top-196 > div:nth-child(1) > div.locations-content > div:nth-child(4) > div.car-select-item.select-item-suguest")));
        pickUpLocation.click();
    }


    @Step("Click Drop Off Location")
    private void clickDropOffLocation() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpLocation = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "#__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div.car-location-form-wrapper > div > div:nth-child(3) > div > div > div > div")));
        pickUpLocation.click();
    }
      
    @Step("Select Drop Off Location")
    private void selectDropOffLocation() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpLocation = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        " #__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div.car-location-form-wrapper > div > div:nth-child(3) > div > div.adm-popup.car-dropdown-popup.location-select-item-wrapper > div > div > div > div > div.select-item-wrapper.margin-top-196 > div:nth-child(3) > div.locations-content > div:nth-child(3) > div.car-select-item.select-item-suguest")));
        pickUpLocation.click();
    }

    

    @Step("Click Pick Up Date")
    private void clickPickUpDate() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpDate = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        " #__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div:nth-child(2) > div.date-container")));
        pickUpDate.click();
    }
    
    @Step("Select Pick Up Date")
    private void selectPickUpDate() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpDate = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        " #__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div.calendar-container > div > div.c-calendar__body > div:nth-child(2) > div > ul:nth-child(3) > li:nth-child(6)")));
        pickUpDate.click();
    }
    
    @Step("Click Pick Up Hours")
    private void clickPickUpHour() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpHour = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        " #__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div:nth-child(2) > div.time-container > div > div > div > div > div > div")));
        pickUpHour.click();
    }

    @Step("Click Pick Up Hours")
    private void selectPickUpHour() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpHour = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        " #__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div:nth-child(2) > div.time-container > div > div > div.adm-popup.car-dropdown-popup.time-pickers-popup-wrapper > div > div > div > div:nth-child(23)")));
        pickUpHour.click();
    }

        @Step("Click Drop Off Date")
    private void clickDropOffDate() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement dropOffDate = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        " #__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div:nth-child(3) > div.date-container")));
        dropOffDate.click();
    }
    
    @Step("Select Drop Off Date")
    private void selectDropOffDate() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement dropOffDate = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "#__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div.calendar-container.select-end > div > div.c-calendar__body > div:nth-child(2) > div > ul:nth-child(4) > li:nth-child(6)")));
        dropOffDate.click();
    }
    
    @Step("Click Drop Off Hours")
    private void clickDropOffHour() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement dropOffHour = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        " #__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div:nth-child(3) > div.time-container > div > div > div > div > div > div")));
        dropOffHour.click();
    }

    @Step("Click Drop Off Hours")
    private void selectDropOffHour() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement dropOffHour = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        " #__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div:nth-child(3) > div.time-container > div > div > div.adm-popup.car-dropdown-popup.time-pickers-popup-wrapper > div > div > div > div:nth-child(26)")));
        dropOffHour.click();
    }

    @Step("Click Driver Licence Country")
    private void clickDriverLicenceCountry() {
         WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement driverLicenceCountry = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "#__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div:nth-child(2) > div > div.car-residency-view-wrap.border > div.driver-residency > div > div > div > div")));
        driverLicenceCountry.click();
    }

    @Step("Select Driver Licence Country")
    private void selectDriverLicenceCountry() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement driverLicenceCountry = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "#ibu_region_selector > div > div.country-select > div.country-list.ttb > div.country-list_bottom-box > ul.section-list > li.list-item.selected")));
        driverLicenceCountry.click();
    }

    @Step("Click Age")
    private void clickAge() {
         WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement age = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "#__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div:nth-child(2) > div > div.car-age-view-wrap.border > div.driver-age > div > div > div > div")));
        age.click();
    }

    @Step("Select Age")
    private void selectAge() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement age = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "#__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div:nth-child(2) > div > div.car-age-view-wrap.border > div.driver-age > div.adm-popup.car-dropdown-popup.driver-age-item-wrapper > div > div > div > div:nth-child(4)")));
        age.click();
    }
        
    @Step("Click button search Car Rental")
    private void searchCarRentalButton() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "#__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > button")));
        searchButton.click();
    }

    @Step("Verify Success Search Car Rental")
    private void verifySuccessSearchCarRental() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#sticky-container > div > div.car-page-inner > div > div.car-page-main > div.car-progress > div");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Failed Search Car Rental")
    private void verifyFailSearchCarRental() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#sticky-container > div > div.car-page-inner > div > div.car-page-main > div.car-list-bar-wrapper > div.car-filter-bar > div.car-filter-bar-content > div > div > div > button");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Search Car Rental Drop Off Different Location")
    private void verifySuccessSearchCarRentalDiffLocation() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#sticky-container > div > div.car-page-inner > div > div.car-page-main > div.car-vehicle-list > div:nth-child(1) > div > div > div.car-vendor-price > div:nth-child(1) > div.center-vendor > div > div.car-term-item > div.text.sub-text");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }
    
    @Step("Click Drop Off at Different Location")
    private void clickDropOffAtDiffLocation() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement diffLocation = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "#__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div:nth-child(2) > div > div.car-checkbox.different-location-check-box > div")));
        diffLocation.click();
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
    
    @Step("Reset Car Rental Page")
    private void resetCarRentalPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/carhire/?channelid=14409&locale=en-ID&curr=IDR");
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
