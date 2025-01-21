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

    @Test
    @Feature("TC049 Failed Book Cruise No Input")
    public void TC049_testFailBookCruiseNoInput() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        setUpCruiseBook();
        selectCruise(3);
        clickSelectRoom(4);
        selectCruiseRoom();
        clickPayNow();
        verifyFailedBookCruiseNoInput();
        takeScreenshot("TC049_testFailBookCruiseNoInput");
        resetCruisePage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }
    
    @Test
    @Feature("TC050 Failed Book Cruise Invalid Email")
    public void TC050_testFailBookCruiseInvalidEmail() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        setUpCruiseBook();
        selectCruise(5);
        clickSelectRoom(6);
        selectCruiseRoom();
        inputBookingField("akun", "akun", "85246888888", 0);
        clickPayNow();
        verifyFailedBookCruiseInvalidEmail();
        takeScreenshot("TC050_testFailBookCruiseInvalidEmail");
        resetCruisePage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC051 Failed Book Cruise Invalid Phone")
    public void TC051_testFailBookCruiseInvalidPhone() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        setUpCruiseBook();
        selectCruise(7);
        clickSelectRoom(8);
        selectCruiseRoom();
        inputBookingField("akun", "akunistts@gmail.com", "", 0);
        clickPayNow();
        verifyFailedBookCruiseInvalidPhone();
        takeScreenshot("TC051_testFailBookCruiseInvalidPhone");

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

    @Step("SetUp Cruise Book")
    private void setUpCruiseBook() {
        clickDepartFrom();
        selectDepartFrom();
        clickMonthOfDeparture();
        selectMonthOfDeparture();
        searchCruiseButton();
        
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

    @Step("Select Cruise")
    private void selectCruise(int index) {

        List<String> windowHandles = new ArrayList<>(app.getDriver().getWindowHandles());
        driver.switchTo().window(windowHandles.get(index));

        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement cruise = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "#__next > div.cruise-container > div.cruise-list-page > div.cruise-content > div > div.list-main > div.product-list > div:nth-child(1) > div")));
        cruise.click();
    }

    @Step("Click Select Cruise")
    private void clickSelectRoom(int index) {
        List<String> windowHandles = new ArrayList<>(app.getDriver().getWindowHandles());
        driver.switchTo().window(windowHandles.get(index));

        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "#ibucru-10650039188-info-select-367340")));
        button.click();
    }

    @Step("Select Cruise Room")
    private void selectCruiseRoom() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        Actions actions = new Actions(app.getDriver());

        WebElement showMore = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "#ibucru-10650039190-room-spread-367340-21687-1-s")));
        showMore.click();

        WebElement showMore2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "#ibucru-10650039190-room-spread-367340-21680-2-s")));
        actions.moveToElement(showMore2).perform();;

        WebElement plusButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "#ibucru-10650039190-room-num-367340-76026420-room-y > i")));
        plusButton.click();

        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "#ibucru-10650039190-room-next-367340 > button")));
        actions.moveToElement(nextButton).perform();
        nextButton.click();
    }

    @Step("Click Book")
    private void clickPayNow() {
        delay(3000);
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(30));
        WebElement pay = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector(
                                "#ibucru-10650039186-trackorder-payway > button")));
        Actions actions = new Actions(app.getDriver());
        actions.moveToElement(pay).perform();
        pay.click();
    }

    @Step("input Booking Field")
    private void inputBookingField(String Name, String email, String phone,int index) {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement nameField = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector("#ibucru-10650039186-passager-box-name > div.input-box-component_base_input_container.blur > div > input[type=text]")));
        Actions actions = new Actions(app.getDriver());
        actions.moveToElement(nameField);
        nameField.clear();
        nameField.sendKeys(Name);


        WebElement emailField = wait
            .until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#ibucru-10650039186-passager-box-email > div.input-box-component_base_input_container.with-prefix-icon.blur > div > input[type=text]")));
        emailField.clear();
        emailField.sendKeys(email);
                        
        WebElement phoneField = wait
            .until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#J-contactInfo > div > div:nth-child(2) > div > div > div > div:nth-child(1) > div > div.input-box-component_base_input_container.blur > div > input[type=text]")));
        phoneField.clear();
        phoneField.sendKeys(phone);
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

    @Step("Verify Fail Book Cruise No Input")
    private void verifyFailedBookCruiseNoInput() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#ibucru-10650039186-passager-box-name > div.input-box-component_base_bottom_container > div");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Fail Book Cruise Invalid Email")
    private void verifyFailedBookCruiseInvalidEmail() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#ibucru-10650039186-passager-box-email > div.input-box-component_base_bottom_container > div");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Fail Book Cruise Invalid Phone")
    private void verifyFailedBookCruiseInvalidPhone() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#J-contactInfo > div > div:nth-child(2) > div > div > div > div:nth-child(1) > div > div.input-box-component_base_bottom_container > div");

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
