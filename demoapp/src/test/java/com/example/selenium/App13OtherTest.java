package com.example.selenium;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.Keys;

import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;

public class App13OtherTest {
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
    @Feature("TC085 Fail Book eSIM And SIM (Empty Field)")
    public void TC085_testFailBookSIMEmptyField() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        navigateToAttractionAndToursPage();
        selectESIM();
        selectPackage();
        delay(5000);
        scrollDown(1);
        clickBookNow();
        delay(500);
        takeScreenshot("TC085_testFailBookSIMEmptyField");
        resetPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC086 Fail Book eSIM And SIM (Empty Contact)")
    public void TC086_testFailBookSIMEmptyContact() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        navigateToAttractionAndToursPage();
        selectESIM();
        selectPackage();
        delay(5000);
        scrollDown(2);
        clickOptionType();
        clickNumberOfDays();
        delay(500);
        clickBookNow();
        clickPay();
        verifyFailBookSIMNoContact();
        takeScreenshot("TC086_testFailBookSIMEmptyContact");
        resetPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC087 Fail Book eSIM And SIM (Invalid Email)")
    public void TC087_testFailBookSIMInvalidEmail() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        navigateToAttractionAndToursPage();
        selectESIM();
        selectPackage();
        delay(5000);
        scrollDown(3);
        clickOptionType();
        clickNumberOfDays();
        delay(500);
        clickBookNow();
        inputBookingField("akun", "akun", "85246888888");
        clickPay();
        verifyFailBookSIMInvalidEmail();
        takeScreenshot("TC087_testFailBookSIMInvalidEmail");
        resetPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC088 Fail Book eSIM And SIM (Invalid Phone)")
    public void TC088_testFailBookSIMInvalidPhone() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        navigateToAttractionAndToursPage();
        selectESIM();
        selectPackage();
        delay(5000);
        scrollDown(4);
        clickOptionType();
        clickNumberOfDays();
        delay(500);
        clickBookNow();
        inputBookingField("akun", "akunistts@gmail.com", "0");
        clickPay();
        verifyFailBookSIMInvalidPhone();
        takeScreenshot("TC088_testFailBookSIMInvalidPhone");
        resetPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC089 Change Language")
    public void TC089_testChangeLanguage() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        declineAddToDesktop();
        clickFlagIcon();
        selectLanguage();
        verifyChangeLanguange();
        delay(2000);
        takeScreenshot("TC089_testChangeLanguage");
        resetPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }
    
    @Test
    @Feature("TC090 Change Currency")
    public void TC090_testChangeCurrency() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        declineAddToDesktop();
        clickCurrencyIcon();
        selectCurrency();
        verifyChangeCurrency();
        delay(2000);
        takeScreenshot("TC090_testChangeCurrency");
        resetPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Step("Navigate to Attraction & Tours page")
    private void navigateToAttractionAndToursPage() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement tourPage = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#header_action_nav_ttd")));
        Actions actions = new Actions(driver);
        actions.moveToElement(tourPage).perform();
    }

    @Step("Navigate to Attraction & Tours page")
    private void scrollDown(int index) {
        List<String> windowHandles = new ArrayList<>(app.getDriver().getWindowHandles());
        driver.switchTo().window(windowHandles.get(index));
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ARROW_DOWN);
    }
    
    @Step("input Booking Field")
    private void inputBookingField(String Name, String email, String phone) {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement nameField = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector("#contactModule > div > div:nth-child(1) > div > input")));
        Actions actions = new Actions(app.getDriver());
        actions.moveToElement(nameField);
        nameField.clear();
        nameField.sendKeys(Name);


        WebElement emailField = wait
            .until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#contactModule > div > div:nth-child(3) > div > div.input-com-container > input")));
        emailField.clear();
        emailField.sendKeys(email);

        WebElement bodyElement = wait
        .until(ExpectedConditions
            .visibilityOfElementLocated(By.tagName("body")));
        bodyElement.click();
                        
        WebElement phoneField = wait
            .until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#region-selector-component > div > input")));
        phoneField.clear();
        phoneField.sendKeys(phone);
    }

    @Step("Select eSIM")
    private void selectESIM() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement eSIM = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector("#header_action_nav_eSIM\\ \\&\\ SIM")));
        eSIM.click();
    }

    @Step("click book now")
    private void clickBookNow() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector(
                                "#anchor_1006_real_dom > div.xtaro-xview.style_m_sku_wrapper__4tMZW > div > div > div > div.xtaro-xview.style_m_quality_btn_wrapper__iTbj_ > div.xtaro-xview.sku-footer_m_footer_detail__lzKof > div > div:nth-child(2) > div > div > div > div.xtaro-xview.footer_m_footer_btn_box__2A8Lx > div > div > div > div > span")));
        Actions actions = new Actions(driver);
        actions.moveToElement(button);
        button.click();
        button.click();
    }

    @Step("click Pay now")
    private void clickPay() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector(
                                "#__next > div.ottd-online-container > div.ottd-right-container > div > div > div.footer > button")));
        button.click();
    }

    @Step("select Option Type")
    private void clickOptionType() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector(
                                "#\\31 777")));
        button.click();
    }

    @Step("select Number of Days")
    private void clickNumberOfDays() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement button = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector(
                                "#\\31 920 > div")));
        button.click();
    }

    @Step("Select package")
    private void selectPackage() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement packages = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector(
                                "#cloud-component-sales-tabs_stru_9Jp47b4WN4f3iX7 > div:nth-child(3) > div.tab-pane.tab-pane-active > div > div > div > div:nth-child(1)")));
        Actions actions = new Actions(driver);
        actions.moveToElement(packages);
        packages.click();

        WebElement packages2 = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector(
                                "#cloud-component-sales-tabs_stru_9Jp47b4WN4f3iX7 > div:nth-child(3) > div.tab-pane.tab-pane-active > div > div > div > div:nth-child(1) > a > div.fpc-tnt-new-card-item__info-con > div.fpc-tnt-new-card-item__name")));
        packages2.click();    
    
    }

    @Step("Click FLag Icon")
    private void clickFlagIcon() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement flag = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "#ibuHeaderMenu > div > div.mc-hd__option.mc-hd__option__locale > div > div")));
        flag.click();
    }

    @Step("decline add to desktop")
    private void declineAddToDesktop() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement decline = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "#modals > div > div.oh-pwa__close > svg")));
        decline.click();
    }

    @Step("Click Currency Icon")
    private void clickCurrencyIcon() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement currency = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "#ibuHeaderMenu > div > div.mc-hd__option.mc-hd__option__currency > div > div")));
        currency.click();
    }

    @Step("Select Currency")
    private void selectCurrency() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement currency = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "body > div.tcp-modal-mask > div > div.tcp-modal-body > div > div > div:nth-child(2) > ul > li:nth-child(2) > div")));
        currency.click();
    }

    @Step("Click Select Language")
    private void selectLanguage() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement flag = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "body > div.tcp-modal-mask > div > div.tcp-modal-body > div > div > div.mc-dropdown-country__cnt.mc-dropdown-country__all > div > ul > li:nth-child(8) > div > div.mc-dropdown-country__item-online")));
        flag.click();
    }

    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Verify Change Language")
    private void verifyChangeLanguange() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#ibuHeaderMenu > div > div:nth-child(1) > div > span");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Change Language")
    private void verifyChangeCurrency() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#ibuHeaderMenu > div > div.mc-hd__option.mc-hd__option__currency > div > div");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Fail Book eSIM no contact")
    private void verifyFailBookSIMNoContact() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#contactModule > div > div:nth-child(1) > div > p.error-txt");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Fail Book eSIM Invalid Email")
    private void verifyFailBookSIMInvalidEmail() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#contactModule > div > div:nth-child(3) > div > div.input-com-container > p.error-txt");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Fail Book eSIM Invalid Phone")
    private void verifyFailBookSIMInvalidPhone() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#contactModule > div > div:nth-child(2) > div > p.error-txt");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
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

    @Step("Reset Page")
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
