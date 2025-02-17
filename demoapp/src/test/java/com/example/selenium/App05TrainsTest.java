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
import org.openqa.selenium.By.ByXPath;
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
public class App05TrainsTest
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
        selectLeavingFrom("Shanghai");
        selectGoingTo("Shenzhen");
        clickDepatureTime(13, 2);
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
        selectLeavingFrom("Shanghai");
        selectGoingTo("Shanghai");
        clickHighSpeedCheckbox();
        searchTrainsButton();
        verifyFailedSearchTrainsSameDestination();
        takeScreenshot("TC020_testFailedSearchTrainsSameDestination");
        
        resetTrainsPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC021 Navigation Train Guides")
    public void TC021_testNavigationTrainGuides(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickTrainGuides();
        verifySuccessTrainGuidesNavigation();
        takeScreenshot("TC021_testNavigationTrainGuides");
        //resetPage();
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC022 Failed Book Train (empty field)")
    public void TC022_testFailedBookTrainEmptyField(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookTrainsPage();
        clickSelectTrainButton();
        clickReserveTrainButton();
        clickBookTrainButton();
        verifyFailedBookTrainEmptyField();

        takeScreenshot("TC022_testFailedBookTrainEmptyField");
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC023 Failed Book Train (invalid email)")
    public void TC023_testFailedBookTrainInvalidEmail(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookTrainsPage();
        clickSelectTrainButton();
        clickReserveTrainButton();
        clickAddAdultButton();
        inputPassengerInfo("Passport", "G2784", "Singapore", "13", "Mar", "2029", "Istts", "Akun", "14", "Mar", "1984");
        inputContactInfo("Akun", "akun", "0814332558");
        clickBookTrainButton();

        verifyFailedBookTrainInvalidEmail();
        takeScreenshot("TC023_testFailedBookTrainInvalidEmail");
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC024 Failed Book Train (invalid phone number)")
    public void TC024_testFailedBookTrainInvalidPhoneNumber(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookTrainsPage();
        clickSelectTrainButton();
        clickReserveTrainButton();
        clickAddAdultButton();
        inputPassengerInfo("Passport", "G2784", "Singapore", "13", "Mar", "2029", "Istts", "Akun", "14", "Mar", "1984");
        inputContactInfo("Akun", "akunistts@gmail.com", "00000000000000000");
        clickBookTrainButton();

        verifyFailedBookTrainInvalidPhoneNumber();
        
        takeScreenshot("TC024_testFailedBookTrainInvalidPhoneNumber");
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC025 Success Book Train")
    public void TC025_testSuccessBookTrain(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookTrainsPage();
        clickSelectTrainButton();
        clickReserveTrainButton();
        clickAddAdultButton();
        inputPassengerInfo("Passport", "G2784", "Singapore", "13", "Mar", "2029", "Istts", "Akun", "14", "Mar", "1984");
        inputContactInfo("Akun", "akunistts@gmail.com", "08143325581");
        clickBookTrainButton();
        verifySuccessBookTrain();
        
        takeScreenshot("TC025_testSuccessBookTrain");
        
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
        WebElement highSpeedCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='trn-ift HighSpeedCheckbox_checkbox__RYD1_']")));
        highSpeedCheckbox.click();
    }
    
    @Step("Click button search trains")
    private void searchTrainsButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[1]/div/div/div[2]/div[2]/div[2]/div/div//*[contains(text(),'Search')]")));
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
        WebElement leavingFromField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='CityPicker_input__2Cqbv'])[1]")));
        leavingFromField.click();
    }

    @Step("Empty Leaving From text field")
    private void emptyLeavingFromTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement leavingFromField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(8) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.search-box-with-season > div > div > div.grid-box > div.station > div.CityPicker_container___pdTr.CityPicker_divider__nc3eJ > div.open-component.trip-seo-search-box-open-component.online > div > div > input")));
        leavingFromField.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Click Leaving From choice")
    private void selectLeavingFrom(String city) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement leavingFrom = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(/html/body/div[1]/div/div[1]/div/div/div[2]/div[2]/div[2]/div/div/div[1]/div[1]/div[1]/div[2]/div/div/div//*[contains(text(), '" + city + "')])[1]")));
        leavingFrom.click();
    }

    @Step("Click going to text field")
    private void clickGoingToTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement goingToField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(8) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.search-box-with-season > div > div > div.grid-box > div.station > div:nth-child(3) > div.CityPicker_inputContainer__upDg7 > span > input")));
        goingToField.click();
    }

    @Step("Empty going to text field")
    private void emptyGoingToTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement goingToField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(8) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.search-box-with-season > div > div > div.grid-box > div.station > div:nth-child(3) > div.open-component.trip-seo-search-box-open-component.online > div > div > input")));
        goingToField.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Click Going To choice")
    private void selectGoingTo(String city) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement goingTo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(/html/body/div[1]/div/div[1]/div/div/div[2]/div[2]/div[2]/div/div/div[1]/div[1]/div[3]/div[2]/div/div/div//*[contains(text(), '" + city + "')])[1]")));
        goingTo.click();
    }

    // @Step("Click Same Destination for Going To")
    // private void selectFailGoingTo() {
    //     WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
    //     // Ensure the dropdown options are clickable
    //     WebElement goingTo = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.station > div:nth-child(3) > div.open-component.trip-seo-search-box-open-component.online > div > div > div > div > ul:nth-child(2) > li > span")));
    //     goingTo.click();
    // }

    @Step("Select Departure Time")
    private void clickDepatureTime(int day, int month){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement departure = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[1]/div/div/div[2]/div[2]/div[2]/div/div/div[4]/div/div/div[1]/div["+month+"]//*[contains(text(),'"+day+"')]")));
        departure.click();
    }
    
    // @Step("Input search with Input:{input}")
    // private void searchFill(String input){
    //     WebElement emailField=app.getDriver().findElement(By.cssSelector("#ibuHeaderSearch > div > div > div > div.gccpoi__TripSearchBox-content > input"));
    //     emailField.sendKeys(input);
    // }

    @Step("Click Train Guides Option")
    private void clickTrainGuides(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement guides = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[1]/div/div/div[2]/div[3]/div[2]/span")));
        guides.click();
    }

    @Step("Click Select Train Button")
    private void clickSelectTrainButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.pv > div.train-wrapper.train-flex.list-v2 > div.train-content > div.train-result.train-result2 > div:nth-child(2) > div.train-result-list2 > div.train-result-list-bd2 > div.rbox > div > button > span")));
        button.click();
    }

    @Step("Click Reserve Train Button")
    private void clickReserveTrainButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.pv > div.train-wrapper.train-flex.list-v2 > div.train-content > div.train-result.train-result2 > div:nth-child(2) > div.train-detail-margin2 > div > div > div > div > div.swiper-wrapper > div.swiper-slide.swiper-slide-active > div > div > button")));
        button.click();
    }

    @Step("Click Add Adult Button")
    private void clickAddAdultButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.pv > div.h5hide > div > div.book-main > div.train-white.book-pasg > div.pasg-add-btn > button:nth-child(1)")));
        button.click();
    }

    @Step("Input Passenger Info")
    private void inputPassengerInfo(String id_type, String id_number, String country, String exp_day, String exp_month, String exp_year, String last_name, String first_name, String dob_day, String dob_month, String dob_year){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='input-click-wrap'])[1]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'"+id_type+"')]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[2]")));
        input.sendKeys(id_number);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='input-click-wrap'])[2]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[contains(text(),'"+country+"')])[1]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='input-click-wrap'])[3]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[text()='"+exp_month+"'])[1]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[text()='"+exp_day+"'])[1]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[text()='"+exp_year+"'])[1]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[text()='Done'])[1]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[6]")));
        input.sendKeys(last_name);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[7]")));
        input.sendKeys(first_name);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='input-click-wrap'])[4]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[text()='"+dob_month+"'])[2]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[text()='"+dob_day+"'])[2]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[text()='"+dob_year+"'])[1]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[text()='Done'])[2]")));
        input.click();
    }

    @Step("Input Contact Info")
    private void inputContactInfo(String contact_name, String email, String phone_number){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[9]")));
        input.sendKeys(contact_name);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[11]")));
        input.sendKeys(email);
        if (!phone_number.equals("")) {
            input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[13]")));
            input.sendKeys(phone_number);            
        }
    }

    @Step("Click Book Train Button")
    private void clickBookTrainButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.pv > div.h5hide > div > div.book-main > div.train-white.book-botm > button")));
        button.click();
    }

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

        By successElementSelector = By.cssSelector("body > div:nth-child(8) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.search-box-with-season > div > div > div.grid-box > div.station > div:nth-child(3) > div.CityPicker_msg__N8KHY.CityPicker_warning__ryIVF > span");

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

    @Step("Verify success Train Guides Navigation")
    private void verifySuccessTrainGuidesNavigation() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[@id=\"__next\"]/div[1]/div[1]/div[2]/div[2]/h1");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.getText().contains("Train Ticket and Travel FAQs"), "Success element not displayed!");
    }

    @Step("Verify failed Book Train (empty field)")
    private void verifyFailedBookTrainEmptyField() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Please select a passenger')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify failed Book Train (invalid email)")
    private void verifyFailedBookTrainInvalidEmail() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Please enter a valid email address.')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify failed Book Train (invalid phone number)")
    private void verifyFailedBookTrainInvalidPhoneNumber() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Please enter a valid phone number')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    
    @Step("Verify Success Book Train")
    private void verifySuccessBookTrain() {
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

    @Step("Reset Trains Page")
    private void resetTrainsPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/trains/?locale=en-ID&curr=IDR");
    }

    @Step("Reset to Bokk Trains Page")
    private void resetBookTrainsPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/trains/china/list?locale=en-ID&curr=IDR&departureStation=%E4%B8%8A%E6%B5%B7&arrivalStation=%E6%B7%B1%E5%9C%B3&displayDepartureStation=Shanghai+(%E4%B8%8A%E6%B5%B7)&displayArrivalStation=Shenzhen+(%E6%B7%B1%E5%9C%B3)&departDate=2025-02-13&highSpeedOnly=1");
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
