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
public class App02RegisterTest
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
    @Feature("TC006 Failed Register (invalid Email)")
    public void TC006_testFailedRegisterInvalidEmail(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        closeBlockingElement();
        registerButton();
        emailFill("d");
        continueButton();

        verifyFailedRegisterInvalidEmail();
        takeScreenshot("TC006_testFailedRegisterInvalidEmail");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC007 Failed Register (password too short)")
    public void TC007_testFailedRegisterPasswordTooShort(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        closeBlockingElement();
        registerButton();
        emailFill("dummyFailSeleniumTest0@gmail.com");
        continueButton();
        passwordFill("123");
        createAccountButton();

        verifyFailedRegisterPasswordTooShort();
        takeScreenshot("TC007_testFailedRegisterPasswordTooShort");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC008 Success Register")
    public void TC008_testSuccessRegister(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        closeBlockingElement();
        registerButton();
        emailFill("SeleniumSoftTest@gmail.com");
        continueButton();
        passwordFill("testingSelenium");
        createAccountButton();

        verifySuccessRegister();
        takeScreenshot("TC008_testSuccessRegister");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
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

    @Step("Click button Register")
    private void registerButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement register = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con.mc-hd__account-nologin > span")));
        register.click();
    }

    @Step("Input email with Input:{email}")
    private void emailFill(String email){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div > div > input")));
        emailField.clear();
        emailField.sendKeys(email);
    }

    @Step("Click button Continue")
    private void continueButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > button > div > span")));
        button.click();
    }

    @Step("Input password with Input:{password}")
    private void passwordFill(String password){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pass = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@type='password']")));
        pass.sendKeys(password);
    }

    @Step("Click button Continue")
    private void createAccountButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > button > div > span > span")));
        button.click();
    }

    @Step("Verify failed register (invalid email)")
    private void verifyFailedRegisterInvalidEmail() {
        WebDriver driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Wait for the toast message to appear
        WebElement toastModal = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("div.toast_modal > div")
        ));

        // Assert the toast modal is displayed
        Assert.assertTrue(toastModal.isDisplayed(), "The toast modal with the error message is not displayed!");

        // Assert the text of the toast message
        String expectedToastMessage = "Oops! There is a problem with your Internet connection. Please try again.(10024)";
        Assert.assertEquals(toastModal.getText().trim(), expectedToastMessage, "The toast message text is incorrect!");
    }

    @Step("Verify failed register (password too short)")
    private void verifyFailedRegisterPasswordTooShort() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Passwords must be 8–20 characters and include numbers, letters, and symbols')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify success Register")
    private void verifySuccessRegister() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Welcome to Trip.com')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
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

    @AfterMethod
    @Description("Reset to Main Page")
    private void resetPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://trip.com/");
    }

    @AfterClass
    @Description("Close Browser")
    public void tearDown(){
        app.closBrowser();
    }
}
