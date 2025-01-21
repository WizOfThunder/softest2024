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
    @Feature("TC053 Search Flight & Hotel (Round-Trip)")
    public void TC053_testSuccessSearchFlightAndHotelRoundTrip(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        navigateToFlightAndHotelPage();
        clickFlightAndRoomOptionDropdown();
        selectRoundTripOption();
        clickGoingFromTextField();
        emptyGoingFromTextField();
        selectGoingFromCity("Jakarta");
        emptyGoingToTextField();
        selectGoingToCity("Bali");
        clickDestinationTextField();
        emptyDestinationTextField();
        selectDestinationCity("Bali");
        clickDepartTextField();
        selectDepartTime();
        selectReturnTime();
        clickCheckInTextField();
        selectCheckInTime();
        selectCheckOutTime();
        searchFlightAndHotelButton();
        verifySuccessSearchFlightAndHotelRoundTrip();

        takeScreenshot("TC053_testSuccessSearchFlightAndHotelRoundTrip");
        resetFlightAndHotelPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC054 Search Flight & Hotel (One-Way)")
    public void TC054_testSuccessSearchFlightAndHotelOneWay(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickFlightAndRoomOptionDropdown();
        selectOneWayOption();
        clickPeopleAndRoomDropdown();
        clickAddRoom();
        clickAddAdult();
        clickAddChildren();
        clickAddInfant();
        clickChildrenAge();
        selectChildrenAge(15);
        clickInfantAge();
        selectInfantAge("1");
        doneFlightAndRoomOptionDropdown();
        clickGoingFromTextField();
        emptyGoingFromTextField();
        selectGoingFromCity("Jakarta");
        emptyGoingToTextField();
        selectGoingToCity("Bali");
        clickDestinationTextField();
        emptyDestinationTextField();
        selectDestinationCity("Bali");
        clickDepartTextField();
        selectDepartTime();
        clickCheckInTextField();
        selectCheckInTime();
        selectCheckOutTime();
        searchFlightAndHotelButton();
        verifySuccessSearchFlightAndHotelOneWay();

        takeScreenshot("TC054_testSuccessSearchFlightAndHotelOneWay");
        resetFlightAndHotelPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC055 Failed Search Flight & Hotel (Same Destination)")
    public void TC055_testFailedSearchFlightAndHotelSameDestination(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickGoingFromTextField();
        emptyGoingFromTextField();
        selectGoingFromCity("Jakarta");
        emptyGoingToTextField();
        selectGoingToCity("Jakarta");
        searchFlightAndHotelButton();
        verifyFailedSearchFlightAndHotelSameDestination();

        takeScreenshot("TC055_testFailedSearchFlightAndHotelSameDestination");

        resetFlightAndHotelPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC056 Navigation Flight Status")
    public void TC056_testNavigationFlightStatus(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickFlightStatus();
        inputFlightNumber("DL1234");
        clickDepartFlightTextField();
        chooseDepartDate(18, 2);
        clickSearchButton();
        verifySuccessFlightStatusNavigation();
        takeScreenshot("TC056_testNavigationFlightStatus");
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC057 Failed Book Flight & Hotel Package (Empty Field)")
    public void TC057_testFailedBookPackageEmptyField(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookFlightAndHotelPage();

        clickBookHotel();
        try {
            Thread.sleep(15000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        clickPayNow();

        verifyFailedBookPackageEmptyField();

        takeScreenshot("TC057_testFailedBookPackageEmptyField");
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC058 Failed Book Flight & Hotel Package (Invalid Email)")
    public void TC058_testFailedBookPackageInvalidEmail(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookFlightAndHotelPage();

        clickBookHotel();
        editPassenger();
        inputPassengerInfo("Akun", "Istts", "Male", "1", "May", "2003", "Singapore");
        clickSaveButton();
        clickConfirmButton();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        deletePassenger();
        inputContactInfo("akun", "0812345678");
        clickPayNow();
        verifyFailedBookPackageInvalidEmail();
        
        takeScreenshot("TC058_testFailedBookPackageInvalidEmail");
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC059 Failed Book Flight & Hotel Package (Invalid Phone Number)")
    public void TC059_testFailedBookPackageInvalidPhoneNumber(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookFlightAndHotelPage();

        clickBookHotel();
        editPassenger();
        inputPassengerInfo("Akun", "Istts", "Male", "1", "May", "2003", "Singapore");
        clickSaveButton();
        clickConfirmButton();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        deletePassenger();
        inputContactInfo("akunistts@gmail.com", "0000000000000000");
        clickPayNow();
        verifyFailedBookPackageInvalidPhoneNumber();
        
        takeScreenshot("TC059_testFailedBookPackageInvalidPhoneNumber");
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC060 Success Book Flight & Hotel Package")
    public void TC060_testSuccessBookPackage(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookFlightAndHotelPage();
        clickBookHotel();
        editPassenger();
        inputPassengerInfo("Akun", "Istts", "Male", "1", "May", "2003", "Singapore");
        clickSaveButton();
        clickConfirmButton();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        deletePassenger();
        inputContactInfo("akunistts@gmail.com", "0812345678");
        clickPayNow();
        verifySuccessBookPackage();
        
        takeScreenshot("TC060_testSuccessBookPackage");
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

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

    @Step("Click add room")
    private void clickAddRoom(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement room = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.fh-sf-trigger-popup > div > div:nth-child(4) > div.right > div:nth-child(3) > i")));
        room.click();
    }

    @Step("Click add adult")
    private void clickAddAdult(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement adult = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.fh-sf-trigger-popup > div > div:nth-child(6) > div.right > div:nth-child(3) > i")));
        adult.click();
    }

    @Step("Click add children")
    private void clickAddChildren(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement children = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.fh-sf-trigger-popup > div > div:nth-child(7) > div.right > div:nth-child(3) > i")));
        children.click();
    }

    @Step("Click add infant")
    private void clickAddInfant(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement infant = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.fh-sf-trigger-popup > div > div:nth-child(8) > div.right > div:nth-child(3) > i")));
        infant.click();
    }

    @Step("Click children age")
    private void clickChildrenAge(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement children = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.fh-sf-trigger-popup > div > div.ages-select > div:nth-child(1) > div > div > div")));
        
        // Scroll into view
        ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", children);

        // Add a small wait to ensure the scroll is complete
        try {
            Thread.sleep(500); // Adjust duration as needed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        children.click();
    }

    @Step("Select children age")
    private void selectChildrenAge(int age){
        int childAge = age-1;
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement children = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(30) > div > div:nth-child("+ childAge +") > label")));
        children.click();
    }

    @Step("Click infant age")
    private void clickInfantAge(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement infant = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.fh-sf-trigger-popup > div > div.ages-select > div:nth-child(2) > div > div > div")));
        infant.click();
    }

    @Step("Select infant age")
    private void selectInfantAge(String age){
        int infantAge = 0;
        if (age.equals("<1")) {
            infantAge = 1;
        } else {
            infantAge = 2;
        }
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement infant = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(30) > div > div:nth-child("+ infantAge +") > label")));
        infant.click();
    }

    @Step("Click Going From text field")
    private void clickGoingFromTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement CityTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#from")));
        
        // Scroll into view
        ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", CityTextField);

        // Add a small wait to ensure the scroll is complete
        try {
            Thread.sleep(500); // Adjust duration as needed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        CityTextField.click();
    }

    @Step("Empty Going From text field")
    private void emptyGoingFromTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement CityTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#from")));
        CityTextField.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Select Going From city")
    private void selectGoingFromCity(String city) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement goingToCity = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(/html/body/div[3]//*[contains(text(), '"+ city +"')])[1]")));
        goingToCity.click();
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
    private void selectGoingToCity(String city) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement goingTo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(/html/body/div[3]//*[contains(text(), '"+ city +"')])[1]")));
        goingTo.click();
    }

    // @Step("Select Going To city (same as Going From)")
    // private void selecFailGoingToCity() {
    //     WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
    //     // Ensure the dropdown options are clickable
    //     WebElement goingTo = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.m-flight-poi-wrapper.overflow-scroll.mt-4.fh-bundle-poi.is-hotcity > ul > li:nth-child(1) > ul > li:nth-child(1) > span")));
    //     goingTo.click();
    // }

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
    private void selectDestinationCity(String destination) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement destinationCity = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(/html/body/div[3]//*[contains(text(), '"+ destination +"')])[1]")));
        destinationCity.click();
    }

    @Step("Click Depart text field")
    private void clickDepartTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement departTextField = wait.until(ExpectedConditions.elementToBeClickable(By.id("depart")));
        departTextField.click();
    }

    @Step("Click Depart Flight text field")
    private void clickDepartFlightTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement departTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#searchTimeLabel > div > input")));
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
        WebElement depart = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.c-calendar.fh-bundle-package-calendar > div.c-calendar__body > div:nth-child(2) > div.c-calendar-month__days > ul:nth-child(3) > li.is-selected.is-selected-last.is-allow-hover > span")));
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

    @Step("Click Flight & Room Option Dropdown")
    private void clickFlightAndRoomOptionDropdown() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement flightoption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.ibu-bundle-container > div.top > div.search-bar > div > div.fh-searchForm-header > div > div.fh-sf-trigger.fh-sf-drop-down.fh-sf-dropdown-trigger.fh-trip-way > div")));
        flightoption.click();
    }

    @Step("Click done on Flight Option Dropdown")
    private void doneFlightAndRoomOptionDropdown() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement flightoption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".done")));
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

    @Step("Click Flight Status Option")
    private void clickFlightStatus(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement status = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='__next']/div[2]/span/div[3]")));
        status.click();
    }

    @Step("Click Search By Departure/ Arrival City Option")
    private void clickSearchByDepartureArrivalCity(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div[2]/div")));
        option.click();
    }

    @Step("Choose day in depart text field")
    private void chooseDepartDate(int day, int month){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement depart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='search']/div[2]/div[2]/div/div[1]/div["+month+"]//*[contains(text(),'"+day+"')][1]")));
       depart.click();
    }

    @Step("Click Flight Number Text Field")
    private void clickFlightNumberTextField(){
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        WebElement flight = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#search > div.search-info-container > div.search-selectors-info.search-selectors-info--ibu.search-selectors-info--fno > label > div.search-selectors-fno > input")));
        flight.click();
    }

    @Step("Input Flight Number")
    private void inputFlightNumber(String flight_number){
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        WebElement flight = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#search > div.search-info-container > div.search-selectors-info.search-selectors-info--ibu.search-selectors-info--fno > label > div.search-selectors-fno > input")));
        flight.sendKeys(flight_number);
    }

    @Step("Click Search Button")
    private void clickSearchButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#search > div.search-info-container > div.search-button.search-button-left.search-button--ibu > div.search-button-text")));
        button.click();
    }

    @Step("Click Book Hotel Button")
    private void clickBookHotel(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(20));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='__next']/div[2]/div[5]/div/div[2]/div[2]//*[contains(text(),'Book')])[1]")));
        
        ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);

        // Add a small wait to ensure the scroll is complete
        try {
            Thread.sleep(500); // Adjust duration as needed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        button.click();
    }

    @Step("Click Pay Now Button")
    private void clickPayNow(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(20));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[contains(text(),'Pay Now')])[1]")));
        button.click();
    }

    @Step("Delete Passenger 2")
    private void deletePassenger(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(20));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#PassengerInfo > div.pax-option-list__page > div.m-booking-content__moduleCard.mb-8.p-16 > ol.option-item-wrapper > li:nth-child(2) > div.pax-item-action-btn > div.icon-remove > i")));
        button.click();
    }

    @Step("Click Edit Passenger 1")
    private void editPassenger(){
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(20));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#PassengerInfo > div.pax-option-list__page > div.m-booking-content__moduleCard.mb-8.p-16 > ol.option-item-wrapper > li > div.pax-item-action-btn > div > i")));
        button.click();
    }

    @Step("Input Passenger Info")
    private void inputPassengerInfo(String first_name, String last_name, String gender, String dob_day, String dob_month, String dob_year, String nationality){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(20));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[8]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'"+gender+"')]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[9]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(/html/body/div[16]//*[contains(text(),'"+dob_day+"')])[1]")));
        input.click();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[17]//*[contains(text(),'"+dob_month+"')]")));
        input.click();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[19]//*[contains(text(),'"+dob_year+"')]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[10]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='ibu_region_selector']/div/div/div//*[contains(text(),'"+nationality+"')])[3]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[6]")));
        input.sendKeys(first_name);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[7]")));
        input.sendKeys(last_name);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[6]")));
        input.click();
    }

    @Step("Click Save Button")
    private void clickSaveButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(20));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[contains(text(),'Save')])[3]")));
        button.click();
    }

    @Step("Click Confirm Button")
    private void clickConfirmButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(20));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[contains(text(),'Confirm')])[2]")));
        button.click();
    }

    @Step("Input Contact Info")
    private void inputContactInfo(String email, String phone_number){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(20));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[2]")));
        input.sendKeys(email);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[3]")));
        input.sendKeys(phone_number);
    }


    @Step("Verify success search Flight & Hotels (Round-Trip)")
    private void verifySuccessSearchFlightAndHotelRoundTrip() {
        driver = app.getDriver();
        
        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        
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

    @Step("Verify success search Flight & Hotels (Round-Trip)")
    private void verifySuccessSearchFlightAndHotelOneWay() {
        driver = app.getDriver();
        
        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        
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

    @Step("Verify success search Flight & Hotels (Round-Trip)")
    private void verifyFailedSearchFlightAndHotelSameDestination() {
        driver = app.getDriver();
        
        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    
        By successElementSelector = By.cssSelector(
                "body > div.fh-sf-trigger-popup.fh-sf-hover-popup-content.error-popover > span");
        
        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify success Flight Status Navigation")
    private void verifySuccessFlightStatusNavigation() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[@id='__next']/div[4]/div[1]/div[1]/div[1]/div[1]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.getText().contains("Delta Air Lines DL1234"), "Success element not displayed!");
    }

    @Step("Verify Failed Book Flight & Hotel Package (Empty Field)")
    private void verifyFailedBookPackageEmptyField() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "(//*[contains(text(),'Please fill in all required fields and save before proceeding')])[1]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Failed Book Flight & Hotel Package (Invalid Email)")
    private void verifyFailedBookPackageInvalidEmail() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Please check Email address format')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Failed Book Flight & Hotel Package (Invalid Phone Number)")
    private void verifyFailedBookPackageInvalidPhoneNumber() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Please enter a valid phone number')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Book Flight & Hotel Package")
    private void verifySuccessBookPackage() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Select a Payment Method')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Reset Flight & Hotel Page")
    private void resetFlightAndHotelPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/packages/?locale=en-ID&curr=IDR");
    }

    @Step("Reset Book Flight & Hotel Page")
    private void resetBookFlightAndHotelPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/packages/list/jakarta-to-bali/jkt-to-dps?dCity=jkt&aCity=dps&hCity=dps&sourceFrom=IBUBundle_home&tripWay=round-trip&room=1&adult=2&child=0&cAges=&infants=0&iAges=&dDate=2025-02-13&rDate=2025-02-15&iDate=2025-02-13&oDate=2025-02-15&locale=en-ID&curr=IDR");
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
