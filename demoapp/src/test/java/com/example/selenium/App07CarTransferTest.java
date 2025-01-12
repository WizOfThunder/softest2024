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

public class App07CarTransferTest {
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
    @Feature("TC033 Success Search Car Transfer (Pick Up)")
    public void TC033_testSuccessSearchCarTransferPickUp() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        navigateToCarRentalPage();
        selectCarTransfer();
        clickPickUp();
        clickArrivalAirport();
        flightNumberFill("UA305");
        clickArrivalDate();
        selectArrivalDate();
        searchButtonClick();
        selectFlight();
        destinationFill("Houston");
        selectDestination();
        searchCarTransferButton();
        verifySuccessSearchCarTransferPickUp();
        takeScreenshot("TC033_testSuccessSearchCarTransferPickUp");
        resetCarTransferPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC035 Fail Search Car Transfer (Empty Field)")
    public void TC035_testFailSearchCarTransfer() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        searchCarTransferButton();
        verifyFailSearchCarTransfer();
        takeScreenshot("TC035_testFailSearchCarTransfer");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }
    
    @Step("Navigate to cars page")
    private void navigateToCarRentalPage() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement carsPage = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#header_action_nav_cars")));
        carsPage.click();
    }

    @Step("Select Car Transfer")
    private void selectCarTransfer() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement carTransfer = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector("#header_action_nav_Airport\\ Transfers")));
        carTransfer.click();
    }

    @Step("Click Pick Up")
    private void clickPickUp() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement carTransfer = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector("#pick")));
        carTransfer.click();
    }

    @Step("Input form with Flight Number:{flightNumber}")
    private void flightNumberFill(String flightNumber) {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "body > div.thanos-modal-mask.fadeIn > div > div > div.el-scrollbar__wrap > div > div.flight_main > div.searchbox_wrapper > div:nth-child(1) > div.flight_searchbox_flightInfo > div > div.thanos-input-pop.flightNo > div.thanos-input > div > input")));

        WebElement flightNumberField = app.getDriver().findElement(By.cssSelector(
                "body > div.thanos-modal-mask.fadeIn > div > div > div.el-scrollbar__wrap > div > div.flight_main > div.searchbox_wrapper > div:nth-child(1) > div.flight_searchbox_flightInfo > div > div.thanos-input-pop.flightNo > div.thanos-input > div > input"));
        flightNumberField.clear();
        flightNumberField.sendKeys(flightNumber);
    }
    
    @Step("Input form with destination:{destination}")
    private void destinationFill(String destination) {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div.airshbox-form-pick.airshbox-form.clearfix > div > div.airshbox-form__address_pick > div > div:nth-child(2) > div.relative > div.thanos-input-pop.thanos-address.airshbox-address__flight > div.thanos-input > div > input")));

        WebElement DestinationField = app.getDriver().findElement(By.cssSelector(
                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div.airshbox-form-pick.airshbox-form.clearfix > div > div.airshbox-form__address_pick > div > div:nth-child(2) > div.relative > div.thanos-input-pop.thanos-address.airshbox-address__flight > div.thanos-input > div > input"));
        DestinationField.clear();
        DestinationField.sendKeys(destination);
    }
    
    @Step("Select Destination")
    private void selectDestination() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpDate = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        " #__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div.airshbox-form-pick.airshbox-form.clearfix > div > div.airshbox-form__address_pick > div > div:nth-child(2) > div.relative > div.thanos-input-pop.thanos-address.airshbox-address__flight.is-active > div.select-panel-wrapper > div > div.thanos-select-content > div.el-scrollbar.thanos-scroller.thanos-select-scroller > div.el-scrollbar__wrap > div > div.address-fuzzy > ul > li:nth-child(3) > div.address-list__info.fl > h5 > span")));
        pickUpDate.click();
    }
    
    @Step("Click Arrival Airport")
    private void clickArrivalAirport() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement carTransfer = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector("#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div.airshbox-form-pick.airshbox-form.clearfix > div > div.airshbox-form__address_pick > div > div:nth-child(1) > div > div.thanos-input-pop.airshbox-address__flight > div.thanos-input > div > input")));
        carTransfer.click();
    }
    
    @Step("Click Arrival Date")
    private void clickArrivalDate() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement arrivalDate = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "body>div.thanos-modal-mask.fadeIn>div>div>div.el-scrollbar__wrap>div>div.flight_main>div.searchbox_wrapper>div:nth-child(1) > div.flightModal__search__item.flight_searchbox_time > div.thanos-input-pop.flightTime > div.thanos-input > div > input")));
        arrivalDate.click();
    }
    
    @Step("Select Arrival Date")
    private void selectArrivalDate() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpDate = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        " body > div.thanos-modal-mask.fadeIn > div > div > div.el-scrollbar__wrap > div > div.flight_main > div.searchbox_wrapper > div:nth-child(1) > div.flightModal__search__item.flight_searchbox_time > div.thanos-input-pop.flightTime.is-active > div.select-panel-wrapper > div > div.c-calendar__body > div:nth-child(2) > div.c-calendar-month__days > ul:nth-child(3) > li:nth-child(5) > div > span")));
        pickUpDate.click();
    }

    @Step("Click Search")
    private void searchButtonClick() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement search = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "body > div.thanos-modal-mask.fadeIn > div > div > div.el-scrollbar__wrap > div > div.flight_main > div.searchbox_wrapper > div:nth-child(1) > div.airshbox-form__button.box-solid")));
        search.click();
    }

    @Step("Select Flight")
    private void selectFlight() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement flight = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                        " body > div.thanos-modal-mask.fadeIn > div > div > div.el-scrollbar__wrap > div > div.flight_main > div.el-scrollbar.thanos-scroller > div.el-scrollbar__wrap > div > div.thanos-fs-fl-container > div > div.thanos-trip-btn__wrapper.fs-fl-btn.solid")));
        flight.click();
    }

    @Step("Click button search Car Transfer")
    private void searchCarTransferButton() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div.airshbox-form-pick.airshbox-form.clearfix > div > div.airshbox-form__detail > div.airshbox-form__button.box-solid")));
        searchButton.click();
    }

    @Step("Verify Fail Search Car Transfer ")
    private void verifyFailSearchCarTransfer() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div.airshbox-form-pick.airshbox-form.clearfix > div > div.airshbox-form__address_pick > div > div:nth-child(1) > div > div.tips-container.error.is-show");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Fail Search Car Transfer Pick Up")
    private void verifySuccessSearchCarTransferPickUp() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div.list-layer > div.page-content-box.main.fr-lt > div.thanos-theme.list-main-view > div.list-notice-bar.fr-sc.list-notice-bar-newversion > div.notice-content.bgf.fl1.fr-lc > span");

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

    @Step("Reset Car Transfer Page")
    private void resetCarTransferPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/airport-transfers/index?locale=en-ID&curr=IDR");
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
}
