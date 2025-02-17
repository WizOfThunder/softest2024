package com.example.selenium;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
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

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;


/**
 * Unit test for simple App.
 */
public class App01LoginTest
{
    private AppProject app;

     @BeforeClass
     @Description("Setup browser and open application using url")
    public void setUp(){
        app = new AppProject();
        app.openURL("https://trip.com/");
    }

    @Test
    @Feature("TC001 Success Login (using password)")
    public void TC001_testCorrectPassword(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

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

        takeScreenshot("TC001_TestCorrectPassword");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC002 Failed Login (invalid email)")
    public void TC002_testInvalidEmailLogin(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

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

        takeScreenshot("TC002_TestInvalidEmail");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC003 Failed Login (invalid password)")
    public void TC003_testIncorrectPassword(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

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

        takeScreenshot("TC003_TestIncorrectPassword");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC004 Failed Login (invalid verification code)")
    public void TC004_testIncorrectVerificationCode(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        WebDriver driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oh-pwa"))); // Wait for the pop up to appear
        
        closeBlockingElement();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con.mc-hd__account-nologin > span"))); // Wait for the email input form to appear

        signIn();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div > div > input"))); // Wait for the email input form to appear

        emailFill("idkwhytest@gmail.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.default-wrapper > div.ibu-password-login-btn-wrapper > span.password-login-btn-text"))); // Wait for the password field
        
        waitForToastModalToDisappear();

        verificationFill("123456");

        verifySignIn();
        
        verifyIncorrectVerificationCode();

        takeScreenshot("TC004_testIncorrectVerificationCode");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC005 Success Login (using verification code)")
    public void TC005_testSuccessLoginUsingVerificationCode(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        WebDriver driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oh-pwa")));
        
        closeBlockingElement();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con.mc-hd__account-nologin > span"))); // Wait for the email input form to appear

        signIn();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div > div > input"))); // Wait for the email input form to appear

        emailFill("akunistts@gmail.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.default-wrapper > div.ibu-password-login-btn-wrapper > span.password-login-btn-text"))); // Wait for the password field
        
        waitForToastModalToDisappear();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Retrieve the verification code from email
        String verificationCode = getVerificationCode(
            "imap.gmail.com",    // IMAP host
            "imap",              // Store type
            "akunistts@gmail.com", // Replace with actual email
            "nhse capw bewb ykmx"         // Replace with email password or app password
        );

        verificationFill(verificationCode); // Use the retrieved code

        verifySignIn();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con > div > div > span.mc-hd__account-username"))); // Wait for success message
        verifySuccessLogin();

        takeScreenshot("TC005_testSuccessLoginUsingVerificationCode");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    // @Test
    // @Feature("Retrieve Verification Code from Latest Email")
    // public void testRetrieveVerificationCodeFromEmail() {
    //     // // Wait 10 seconds for the email to arrive
    //     // try {
    //     //     Thread.sleep(10000);
    //     // } catch (InterruptedException e) {
    //     //     e.printStackTrace();
    //     // }

    //     // Retrieve the verification code
    //     String verificationCode = getVerificationCode(
    //         "imap.gmail.com",    // IMAP host
    //         "imap",              // Store type
    //         "akunistts@gmail.com", // Replace with actual email
    //         "nhse capw bewb ykmx"         // Replace with email password or app password
    //     );

    //     // Verify that the code is successfully retrieved
    //     if (verificationCode != null) {
    //         System.out.println("Verification Code Retrieved: " + verificationCode);
    //     } else {
    //         throw new RuntimeException("Failed to retrieve verification code from email.");
    //     }
    // }

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

    @Step("Click verification sign in button")
    private void verifySignIn(){
        app.getDriver().findElement(By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > button > div > span > span")).click();
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

    @Step("Input form with Verification Code:{verification_code}")
    private void verificationFill(String verification_code) {
        for (int i = 0; i < 6; i++) {
            WebElement verificationField = app.getDriver().findElement(By.xpath("(//*[@inputmode='numeric'])[" + (i + 1) + "]"));
            verificationField.sendKeys(Character.toString(verification_code.charAt(i)));
        }
    }

    @Step("Verify failed login with incorrect password")
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

    @Step("Verify failed login with incorrect verification code")
    private void verifyIncorrectVerificationCode() {
        WebDriver driver = app.getDriver();
    
        // Wait for the page or element to appear after login
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#ibu_login_online > div.clearfix > div > div.content_wrapper.textAlignLeft > form > div > div.input_wrapper > div > div.inputGroup-module__s_error_tips__nZZJg > span")
        ));
    
        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success message or profile element not displayed!");
    }

    @AfterMethod
    @Description("Reset to Main Page")
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

    @Step("Get Verification Code")
    public static String getVerificationCode(String host, String storeType, String user, String password) {
        try {
            // Set mail properties
            Properties properties = new Properties();
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.ssl.enable", "true");
            properties.put("mail.imap.ssl.trust", "*");
            //properties.put("mail.debug", "true");
        
            // Get the session
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore(storeType);
            store.connect(host, user, password);
        
            // Access the inbox folder
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
        
            // Check if there are any messages
            Message[] messages = emailFolder.getMessages();
            if (messages.length == 0) {
                System.out.println("No messages found in the inbox.");
                return null;
            }
        
            // Retrieve the latest email
            Message latestMessage = messages[messages.length - 1];
            String content = getTextFromMessage(latestMessage);
        
            // Log the email content for debugging
            System.out.println("Latest Email Content:\n" + content);
        
            // Extract the verification code (assumes it's a 6-digit number)
            Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                String code = matcher.group();
                emailFolder.close(false);
                store.close();
                return code;
            }
        
            // Close folder and store
            emailFolder.close(false);
            store.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("text/html")) {
            // If the message is HTML, you can return the plain text version
            return Jsoup.parse(message.getContent().toString()).text();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < mimeMultipart.getCount(); i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    result.append(bodyPart.getContent());
                } else if (bodyPart.isMimeType("text/html")) {
                    result.append(Jsoup.parse(bodyPart.getContent().toString()).text());
                }
            }
            return result.toString();
        }
        return "";
    }

    @AfterClass
    @Description("Close Browser")
    public void tearDown(){
        app.closBrowser();
    }
}
