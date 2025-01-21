package com.example.selenium;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Feature;

import java.nio.file.Files;

public class App06CarRentalTest {
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
    @Feature("TC026 Success Search Car Rental")
    public void TC026_testSuccessSearchCarRental() {
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
        takeScreenshot("TC026_testSuccessSearchCarRental");
        resetCarRentalPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }
    
    @Test
    @Feature("TC027 Success Search Car Rental( Drop Off Different Location )")
    public void TC027_testSuccessSearchCarRentallDropOffDiffLocation() {
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
        takeScreenshot("TC027_testSuccessSearchCarRentallDropOffDiffLocation");
        resetCarRentalPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC028 Failed Search Cars Rentals (dont do anything with the field)")
    public void TC028_testFailedSearchCarsRentals() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        clickPickUpLocationTextField();
        emptyPickUpLocationTextField();
        searchCarRentalButton();
        verifyFailSearchCarRental();
        takeScreenshot("TC028_testFailedSearchCarsRentals");
        resetCarRentalPage();
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }
    
    @Test
    @Feature("TC029 Failed book Transfer Car (Empty Field)")
    public void TC029_FailedBookCarRentalsEmptyField() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        setUpBook();
        selectCars();
        delay(10000);
        inputBookingField("", "", "" , "","",1);
        clickBookNow();
        verifyFailedBookCarEmptyField();
        takeScreenshot("TC029_FailedBookCarRentalsEmptyField");
        resetCarRentalPage();
        

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }
    
    @Test
    @Feature("TC030 Failed book Transfer Car (Invalid Email)")
    public void TC030_FailedBookCarRentalsInvalidEmail() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        setUpBook();
        selectCars();
        delay(10000);
        inputBookingField("akun", "istts","" , "85246880674", "UA035",2);
        clickBookNow();
        verifyFailedBookCarInvalidEmail();
        takeScreenshot("TC030_FailedBookCarRentalsInvalidEmail");
        resetCarRentalPage();
               
       
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC031 Failed book Transfer Car (Invalid Phone Number)")
    public void TC031_FailedBookCarRentalsInvalidPhone() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        setUpBook();
        selectCars();
        delay(10000);
        inputBookingField("akun", "istts","akunistts@gmail.com" , "0", "UA035",3);
        clickBookNow();
        verifyFailedBookCarInvalidPhone();
        takeScreenshot("TC031_FailedBookCarRentalsInvalidPhone");
        resetCarRentalPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }
    
    @Test
    @Feature("TC032 Failed book Transfer Car (Invalid Flight Number)")
    public void TC032_FailedBookCarRentalsInvalidFlight() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        setUpBook();
        selectCars();
        delay(10000);
        inputBookingField("akun", "istts", "akunistts@gmail.com", "85246888888", "U", 4);
        clickBookNow();
        verifyFailedBookCarInvalidFlight();
        takeScreenshot("TC032_FailedBookCarRentalsInvalidFlight");
        resetCarRentalPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }
    
    @Test
    @Feature("TC033 Success book Transfer Car")
    public void TC033_SuccessBookCarRentals() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        navigateToCarRentalPage();
        selectCarRental();
        setUpBook();
        selectCars();
        delay(10000);
        inputBookingField("akun", "istts","akunistts@gmail.com" , "85246888888", "UA035",1);
        clickBookNow();
        verifyFailedBookCarInvalidFlight();
        takeScreenshot("TC033_SuccessBookCarRentals");
        // resetCarRentalPage();

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

    @Step("Click Pick Up Text Field")
    private void clickPickUpLocationTextField() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpTextField = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "#__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div.car-location-form-wrapper > div > div > div > div > div > div > button > span > div > input")));
        pickUpTextField.click();
    }

    @Step("Empty Pick Up Text Field")
    private void emptyPickUpLocationTextField() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpTextField = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        "#__next > div.car-search-form-wrapper > div > div.title-wrapper > div.car-card.large.gray.car-search-form > div.car-search-content > div.car-location-form-wrapper > div > div > div > div > div > div > button > span > div > input")));
        pickUpTextField.sendKeys(Keys.BACK_SPACE);
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
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//*[@class='car-button car-button-hover car-button-default car-button-shape-default search-button']")));
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
                ".car-toast-inner.car-ani.fade-in-down");

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
    
    @Step("Verify Fail Book Car Rental Empty Field")
    private void verifyFailedBookCarEmptyField() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div.car-booking-page > div.left > div:nth-child(3) > div.car-card.large.white.car-booking-driver-wrapper.card > div.driver-form.card-content > div:nth-child(1) > div.car-input-error-info > span");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Fail Book Car Rental Invalid Email")
    private void verifyFailedBookCarInvalidEmail() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div.car-booking-page > div.left > div:nth-child(3) > div.car-card.large.white.car-booking-driver-wrapper.card > div.driver-form.card-content > div:nth-child(4) > div.car-input-error-info > span");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Fail Book Car Transfer Invalid Phone")
    private void verifyFailedBookCarInvalidPhone() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div.car-booking-page > div.left > div:nth-child(3) > div.car-card.large.white.car-booking-driver-wrapper.card > div.driver-form.card-content > div.car-input.car-mobile-wrap > div.car-mobile-error > div.car-input-error-info > span");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Fail Book Car Transfer Invalid Phone")
    private void verifyFailedBookCarInvalidFlight() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div.car-booking-page > div.left > div:nth-child(3) > div.car-card.large.white.car-booking-driver-wrapper.card > div.driver-form.card-content > div:nth-child(7) > div.car-input-error-info > span");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Book Car Transfer")
    private void verifySuccessBookCar() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div.car-booking-page > div.left > div:nth-child(3) > div.car-card.large.white.car-booking-driver-wrapper.card > div.driver-form.card-content > div:nth-child(7) > div.car-input-error-info > span");

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

    @Step("Set Up")
    private void setUpBook() {
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
    }

    @Step("Select Cars")
    private void selectCars() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement bookCar = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#sticky-container > div > div.car-page-inner > div > div.car-page-main > div.car-vehicle-list > div:nth-child(1) > div > div > div.car-vendor-price > div > div.right-vendor > div > button")));
        bookCar.click();
    }

    @Step("Click Book")
    private void clickBookNow() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(30));
        WebElement book = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector(
                                "#__next > div.car-booking-page > div.left > div:nth-child(3) > div.car-card.large.white.bookContainer > div:nth-child(2) > button")));
        Actions actions = new Actions(app.getDriver());
        actions.moveToElement(book).perform();
        book.click();
    }

    @Step("input Booking Field")
    private void inputBookingField(String firstName, String lastName, String email, String phone, String flightNumber, int index) {

        List<String> windowHandles = new ArrayList<>(app.getDriver().getWindowHandles());
        driver.switchTo().window(windowHandles.get(index));

        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        Actions actions = new Actions(app.getDriver());

        actions.sendKeys(Keys.PAGE_DOWN).perform();

        delay(3000);

        WebElement firstNameField = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector("#__next > div.car-booking-page > div.left > div:nth-child(3) > div.car-card.large.white.car-booking-driver-wrapper.card > div.driver-form.card-content > div:nth-child(1) > div.car-input-wrap > div > input")));

        actions.moveToElement(firstNameField);
        firstNameField.clear();
        firstNameField.sendKeys(firstName);

        WebElement lastNameField = wait
            .until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#__next > div.car-booking-page > div.left > div:nth-child(3) > div.car-card.large.white.car-booking-driver-wrapper.card > div.driver-form.card-content > div:nth-child(2) > div.car-input-wrap > div > input")));
        lastNameField.clear();
        lastNameField.sendKeys(lastName);            

        WebElement emailField = wait
            .until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#__next > div.car-booking-page > div.left > div:nth-child(3) > div.car-card.large.white.car-booking-driver-wrapper.card > div.driver-form.card-content > div:nth-child(4) > div.car-input-wrap > div > input")));
        emailField.clear();
        emailField.sendKeys(email);
                        
        WebElement phoneField = wait
            .until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#phoneNumber")));
        phoneField.clear();
        phoneField.sendKeys(phone);

        WebElement flightField = wait
            .until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#__next > div.car-booking-page > div.left > div:nth-child(3) > div.car-card.large.white.car-booking-driver-wrapper.card > div.driver-form.card-content > div:nth-child(7) > div.car-input-wrap > div > input")));
        flightField.clear();
        flightField.sendKeys(flightNumber);
    }
    
    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
