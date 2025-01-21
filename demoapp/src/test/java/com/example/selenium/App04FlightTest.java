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
        clearGoingTo();
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
        // resetFlightPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC014 Failed Book Flight (empty field)")
    public void TC014_testFailedBookFlightEmptyField(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookFlightPage();
        clickSelectFlight();
        clickBookFlight();
        clickNextButton();
        verifyFailedBookFlightEmptyField();
        
        takeScreenshot("TC014_testFailedBookFlightEmptyField");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC015 Failed Book Flight (invalid email)")
    public void TC015_testFailedBookFlightInvalidEmail(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookFlightPage();
        clickSelectFlight();
        clickBookFlight();
        inputPassengerInfo("Akun", "Istts", "Male", "13", "Mar", "1984", "Singapore");
        clickSaveButton();
        clickConfirmButton();
        inputContactInfo("Akun", "akun", "0814332558");
        clickNextButton();

        verifyFailedBookFlightInvalidEmail();
        
        takeScreenshot("TC015_testFailedBookFlightInvalidEmail");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC016 Failed Book Flight (invalid phone number)")
    public void TC016_testFailedBookFlightInvalidPhoneNumber(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookFlightPage();
        clickSelectFlight();
        clickBookFlight();
        inputPassengerInfo("Akun", "Istts", "Male", "13", "Mar", "1984", "Singapore");
        clickSaveButton();
        clickConfirmButton();
        inputContactInfo("Akun", "akunistts@gmail.com", "ddddddddddd");
        clickNextButton();

        verifyFailedBookFlightInvalidPhoneNumber();
        
        takeScreenshot("TC016_testFailedBookFlightInvalidPhoneNumber");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC017 Success Book Flight")
    public void TC017_testSuccessBookFlight(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookFlightPage();
        clickSelectFlight();
        clickBookFlight();
        inputPassengerInfo("Akun", "Istts", "Male", "13", "Mar", "1984", "Singapore");
        clickSaveButton();
        clickConfirmButton();
        inputContactInfo("Akun", "akunistts@gmail.com", "08143325581");
        clickNextButton();
        clickNoThanks();

        verifySuccessBookFlight();

        takeScreenshot("TC017_testSuccessBookFlight");

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
        WebElement goingToField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-testid='search_city_to0_wrapper']")));
        goingToField.click();
    }

    @Step("Click Going To choice")
    private void selectGoingTo(String city) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement goingTo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(/html/body/div[4]//*[contains(text(), '" + city + "')])[1]")));
        goingTo.click();
    }

    @Step("Clear Going To Text Field")
    private void clearGoingTo() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement goingTo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@data-testid=\"cityLabel_delete_0\"])[2]")));
        goingTo.click();
    }

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

    @Step("Click select flight button")
    private void clickSelectFlight() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#\\32 -0_1 > div > div.f-info-head.is-v2.u-clearfix.result-item-dep.selected.result-item-recommend > div.f-info-content.is-v2 > div.select-area_7bcf > div.c-result-operate.c-result-operate_8da2 > span > button > div > span > span > span")));
        button.click();
    }

    @Step("Click book flight button")
    private void clickBookFlight() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#slide-content-shoppingBrandedFareCard_2-0 > div:nth-child(1) > div > div > div.item-con-cell.result-item-price.mt-24.result-item-flex__bottom-info-wrapper > div > div > div.item-con-cell-btn > span > button > div")));
        button.click();
    }
    
    @Step("Click Next button")
    private void clickNextButton() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[contains(text(),'Next')])[1]")));
        button.click();
    }

    @Step("Click No Thanks Option")
    private void clickNoThanks() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[contains(text(),'No Thanks')])[2]")));
        button.click();
    }

    @Step("Input Passenger Info")
    private void inputPassengerInfo(String first_name, String last_name, String gender, String dob_day, String dob_month, String dob_year, String nationality) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[1]")));
        input.sendKeys(first_name);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[2]")));
        input.sendKeys(last_name);
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='PassengerInfo']/div[2]/div[1]/div[2]")));
        button.click();     
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[3]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'"+gender+"')]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[4]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='PassengerInfo']/div[2]/div[1]//*[contains(text(),'"+dob_day+"')]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='selector-list-wrapper']//*[contains(text(),'"+dob_month+"')]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='selector-list-wrapper']//*[contains(text(),'"+dob_year+"')]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[5]")));
        input.click();
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='ibu_region_selector']/div/div/div/div[2]//*[contains(text(),'"+nationality+"')])[3]")));
        input.click();
    }

    @Step("Click Save button")
    private void clickSaveButton() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='PassengerInfo']/div[2]/div[1]/div[2]")));
        button.click();
    }

    @Step("Click Confirm button")
    private void clickConfirmButton() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#dialogWrapper > div > div > div > div > div > div.ift-modal-content.ift-modal-content__scroll > div > div.name-confirm-body > div.name-confirm-actions > div.o-btn-v1_blue-1x.name-confirm-btn-confirm")));
        button.click();
    }

    @Step("Input Contact Info")
    private void inputContactInfo(String name, String email, String phone_number) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[1]")));
        input.sendKeys(name);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[2]")));
        input.sendKeys(email);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@type='text'])[3]")));
        input.sendKeys(phone_number);
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

    @Step("Verify failed book flight (Empty Field)")
    private void verifyFailedBookFlightEmptyField() {
        driver = app.getDriver();
        // Wait for the page or element to appear
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector("#PassengerInfo > div.pax-form-wrapper > div.m-booking-content__moduleCard.p-16.mb-8 > div.m-passenger-form > div.info-form.m-passenger-form__body > ul:nth-child(1) > li.m-passenger-form__item.item-given.item-given-new.error.with-guide-icon > p");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
    
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success message or profile element not displayed!");
    }

    @Step("Verify failed book flight (Invalid Email)")
    private void verifyFailedBookFlightInvalidEmail() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.xpath("//*[contains(text(),'Please check Email address format')]");
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success message or profile element not displayed!");
    }

    @Step("Verify failed book flight (Invalid Phone Number)")
    private void verifyFailedBookFlightInvalidPhoneNumber() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.xpath("//*[contains(text(),'Please enter a valid phone number')]");
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success message or profile element not displayed!");
    }

    @Step("Verify Success Book Flight")
    private void verifySuccessBookFlight() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Add protection for a worry-free journey')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
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

    @Step("Reset Book Flight Page")
    private void resetBookFlightPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/flights/showfarefirst?dcity=jkt&acity=dps&ddate=2025-02-24&rdate=2025-02-27&triptype=ow&class=y&lowpricesource=searchform&quantity=1&searchboxarg=t&nonstoponly=off&locale=en-ID&curr=IDR");
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
