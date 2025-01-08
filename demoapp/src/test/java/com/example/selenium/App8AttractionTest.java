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
public class App8AttractionTest
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
    @Feature("TC040 Search Attractions & Tours By City")
    public void TC040_testSuccessSearchAttractionByCity(){
        navigateToAttractionPage();
        clickCityTextField();
        selectCity();
        searchAttractionButton();
        verifySuccessSearchAttractionByCity();
        takeScreenshot("TC040_testSuccessSearchAttractionByCity");
        resetAttractionPage();
    }

    @Test
    @Feature("TC041 Success Search Attractions & Tours By Attraction Name")
    public void TC041_testSuccessSearchAttractionByAttractionName(){
        clickCityTextField();
        selectCity();
        clickAttractionTextField();
        selectAttraction();
        verifySuccessSearchAttractionByAttractionName();
        takeScreenshot("TC041_testSuccessSearchAttractionByAttractionName");
        //resetAttractionPage();
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
    private void selectCity() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement city = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibuact-10650012750-destarea-dest-undefined-4-38")));
        city.click();
    }

    @Step("Click Attraction text field")
    private void clickAttractionTextField(){
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement AttractionTextField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ibuact-undefined-search-click-38-home-en-ID")));
        AttractionTextField.click();
    }

    @Step("Click Attraction From choice")
    private void selectAttraction() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    
        // Ensure the dropdown options are clickable
        WebElement Attraction = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ticket_header_pic > div.ticket_header_search > div.trip-search > div > div > div.ol_citypage_layer > div > div > div > article > div > div > div > div > div:nth-child(1) > div > div.popover_card_content > div:nth-child(1) > div.popover_card_content_row_carditem_box.flexone > div > div > span")));
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

    @Step("Reset Attraction Page")
    private void resetAttractionPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/things-to-do/ttd-home/?locale=en-ID&curr=IDR");
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
