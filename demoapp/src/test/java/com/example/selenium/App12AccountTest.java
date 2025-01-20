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
import org.testng.annotations.BeforeMethod;
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

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Unit test for simple App.
 */
public class App12AccountTest
{
    private AppProject app;
    private static WebDriver driver;

    @BeforeClass
    @Description("Setup browser and open application using url")
    public void setUp(){
        app = new AppProject();
        app.openURL("https://trip.com/");
        driver = app.getDriver();

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
    }

    @BeforeMethod
    public void resetPage() {
        // // Clear cookies
        // driver.manage().deleteAllCookies();

        // // Clear localStorage and sessionStorage
        // JavascriptExecutor js = (JavascriptExecutor) driver;
        // js.executeScript("window.localStorage.clear();");
        // js.executeScript("window.sessionStorage.clear();");

        // Navigate to the desired URL
        driver.navigate().to("https://trip.com/");
    }

    @Test
    @Feature("TC066 Add Price Alerts")
    public void TC066_testAddPriceAlerts(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        closeBlockingElement();
        clickAccount();
        clickPriceAlertsDropdown();
        clickPriceAlerts();
        clickOneWay();
        InputLeavingFrom("Jakarta");
        InputGoingTo("Surabaya");
        InputDate(17, 2);
        clickNonstop();
        clickEnablePriceTracking();

        verifySuccessAddPriceAlerts();

        takeScreenshot("TC066_testAddPriceAlerts");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC067 Failed Add Promo Code (invalid code)")
    public void TC067_testFailAddPromoCode(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        closeBlockingElement();
        clickAccount();
        clickPromoCodes();
        inputPromoCode("000000000");
        addPromoCode();

        verifyFailedAddPromoCode();

        takeScreenshot("TC067_testFailAddPromoCode");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC068 Add Promo Code")
    public void TC068_testAddPromoCode(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
    
        closeBlockingElement();
        clickAccount();
        clickPromoCodes();
        inputPromoCode("TRIPBEST8");
        addPromoCode();

        verifySuccessAddPromoCode();

        takeScreenshot("TC068_testAddPromoCode");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC069 Failed Edit Personal Info (Display Name sudah terpakai)")
    public void TC069_testFailEditPersonalInfo(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        closeBlockingElement();
        clickAccount();
        clickManageMyAccount();
        clickEdit();
        inputPersonalInfo("a", "Female", "Singapore");
        clickSavePersonalInfo();

        verifyFailedEditProfile();

        takeScreenshot("TC069_testFailEditPersonalInfo");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC070 Edit Personal Info (Display Name, Gender, Country)")
    public void TC070_testEditPersonalInfo(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        closeBlockingElement();
        clickAccount();
        clickManageMyAccount();
        clickEdit();
        inputPersonalInfo("testcasesofttest001", "Female", "Singapore");
        clickSavePersonalInfo();

        verifySuccessEditProfile();

        takeScreenshot("TC070_testEditPersonalInfo");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC071 Add a New Contact")
    public void TC071_testAddNewContact(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        closeBlockingElement();
        clickAccount();
        clickManageMyAccount();
        clickContactInfoDropdown();
        clickContactInfoButton();
        clickDefaultContactOption();
        inputContactInfo("Gregorius Kendick", "akunistts@gmail.com", "82233765959");
        clickSaveContactInfoButton();

        verifySuccessAddContactInfo();

        takeScreenshot("TC071_testAddNewContact");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC072 Failed Reset Password (using password)")
    public void TC072_testFailedResetPasswordUsingPassword(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        
        closeBlockingElement();
        clickAccount();
        clickManageMyAccount();
        clickUpdatePassword();
        clickPasswordOption();
        inputOldPassword("passwordSalah");
        inputNewPassword("seleniumTest");
        clickSetNewPassword();
        verifyFailedSetPasswordUsingPassword();

        takeScreenshot("TC072_testFailedResetPasswordUsingPassword");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC073 Success Reset Password (using password)")
    public void TC073_testSuccessResetPasswordUsingPassword(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        
        closeBlockingElement();
        clickAccount();
        clickManageMyAccount();
        clickUpdatePassword();
        clickPasswordOption();
        inputOldPassword("yangpentingjalan");
        inputNewPassword("seleniumTest");
        clickSetNewPassword();
        verifySuccessSetPasswordUsingPassword();

        takeScreenshot("TC073_testSuccessResetPasswordUsingPassword");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC074 Failed Reset Password (using email verify)")
    public void TC074_testFailedResetPasswordUsingEmailVerify(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        
        closeBlockingElement();
        clickAccount();
        clickManageMyAccount();
        clickUpdatePassword();
        clickEmailOption();
        verificationFill("123456");
        clickContinueAfterVerify();
        verifyFailedSetPasswordUsingVerificationCode();

        takeScreenshot("TC074_testFailedResetPasswordUsingEmailVerify");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC075 Success Reset Password (using email verify)")
    public void TC075_testSuccessResetPasswordUsingEmailVerify(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        
        closeBlockingElement();
        clickAccount();
        clickManageMyAccount();
        clickUpdatePassword();
        clickEmailOption();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String verificationCode = getVerificationCode(
            "imap.gmail.com",    // IMAP host
            "imap",              // Store type
            "dummytestingistts@gmail.com", // Replace with actual email
            "pjcl hwqh mpcu ltqs"         // Replace with email password or app password
        );

        verificationFill(verificationCode);
        clickContinueAfterVerify();
        inputNewPasswordAfterVerify("yangpentingjalan");
        clickSetNewPassword();
        verifySuccessSetPasswordUsingVerificationCode();

        takeScreenshot("TC075_testSuccessResetPasswordUsingEmailVerify");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC076 Failed Update Email (invalid email)")
    public void TC076_testFailedUpdateEmailInvalidEmail(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        
        closeBlockingElement();
        clickAccount();
        clickManageMyAccount();
        clickUpdateEmail();
        clickSendVerificationCode();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String verificationCode = getVerificationCode(
            "imap.gmail.com",    // IMAP host
            "imap",              // Store type
            "dummytestingistts@gmail.com", // Replace with actual email
            "pjcl hwqh mpcu ltqs"         // Replace with email password or app password
        );

        verificationFill(verificationCode);
        clickContinueAfterVerify();

        inputNewEmail("d");
        clickContinueEmail();

        verifyFailedSetEmailIncorrectEmail();

        takeScreenshot("TC076_testFailedUpdateEmailInvalidEmail");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC077 Failed Update Email (invalid code)")
    public void TC077_testFailedUpdateEmailInvalidCode(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        
        closeBlockingElement();
        clickAccount();
        clickManageMyAccount();
        clickUpdateEmail();

        clickSendVerificationCode();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String verificationCode = getVerificationCode(
            "imap.gmail.com",    // IMAP host
            "imap",              // Store type
            "dummytestingistts@gmail.com", // Replace with actual email
            "pjcl hwqh mpcu ltqs"         // Replace with email password or app password
        );

        verificationFill(verificationCode);
        clickContinueAfterVerify();

        inputNewEmail("seleniumsofttest@gmail.com");
        clickContinueEmail();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        verificationFill("123456");
        clickContinueAfterVerifyEmail();

        verifyFailedSetEmailIncorrectVerificationCode();

        takeScreenshot("TC077_testFailedUpdateEmailInvalidCode");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC078 Success Update Email")
    public void TC078_testSuccessUpdateEmail(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        
        closeBlockingElement();
        clickAccount();
        clickManageMyAccount();
        clickUpdateEmail();

        clickSendVerificationCode();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String verificationCode1 = getVerificationCode(
            "imap.gmail.com",    // IMAP host
            "imap",              // Store type
            "dummytestingistts@gmail.com", // Replace with actual email
            "pjcl hwqh mpcu ltqs"         // Replace with email password or app password
        );

        verificationFill(verificationCode1);
        clickContinueAfterVerify();

        inputNewEmail("seleniumsofttest@gmail.com");
        clickContinueEmail();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String verificationCode = getVerificationCode(
            "imap.gmail.com",    // IMAP host
            "imap",              // Store type
            "seleniumsofttest@gmail.com", // Replace with actual email
            "dvbo tbuu dxak rmey"         // Replace with email password or app password
        );

        verificationFill(verificationCode);
        clickContinueAfterVerifyEmail();

        verifySuccessSetEmail();

        takeScreenshot("TC078_testSuccessUpdateEmail");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC079 Member Tiers Account Navigation")
    public void TC079_testMemberTiersNavigation(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        
        closeBlockingElement();
        clickAccount();
        clickMemberTiers();
        verifySuccessMemberTiersNavigation();

        takeScreenshot("TC079_testMemberTiersNavigation");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC080 My Bookings Account Navigation")
    public void TC080_testMyBookingsNavigation(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        
        closeBlockingElement();
        clickAccount();
        clickMyBookings();
        //clickCancelVerifyAccount();
        verifySuccessMyBookingsNavigation();

        takeScreenshot("TC080_testMyBookingsNavigation");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC081 Trip Coins Account Navigation")
    public void TC081_testTripCoinsNavigation(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        
        closeBlockingElement();
        clickAccount();
        clickTripCoins();
        verifySuccessTripCoinsNavigation();

        takeScreenshot("TC081_testTripCoinsNavigation");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC082 Favorites Account Navigation")
    public void TC082_testFavoritesNavigation(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        
        closeBlockingElement();
        clickAccount();
        clickFavorites();
        verifySuccessFavoritesNavigation();

        takeScreenshot("TC082_testFavoritesNavigation");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC083 My Posts Account Navigation")
    public void TC083_testMyPostsNavigation(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        
        closeBlockingElement();
        clickAccount();
        clickMyPosts();
        verifySuccessMyPostsNavigation();

        takeScreenshot("TC083_testMyPostsNavigation");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
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

    @Step("Click Account")
    private void clickAccount(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibuHeaderMenu > div > div.mc-hd__account.mc-hd__dropdown-con > div > div > span.mc-hd__account-username")));
        acc.click();
    }

    @Step("Click Flight Price Alerts Dropdown")
    private void clickPriceAlertsDropdown(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.tooltip-pop-con.mc-hd__en-id > div.tooltip-pop > div > ul > li:nth-child(7) > div > span")));
        acc.click();
    }

    @Step("Click Add Price Alerts button")
    private void clickPriceAlerts(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(20));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#main > div > div > div.m-main-list__wrapper.flex > div.m-main-list__cnt.flex > div > div.o-btn-v1_undefined-1x.mr-16.o-btn-v1_blue.add-price-alert")));
        acc.click();
    }

    @Step("Click One-Way option")
    private void clickOneWay(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#dialogWrapper > div > div > div > div > div.ift-modal-content.ift-modal-content__scroll.ps > div.mod-price-alert__modal > div > div.mod-pa-select-trip__item.flex.mt-16 > div > div.mode-type-item.mode-type-checked > span.fui-radio.fui-checked > i")));
        acc.click();
    }

    @Step("Input Leaving From")
    private void InputLeavingFrom(String city){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#dialogWrapper > div > div > div > div > div.ift-modal-content.ift-modal-content__scroll.ps > div.mod-price-alert__modal > div > div.price-alert-search-wrapper > div.side-route-set > div > div:nth-child(1) > div > div > div > input")));
        acc.click();
        acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/ul//*[contains(text(),'"+city+"')]")));
        acc.click();
    }

    @Step("Input Going To")
    private void InputGoingTo(String city){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#dialogWrapper > div > div > div > div > div.ift-modal-content.ift-modal-content__scroll.ps > div.mod-price-alert__modal > div > div.price-alert-search-wrapper > div.side-route-set > div > div:nth-child(3) > div > div > div > input")));
        acc.click();
        acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/ul//*[contains(text(),'"+city+"')]")));
        acc.click();
    }

    @Step("Input Date")
    private void InputDate(int day, int month){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#dialogWrapper > div > div > div > div > div.ift-modal-content.ift-modal-content__scroll.ps > div.mod-price-alert__modal > div > div.price-alert-search-wrapper > div.price-alert-date-wrapper.flex.mt-8.w100p > div.form-input-wrapper.flex > input")));
        acc.click();
        acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='main']/div[2]/div[1]/div[2]/div["+month+"]//*[contains(text(),'"+day+"')]")));
        acc.click();
    }

    @Step("Click Nonstop only checkbox")
    private void clickNonstop(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"dialogWrapper\"]/div/div/div/div/div[2]/div[3]/div/span/span/i")));
        acc.click();
    }

    @Step("Click Enable 24h Price Tracking")
    private void clickEnablePriceTracking(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#dialogWrapper > div > div > div > div > div.ift-modal-content.ift-modal-content__scroll.ps > div.mod-price-alert__modal > div > div.mod-price-alert__content > div > div.mod-pa-btnWrap.mb-8.showRadius > div")));
        acc.click();
    }

    @Step("Click Promo Codes Dropdown")
    private void clickPromoCodes(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.tooltip-pop-con.mc-hd__en-id > div.tooltip-pop > div > ul > li:nth-child(3) > div > span")));
        acc.click();
    }

    @Step("Input Promo Code")
    private void inputPromoCode(String promo_code){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='main']/div[2]/div/div[2]/div[1]/div/div/input")));
        acc.sendKeys(promo_code);
    }

    @Step("Click Add Promo Code Button")
    private void addPromoCode(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#main > div.ibu-promo.newTheme.en-id > div > div.coins-con > div.section.new.input > div > div > div > button > div > span")));
        acc.click();
    }

    @Step("Click Manage My Account Dropdown")
    private void clickManageMyAccount(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.tooltip-pop-con.mc-hd__en-id > div.tooltip-pop > div > ul > li:nth-child(4) > div > span")));
        acc.click();
    }

    @Step("Click Edit in Profile")
    private void clickEdit(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='__next']/div[2]/div/div[2]/div[3]/div/div[1]/div[2]/div[2]")));
        acc.click();
    }

    @Step("Input Personal Info Name: {name}, Gender: {gender}, Country: {country}")
    private void inputPersonalInfo(String name, String gender, String country){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.online-account-manage-wrap > div > div.right-wrapper > div:nth-child(3) > div.edit-profile-wrap > div > div > div > div:nth-child(3) > div > input[type=text]")));
        acc.sendKeys(name);
        if (!name.equals("a")) {
            acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.online-account-manage-wrap > div > div.right-wrapper > div:nth-child(3) > div.edit-profile-wrap > div > div > div > div.name-input.gender-input-wrap > div > input[type=text]")));
            acc.click();
            acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'"+gender+"')]")));
            acc.click();
            acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibu_region_selector > div > div > div.selected-box > div")));
            acc.click();
            acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibu_region_selector > div > div > div.country-list.show.ttb > div.country-list_top-box > ul.selected-list > li > input")));
            acc.sendKeys(country);
            acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[contains(text(),'"+country+"')])[1]")));
            acc.click();            
        }
    }

    @Step("Click Save Personal Info Button")
    private void clickSavePersonalInfo(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Save')]")));
        acc.click();
    }

    @Step("Click Contact Info Dropdown")
    private void clickContactInfoDropdown(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='__next']/div[2]/div[1]/div/div[1]/div/div//*[contains(text(),'Contact Info')]")));
        acc.click();
    }

    @Step("Click Add a New Contact button")
    private void clickContactInfoButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Add a New Contact')]")));
        acc.click();
    }

    @Step("Click default contact option")
    private void clickDefaultContactOption(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.contacts_global-wrapper__34HhN > div.Drawer_drawer-container__5kPCS.drawer-enter-done > div.ContactModify_modify-wrapper__O1SCM > div.SetDefaultContacts_set-default-wrapper__k_qMY > div.right-wrapper > div > div")));
        acc.click();
    }

    @Step("Click Member Tiers Dropdown")
    private void clickMemberTiers(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.tooltip-pop-con.mc-hd__en-id > div.tooltip-pop > div > ul > li:nth-child(8) > div > span")));
        acc.click();
    }

    @Step("Click My Bookings Dropdown")
    private void clickMyBookings(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.tooltip-pop-con.mc-hd__en-id > div.tooltip-pop > div > ul > li:nth-child(1) > div > span")));
        acc.click();
    }

    @Step("Click Cancel Verify Account")
    private void clickCancelVerifyAccount(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div:nth-child(3) > div.orders_new-order-wrap__XYRTs > div:nth-child(2) > div.temp_bg.hide_order_container > div > div.common_alert_content > div.new_button_wrap > button.tripui-online-btn.tripui-online-btn-middle.tripui-online-btn-wireframe-primary > div > span")));
        acc.click();
    }

    @Step("Click Trip Coins Dropdown")
    private void clickTripCoins(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.tooltip-pop-con.mc-hd__en-id > div.tooltip-pop > div > ul > li:nth-child(2) > div > span")));
        acc.click();
    }

    @Step("Click Favorites Dropdown")
    private void clickFavorites(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.tooltip-pop-con.mc-hd__en-id > div.tooltip-pop > div > ul > li:nth-child(5) > div > span")));
        acc.click();
    }

    @Step("Click My Posts Dropdown")
    private void clickMyPosts(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.tooltip-pop-con.mc-hd__en-id > div.tooltip-pop > div > ul > li:nth-child(6) > div > span")));
        acc.click();
    }

    @Step("Input Contact Info Name: {contact_name}, Email: {email}, Phone Number: {phone}")
    private void inputContactInfo(String contact_name, String email, String phone){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.contacts_global-wrapper__34HhN > div.Drawer_drawer-container__5kPCS.drawer-enter-done > div.ContactModify_modify-wrapper__O1SCM > div:nth-child(2) > div.suJf9 > div.tripui-online-input > div > input[type=text]")));
        acc.sendKeys(contact_name);
        acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.contacts_global-wrapper__34HhN > div.Drawer_drawer-container__5kPCS.drawer-enter-done > div.ContactModify_modify-wrapper__O1SCM > div:nth-child(2) > div.suJf9 > div.YfH2s > div > div > input[type=text]")));
        acc.sendKeys(email);
        acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.contacts_global-wrapper__34HhN > div.Drawer_drawer-container__5kPCS.drawer-enter-done > div.ContactModify_modify-wrapper__O1SCM > div:nth-child(2) > div.suJf9 > div.zqU2W.phone-number-reset > div.tripui-online-input > div > input[type=text]")));
        acc.sendKeys(phone);
    }

    @Step("Click Save New Contact button")
    private void clickSaveContactInfoButton(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='__next']/div[2]/div[4]//*[contains(text(),'Save')]")));
        acc.click();
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

    @Step("Click From Text Field")
    private void clickFromTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement from = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='search']/div[2]/div[1]/div[2]/input")));
        from.click();
    }

    @Step("Click Update Password")
    private void clickUpdatePassword(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.online-account-manage-wrap > div > div.right-wrapper > div:nth-child(1) > div > div.account-security-container > div > div:nth-child(3) > div > div.card-right-wrapper > div")));
        acc.click();
    }

    @Step("Click Password Option")
    private void clickPasswordOption(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Verify your current password')]")));
        acc.click();
    }

    @Step("Input Old Password")
    private void inputOldPassword(String old_password){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='ibu-password-group']/div/div[1]/input)[1]")));
        acc.sendKeys(old_password);
    }

    @Step("Input New Password")
    private void inputNewPassword(String new_password){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='ibu-password-group']/div/div[1]/input)[2]")));
        acc.sendKeys(new_password);
    }

    @Step("Click Set New Password Button")
    private void clickSetNewPassword(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Set New Password')]")));
        acc.click();
    }

    @Step("Click Email Option")
    private void clickEmailOption(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Send a verification code to')]")));
        acc.click();
    }

    @Step("Input form with Verification Code:{verification_code}")
    private void verificationFill(String verification_code) {
        for (int i = 0; i < 6; i++) {
            WebElement verificationField = app.getDriver().findElement(By.xpath("(//*[@inputmode='numeric'])[" + (i + 1) + "]"));
            verificationField.sendKeys(Character.toString(verification_code.charAt(i)));
        }
    }

    @Step("Click Continue After Inputting Verification Code")
    private void clickContinueAfterVerify(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#verifyEmail > div > div > div > div.content-main-wrapper_pe24u.content-main-wrapper_8jejjh.content-main-wrapper_td6wru.content-main-wrapper_kl4bb.content-main-wrapper_cczse8.content-main-wrapper_h2plss.content-main-wrapper_v26hhg.content-main-wrapper_vzx25v.content-main-wrapper_16kza.content-main-wrapper_guj92p > div.content-main-wrapper-form_7bo0q.content-main-wrapper-form_dsmpsg.content-main-wrapper-form_7fiu37.content-main-wrapper-form_d6t3fs.content-main-wrapper-form_u69d6b.content-main-wrapper-form_udj7hm.content-main-wrapper-form_tg3sra.content-main-wrapper-form_n2amkm > button > div > span")));
        acc.click();
    }

    @Step("Input New Password after Email Verification")
    private void inputNewPasswordAfterVerify(String new_password){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibu-password-group > div > div.input-module__r_input__-Yspv > input")));
        acc.sendKeys(new_password);
    }

    @Step("Click Set New Password Button")
    private void clickSetNewPasswordAfterVerify(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.online-account-manage-wrap > div > div.right-wrapper > div:nth-child(1) > div > div.account-security-container > div.account-modal-wrapper > div > div > div > div.content-wrapper > form > div.form-submit > button > div > span")));
        acc.click();
    }

    @Step("Click Update Email")
    private void clickUpdateEmail(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#__next > div.online-account-manage-wrap > div > div.right-wrapper > div:nth-child(1) > div > div.account-security-container > div > div:nth-child(1) > div > div.card-right-wrapper > div")));
        acc.click();
    }

    @Step("Input New Email")
    private void inputNewEmail(String new_email){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#\\#i18n_flow > div > div > div > div > div > div.content-main-wrapper_zpkmk.content-main-wrapper_rx1x63.content-main-wrapper_h4urp4.content-main-wrapper_oz1zaj.content-main-wrapper_nvkcek.content-main-wrapper_vj9hjp > div.content-main-wrapper-form_wh4oh6.content-main-wrapper-form_yvfjwj.content-main-wrapper-form_j9ppih.content-main-wrapper-form_b4u6i > div > div > div > input")));
        acc.sendKeys(new_email);
    }

    @Step("Click Continue Button after inputting new email")
    private void clickContinueEmail(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#\\#i18n_flow > div > div > div > div > div > div.content-main-wrapper_zpkmk.content-main-wrapper_rx1x63.content-main-wrapper_h4urp4.content-main-wrapper_oz1zaj.content-main-wrapper_nvkcek.content-main-wrapper_vj9hjp > div.content-main-wrapper-form_wh4oh6.content-main-wrapper-form_yvfjwj.content-main-wrapper-form_j9ppih.content-main-wrapper-form_b4u6i > button > div > span")));
        acc.click();
    }

    @Step("Click Continue Button verifying email")
    private void clickContinueAfterVerifyEmail(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#verifyEmail > div > div > div > div.content-main-wrapper_pe24u.content-main-wrapper_8jejjh.content-main-wrapper_td6wru.content-main-wrapper_kl4bb.content-main-wrapper_cczse8.content-main-wrapper_h2plss.content-main-wrapper_v26hhg.content-main-wrapper_vzx25v.content-main-wrapper_16kza.content-main-wrapper_guj92p > div.content-main-wrapper-form_7bo0q.content-main-wrapper-form_dsmpsg.content-main-wrapper-form_7fiu37.content-main-wrapper-form_d6t3fs.content-main-wrapper-form_u69d6b.content-main-wrapper-form_udj7hm.content-main-wrapper-form_tg3sra.content-main-wrapper-form_n2amkm > button > div > span")));
        acc.click();
    }

    @Step("Click Send Verification Code")
    private void clickSendVerificationCode(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement acc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Send Verification Code')]")));
        acc.click();
    }

    @Step("Verify success Add Price Alerts")
    private void verifySuccessAddPriceAlerts() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'24-hour price monitoring is active!')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Failed Add Promo Code")
    private void verifyFailedAddPromoCode() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Sorry, this promo code does not exist. Please confirm you have entered it correctly.')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Add Promo Code")
    private void verifySuccessAddPromoCode() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Added')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Failed Edit Personal Info (Nama sudah terpakai)")
    private void verifyFailedEditProfile() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'This display name is already taken, please try another name')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Edit Personal Info")
    private void verifySuccessEditProfile() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Under review')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Add New Contact Info")
    private void verifySuccessAddContactInfo() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Please update info')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Member Tiers Navigation")
    private void verifySuccessMemberTiersNavigation() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "(//*[contains(text(),'Loyalty Program')])[2]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success My Bookings Navigation")
    private void verifySuccessMyBookingsNavigation() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[@id='__next']/div[2]/div[2]/div[1]/div[2]//*[contains(text(),'My Bookings')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Trip Coins Navigation")
    private void verifySuccessTripCoinsNavigation() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "(//*[contains(text(),'Loyalty Program')])[2]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Favorites Navigation")
    private void verifySuccessFavoritesNavigation() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.cssSelector(
                "#__next > div:nth-child(3) > div > div > div.main > div.fav_model > div.otherContent > div.collect-noresult.d-block > i");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success My Posts Navigation")
    private void verifySuccessMyPostsNavigation() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "(//*[contains(text(),'Trip Moments')])[2]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Failed Set New Password using Password")
    private void verifyFailedSetPasswordUsingPassword() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Incorrect password. Please try again.')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Set Password using Password")
    private void verifySuccessSetPasswordUsingPassword() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Password Updated')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Failed Set New Password using Verification Code")
    private void verifyFailedSetPasswordUsingVerificationCode() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Verification code error, please check and try again')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Set Password using Password")
    private void verifySuccessSetPasswordUsingVerificationCode() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Password Updated')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Failed Set New Email using Invalid Email")
    private void verifyFailedSetEmailIncorrectEmail() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Please provide a valid email')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Failed Set New Email using Verification Code")
    private void verifyFailedSetEmailIncorrectVerificationCode() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Verification code error, please check and try again')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Success Set Email")
    private void verifySuccessSetEmail() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[contains(text(),'Email Address Changed')]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    // @Step("Reset Main Page")
    // private void resetPage() {
    //     driver.manage().deleteAllCookies();

    //     // Execute JavaScript to clear localStorage and sessionStorage
    //     JavascriptExecutor js = (JavascriptExecutor) driver;
    //     js.executeScript("window.localStorage.clear();");
    //     js.executeScript("window.sessionStorage.clear();");

    //     driver.navigate().to("https://trip.com/");
    // }
    
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
