package com.example.selenium;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Feature;

/**
 * Unit test for simple App.
 */
public class App1LoginTest
{
    private AppProject app;

     @BeforeClass
     @Description("Setup browser and open application using url")
    public void setUp(){
        app = new AppProject();
        app.openURL("https://trip.com/");
    }

    @Test
    @Feature("TC002 Success Login (using password)")
    public void TC002_testCorrectPassword(){
        WebDriver driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oh-pwa"))); // Wait for the pop up to appear
        
        closeBlockingElement();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con.mc-hd__account-nologin > span"))); // Wait for the email input form to appear

        signIn();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div > div > input"))); // Wait for the email input form to appear

        emailFill("seleniumtest@gmail.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.default-wrapper > div.ibu-password-login-btn-wrapper > span.password-login-btn-text"))); // Wait for the password field
        
        waitForToastModalToDisappear();
        goToPassword();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div:nth-child(2) > div > input"))); // Wait for the password input
        
        passwordFill("yangpentingjalan");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con > div > div > span.mc-hd__account-username"))); // Wait for success message
        
        verifySuccessLogin();

        takeScreenshot("TC002_TestCorrectPassword");

        resetPage();
    }

    @Test
    @Feature("TC003 Failed Login (invalid email)")
    public void TC003_testInvalidEmailLogin(){
        WebDriver driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oh-pwa"))); // Wait for the pop up to appear

        closeBlockingElement();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con.mc-hd__account-nologin > span"))); // Wait for the email input form to appear

        signIn();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div > div > input"))); // Wait for the email input form to appear

        emailFill("d");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.toast_modal > div"))); // Wait for the error to appear

        verifyInvalidEmail();

        takeScreenshot("TC003_TestInvalidEmail");

        resetPage();
    }

    @Test
    @Feature("TC005 Failed Login (invalid password)")
    public void TC005_testIncorrectPassword(){
        WebDriver driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oh-pwa"))); // Wait for the pop up to appear
        
        closeBlockingElement();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con.mc-hd__account-nologin > span"))); // Wait for the email input form to appear

        signIn();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div > div > input"))); // Wait for the email input form to appear

        emailFill("hansen.22@mhs.istts.ac.id");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.default-wrapper > div.ibu-password-login-btn-wrapper > span.password-login-btn-text"))); // Wait for the password field
        
        waitForToastModalToDisappear();
        goToPassword();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div:nth-child(2) > div > input"))); // Wait for the password input

        passwordFill("123");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div.input-module__ibu_input_item__EQTLR.input-module__input_item_error__LpLi1 > div.input-module__s_error_tips__bedHb"))); // Wait for the error message
    
        verifyIncorrectPassword();

        takeScreenshot("TC005_TestIncorrectPassword");
    }

    @Step("Close blocking element")
    private void closeBlockingElement() {
        WebDriver driver = app.getDriver();
        WebElement blockingElement = driver.findElement(By.cssSelector(".oh-pwa")); // Replace with correct selector if needed
            if (blockingElement.isDisplayed()) {
                // Dismiss the blocking element
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].style.display='none';", blockingElement);
            }
    }

    @Step("Click button sign in")
    private void signIn(){
        app.getDriver().findElement(By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con.mc-hd__account-nologin > span")).click();
    }

    @Step("Input form with Email:{email}")
    private void emailFill(String email){
        WebElement emailField=app.getDriver().findElement(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div > div > input"));
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

    @Step("Verify invalid email or error message")
    private void verifyInvalidEmail() {
        WebDriver driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Wait for the toast message to appear
        WebElement toastModal = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("div.toast_modal > div") // Selector for the toast message container
        ));

        // Assert the toast modal is displayed
        Assert.assertTrue(toastModal.isDisplayed(), "The toast modal with the error message is not displayed!");

        // Assert the text of the toast message
        String expectedToastMessage = "Oops! There is a problem with your Internet connection. Please try again.(10024)";
        Assert.assertEquals(toastModal.getText().trim(), expectedToastMessage, "The toast message text is incorrect!");
    }

    @Step("Input form with Password:{password}")
    private void passwordFill(String password){
        WebElement passwordField=app.getDriver().findElement(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div:nth-child(2) > div > input"));
        passwordField.clear();
        passwordField.sendKeys(password);

        app.getDriver().findElement(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > button")).click();
    }

    @Step("Verify success message or redirection after login")
    private void verifyIncorrectPassword() {
        WebDriver driver = app.getDriver();
    
        // Wait for the page or element to appear after login
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div.input-module__ibu_input_item__EQTLR.input-module__input_item_error__LpLi1 > div.input-module__s_error_tips__bedHb")
        ));
    
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Error incorrect password displayed");
    }

    @Step("Verify success message or redirection after login")
    private void verifySuccessLogin() {
        WebDriver driver = app.getDriver();
    
        // Wait for the page or element to appear after login
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con > div > div > span.mc-hd__account-username")
        ));
    
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success message or profile element not displayed!");
    }

    @Step("Reset Page")
    private void resetPage() {
        WebDriver driver = app.getDriver();
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://trip.com/");
    }

    @Step("Take Screenshot")
    private void takeScreenshot(String testName) {
        WebDriver driver = app.getDriver();
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
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }
    }

    @AfterClass
    @Description("Close Browser")
    public void tearDown(){
        app.closBrowser();
    }
}
