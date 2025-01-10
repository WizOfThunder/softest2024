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
public class App03SearchTest
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
    @Feature("TC009 Search bar (by input)")
    public void TC009_testSearchByInput(){
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibuHeaderSearch > div > div > div > div.gccpoi__TripSearchBox-content > input"))); // Wait for the search bar to appear

        searchFill("cruise");
       
        searchButton();

        verifySuccessSearchByInput("cruise");

        takeScreenshot("TC009_testSearchByInput");

        resetPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC010 Search bar (by choice)")
    public void TC010_testSearchByChoice() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibuHeaderSearch > div > div > div > div.gccpoi__TripSearchBox-content > input")));
        searchFill("cruise");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ibuHeaderSearch > div > div > div > div.gccpoi__TripSearchBox-content > div > div:nth-child(1) > div > div > span.gccpoi__TripSearchBox-layer-subtitle")));

        selectChoice("Disney Cruise Line");

        verifySuccessSearchByChoice("Disney Cruise Line");

        takeScreenshot("TC010_testSearchByChoice");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Step("Click button search")
    private void searchButton(){
        app.getDriver().findElement(By.cssSelector("#ibuHeaderSearch > div > div > div > div.gccpoi__TripSearchBox-btn > div > div")).click();
    }

    @Step("Click dropdown choice")
    private void selectChoice(String choice) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Dynamically build the XPath using the 'choice' parameter
        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//*[@id='ibuHeaderSearch']//*[contains(text(), '" + choice.split(" ")[0] + "') and contains(., '" + choice.split(" ")[1] + "') and contains(., '" + choice.split(" ")[2] + "')])[1]")
        ));
    
        firstOption.click();
    }

    @Step("Click search bar")
    private void search(){
        app.getDriver().findElement(By.cssSelector("#ibuHeaderSearch > div > div > div > div.gccpoi__TripSearchBox-content > input")).click();
    }

    @Step("Input search with Input:{input}")
    private void searchFill(String input){
        WebElement emailField=app.getDriver().findElement(By.cssSelector("#ibuHeaderSearch > div > div > div > div.gccpoi__TripSearchBox-content > input"));
        emailField.sendKeys(input);
    }

    @Step("Verify success search by input")
    private void verifySuccessSearchByInput(String result) {
        driver = app.getDriver();
        
        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    
        By successElementSelector = By.cssSelector("#__next > div.travel_guide_root_class.add_font-family > div > div.page-title > span");
        
        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
        
        // Assert the element is displayed
        Assert.assertTrue(successElement.getText().contains(result), "Success message or profile element not displayed!");
    }

    @Step("Verify success search by choice")
    private void verifySuccessSearchByChoice(String result) {
        driver = app.getDriver();
        // Wait for the page or element to appear
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector("#poi\\.detail\\.overview > div > div.TopBoxStyle-sc-2jpvue-4.cKCoNt > div > div.top-box > div > div.poi-page-title > h1");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));
    
        // Assert the element is displayed
        Assert.assertTrue(successElement.getText().contains(result), "Success message or profile element not displayed!");
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
