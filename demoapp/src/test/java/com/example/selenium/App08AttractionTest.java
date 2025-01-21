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
public class App08AttractionTest
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
    @Feature("TC041 Search Attractions & Tours By City")
    public void TC041_testSuccessSearchAttractionByCity(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        navigateToAttractionPage();
        clickCityTextField();
        selectCity("Hong Kong");
        searchAttractionButton();
        verifySuccessSearchAttractionByCity();
        takeScreenshot("TC041_testSuccessSearchAttractionByCity");
        resetAttractionPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC042 Success Search Attractions & Tours By Attraction Name")
    public void TC042_testSuccessSearchAttractionByAttractionName(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickCityTextField();
        selectCity("Hong Kong");
        clickAttractionTextField();
        selectAttraction("Hong Kong Disneyland");
        verifySuccessSearchAttractionByAttractionName();
        takeScreenshot("TC042_testSuccessSearchAttractionByAttractionName");
        //resetAttractionPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC043 Failed Book Attraction Ticket (Empty Field)")
    public void TC043_testFailedBookAttractionEmptyField(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookAttractionPage();

        clickSelectTicket();
        selectTicketDate("13", "02");
        selectTicketPackage(2);
        clickNextButton();

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            // TODO: handle exception
        }

        clickPayButton();
        verifyFailBookAttractionEmptyField();

        takeScreenshot("TC043_testFailedBookAttractionEmptyField");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC044 Failed Book Attraction Ticket (Invalid Email)")
    public void TC044_testFailedBookAttractionInvalidEmail(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookAttractionPage();

        clickSelectTicket();
        selectTicketDate("13", "02");
        selectTicketPackage(2);
        clickNextButton();
        clickAddTravelers();
        inputTravelerInfo("Istts", "Akun");
        inputContactInfo("Akun Istts", "akun", "08165434236");
        clickPayButton();
        verifyFailBookAttractionInvalidEmail();
        
        takeScreenshot("TC044_testFailedBookAttractionInvalidEmail");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC045 Failed Book Attraction Ticket (Invalid Phone Number)")
    public void TC045_testFailedBookAttractionInvalidPhoneNumber(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        resetBookAttractionPage();

        clickSelectTicket();
        selectTicketDate("13", "02");
        selectTicketPackage(2);
        clickNextButton();
        clickAddTravelers();
        inputTravelerInfo("Istts", "Akun");
        inputContactInfo("Akun Istts", "akunistts@gmail.com", "00000000000000000");
        clickPayButton();
        verifyFailBookAttractionInvalidPhoneNumber();
        
        takeScreenshot("TC045_testFailedBookAttractionInvalidPhoneNumber");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC046 Success Book Attraction Ticket")
    public void TC046_testSuccessBookAttraction(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        WebDriver driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        closeBlockingElement();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con.mc-hd__account-nologin > span"))); // Wait for the email input form to appear

        signIn();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div > div > input"))); // Wait for the email input form to appear

        emailFill("dummytestingistts@gmail.com");
        //emailFill("testingSeleniumTest@gmail.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.default-wrapper > div.ibu-password-login-btn-wrapper > span.password-login-btn-text"))); // Wait for the password field
        
        waitForToastModalToDisappear();
        goToPassword();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div:nth-child(2) > div > input"))); // Wait for the password input
        
        passwordFill("yangpentingjalan");
        //passwordFill("testingSelenium");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resetBookAttractionPageLogin();

        clickSelectTicket();
        selectTicketDate("13", "02");
        selectTicketPackage(2);
        clickNextButton();
        //clickAddTravelers();
        //inputTravelerInfo("Istts", "Akun");
        inputContactInfo("Akun Istts", "akunistts@gmail.com", "08165434236");
        clickPayButton();

        verifySuccessBookAttraction();
        
        takeScreenshot("TC046_testSuccessBookAttraction");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Step("Navigate to attraction page")
    private void navigateToAttractionPage(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement attractionPage = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#header_action_nav_ttd")));
        attractionPage.click();
    }

    @Step("Click button search attraction")
    private void searchAttractionButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ticket_header_pic > div.ticket_header_search > div.trip-search > div > div > div.ol_citypage_height > div.header_search_content > div.search-icon")));
        searchButton.click();
    }
    
    // @Step("Clear going to recent searches")
    // private void clearGoingToRecentSearches(){
    //     WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
    //     WebElement clearGoingToRecentSearches = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(6) > div > div.search_top-card__49bt4.with-menu > div > div > div.full-cont.search_top-view__e6uw4 > div.search_search-bar-wp__FgD72 > div.trip-seo-search-box.online > div > div.grid-box > div.station > div:nth-child(3) > div.open-component.trip-seo-search-box-open-component.online > div > div > div > div > div:nth-child(1) > i")));
    //     clearGoingToRecentSearches.click();
    // }

    @Step("Click City text field")
    private void clickCityTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement CityTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ticket_header_pic > div.ticket_header_search > div.trip-search > div > div > div.ol_citypage_height > div.header_search_content > div.switch_header > div.ol_cityselect_region_search.ol_cityselect_search")));
        CityTextField.click();
    }

    @Step("Click City From choice")
    private void selectCity(String city) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement select = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[contains(text(), '" + city + "')])[1]")));
        select.click();
    }

    @Step("Click Attraction text field")
    private void clickAttractionTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement AttractionTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibuact-undefined-search-click-38-home-en-ID")));
        AttractionTextField.click();
    }

    @Step("Click Attraction From choice")
    private void selectAttraction(String attraction) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement Attraction = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[contains(text(), '" + attraction + "')])[1]")));
        Attraction.click();
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

    @Step("Click Select Ticket")
    private void clickSelectTicket(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='undefined']/taro-view-core/taro-view-core/taro-text-core)[12]")));
        
        ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);

        // Add a small wait to ensure the scroll is complete
        try {
            Thread.sleep(500); // Adjust duration as needed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        button.click();
    }

    @Step("Click Select Ticket Date")
    private void selectTicketDate(String day, String month){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='2025-"+month+"-"+day+"']/taro-text-core[1]")));
        button.click();
    }

    @Step("Select Ticket Package")
    private void selectTicketPackage(int tier){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='undefined']/taro-text-core)["+(72+tier)+"]")));
        button.click();
    }

    @Step("Click Next Button")
    private void clickNextButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@style='background-image: linear-gradient(90deg, rgb(50, 100, 255), rgb(50, 100, 255)); width: 96px; height: 42px; text-align: center; display: flex; flex-direction: column; justify-content: center; align-items: center; margin-top: 24px; margin-bottom: 24px; border-radius: 4px; cursor: pointer;']")));
        button.click();
    }   

    @Step("Click Add Travelers")
    private void clickAddTravelers(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#passenger_37724224 > div:nth-child(2) > div > div:nth-child(2) > div:nth-child(3) > div > span.taro-text.ottdpage.ottd-tripPlus")));
        button.click();
    }   

    @Step("Input Traveler Info")
    private void inputTravelerInfo(String last_name, String first_name){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='weui-input'])[2]")));
        input.sendKeys(last_name);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='weui-input'])[3]")));
        input.sendKeys(first_name);
    }   

    @Step("Input Contact Info")
    private void inputContactInfo(String name, String email, String phone_number){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='weui-input'])[4]")));
        input.sendKeys(name);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='weui-input'])[5]")));
        input.sendKeys(email);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='weui-input'])[6]")));
        input.sendKeys(phone_number);
    }   

    @Step("Click Pay Button")
    private void clickPayButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.ticket_page_booking > div.ticket_body > div.ticket_body_left > div.ottd-footer > div.m-footer-shadow > div:nth-child(2) > div:nth-child(2) > div")));
        button.click();
    }   

    @Step("Verify success search Attractions & Tours By City")
    private void verifySuccessSearchAttractionByCity() {
        driver = app.getDriver();
        
        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    
        By successElementSelector = By.cssSelector(
                "#ottd-smart-platform > section > div.mix-list-container > div.content-container > div:nth-child(2) > div.content-wrap > div.left-content-container > div > div:nth-child(1) > div.filter-title-container > div");
        
        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify success search Attractions & Tours By Attraction Name")
    private void verifySuccessSearchAttractionByAttractionName() {
        driver = app.getDriver();
        
        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    
        By successElementSelector = By.cssSelector(
                "#poi\\.detail\\.overview > div > div.TopBoxStyle-sc-2jpvue-4.cKCoNt > div.top-left > div.top-box > div > div.poi-page-title > h1");
        
        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify failed book Attractions & Tours (Empty Field)")
    private void verifyFailBookAttractionEmptyField() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Select 1 more traveler')]");

        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify failed book Attractions & Tours (Invalid Email)")
    private void verifyFailBookAttractionInvalidEmail() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Please enter a valid email address')]");

        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify failed book Attractions & Tours (Invalid Phone Number)")
    private void verifyFailBookAttractionInvalidPhoneNumber() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Enter a valid phone number')]");

        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Book Attraction Ticket")
    private void verifySuccessBookAttraction() {
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

    @Step("Reset Attraction Page")
    private void resetAttractionPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/things-to-do/ttd-home/?locale=en-ID&curr=IDR");
    }

    @Step("Reset Book Attraction Page")
    private void resetBookAttractionPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/travel-guide/attraction/hong-kong/hong-kong-disneyland-10558849/?locale=en-ID&curr=IDR");
    }

    @Step("Reset Book Attraction Page after login")
    private void resetBookAttractionPageLogin() {
        driver.navigate().to("https://id.trip.com/travel-guide/attraction/hong-kong/hong-kong-disneyland-10558849/?locale=en-ID&curr=IDR");
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

    @Step("Click button sign in")
    private void signIn(){
        app.getDriver().findElement(By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con.mc-hd__account-nologin > span")).click();
    }

    @Step("Input form with Password:{password}")
    private void passwordFill(String password){
        WebElement passwordField=app.getDriver().findElement(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div:nth-child(2) > div > input"));
        passwordField.clear();
        passwordField.sendKeys(password);

        app.getDriver().findElement(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > button")).click();
    }

    @Step("Input form with Email:{email}")
    private void emailFill(String email){
        WebElement emailField = app.getDriver().findElement(By.cssSelector(
                "#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div > div > input"));
        emailField.clear();
        emailField.sendKeys(email);

        app.getDriver().findElement(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > button > div > span > span")).click();
    }

    @Step("wait toast modal to disappear")
    private void waitForToastModalToDisappear() {
        WebDriver driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".toast_modal")));
    }

    @Step("Go to Password")
    private void goToPassword(){
        app.getDriver().findElement(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.default-wrapper > div.ibu-password-login-btn-wrapper > span.password-login-btn-text")).click();
    }

    
    @Step("Close blocking element")
    private void closeBlockingElement() {
        WebDriver driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oh-pwa")));
        WebElement blockingElement = driver.findElement(By.cssSelector(".oh-pwa")); // Replace with correct selector if needed
            if (blockingElement.isDisplayed()) {
                // Dismiss the blocking element
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].style.display='none';", blockingElement);
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
