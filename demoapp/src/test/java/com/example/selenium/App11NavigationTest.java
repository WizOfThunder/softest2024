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

/**
 * Unit test for simple App.
 */
public class App11NavigationTest
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

    @BeforeMethod
    public void resetPage() {
        // Clear cookies
        driver.manage().deleteAllCookies();

        // Clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        // Navigate to the desired URL
        driver.navigate().to("https://trip.com/");
    }

    @Test
    @Feature("TC061 Link Navigation")
    public void TC061_testLinkNavigation(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickFooterLink("Customer Support");
        verifySuccessLinkNavigation();
        takeScreenshot("TC061_testLinkNavigation");
        //resetPage();
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC062 Promo Navigation")
    public void TC062_testPromoNavigation(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickPromo(1);
        verifySuccessPromoNavigation();
        takeScreenshot("TC062_testPromoNavigation");
        //resetPage();
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC063 Header Navigation Map")
    public void TC063_testHeaderNavigationMap(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickMapHeader();
        verifySuccessMapHeaderNavigation();
        takeScreenshot("TC063_testHeaderNavigationMap");
        //resetPage();
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC064 Header Navigation Trip.com Rewards")
    public void TC064_testHeaderNavigationTripRewards(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        clickTripRewardsHeader();
        verifySuccessTripRewardsHeaderNavigation();
        takeScreenshot("TC064_testHeaderNavigationTripRewards");
        //resetPage();
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC065 Header Navigation Deals")
    public void TC065_testHeaderNavigationDeals(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        closeBlockingElement();
        clickDealsHeader();
        verifySuccessDealsHeaderNavigation();
        takeScreenshot("TC065_testHeaderNavigationDeals");
        //resetPage();
        
        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Step("Click Promo")
    private void clickPromo(int number){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement promo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='deals']/div[2]/div[2]/div/div["+(number+1)+"]/div/a/img")));
        promo.click();
    }

    @Step("Click Footer Link")
    private void clickFooterLink(String link){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement linkNav = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='main']/div[4]/div/div[1]//*[contains(text(),'"+ link +"')][1]")));
        linkNav.click();
    }

    @Step("Click Map Header")
    private void clickMapHeader(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement map = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#header_action_nav_tripmap")));
        map.click();
    }

    @Step("Click Trip.com Rewards Header")
    private void clickTripRewardsHeader(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement rewards = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#header_action_nav_tripcoins")));
        rewards.click();
    }

    @Step("Click Deals Header")
    private void clickDealsHeader(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement deals = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#header_action_nav_sales")));
        deals.click();
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
        WebElement from = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"search\"]/div[2]/div[1]/div[2]/input")));
        from.click();
    }

    @Step("Verify success link navigation (Customer Support)")
    private void verifySuccessLinkNavigation() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.xpath(
                "//*[@id='main']/div/div[3]/div[2]/div//*[contains(text(),'Customer Support')][1]");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify success promo navigation (Hello Malaysia)")
    private void verifySuccessPromoNavigation() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.cssSelector(
                "#foxpage-app > div.tcp-background-wrapper-stru_6BqNkcFa08q6VWd");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify success Map Header Navigation")
    private void verifySuccessMapHeaderNavigation() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.cssSelector(
                "#poi-filter-btn > div.xtaro-xview.xt-xview.common-filter-btn_commonFilterWrap__2TrwP > div > div.xtaro-xview.xt-xview.common-filter-btn_filterBtnInner___jAqG > span");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify success Trip.com Rewards Header Navigation")
    private void verifySuccessTripRewardsHeaderNavigation() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.cssSelector(
                "#main > div.loyalty-online.en-id > div.main-bg > div.main-bg--logo");
        
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify success Deals Header Navigation")
    private void verifySuccessDealsHeaderNavigation() {
        driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(100));
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        By successElementSelector = By.cssSelector(
                "#foxpage-app > div.top-deals_root > h1");
        
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
