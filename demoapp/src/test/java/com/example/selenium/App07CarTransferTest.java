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
import org.openqa.selenium.interactions.Actions;
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
    @Feature("TC034 Success Search Car Transfer (Pick Up)")
    public void TC034_testSuccessSearchCarTransferPickUp() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        navigateToCarTransferPage();
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
        takeScreenshot("TC034_testSuccessSearchCarTransferPickUp");
        resetCarTransferPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC035 Success Search Car Transfer (Drop Off)")
    public void TC035_testSuccessSearchCarTransferDropOff() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        clickDropOff();
        inputPickUpPoint("Houston");
        selectInputPoint();
        selectAirPort();
        selectDate();
        selectTime();
        searchButtonClick2();
        verifySuccessSearchCarTransferDropOff();
        takeScreenshot("TC035_testSuccessSearchCarTransferDropOff");
        resetCarTransferPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC036 Fail Search Car Transfer (Empty Field)")
    public void TC036_testFailSearchCarTransfer() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);

        searchCarTransferButton();
        verifyFailSearchCarTransfer();
        takeScreenshot("TC036_testFailSearchCarTransfer");

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC037 Failed book Transfer Car (EmptyField)")
    public void TC037_FailedBookTransferCarEmptyField() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        clickDropOff();
        inputPickUpPoint("Houston");
        selectInputPoint();
        selectAirPort();
        selectDate();
        selectTime();
        searchButtonClick2();
        selectCars();
        clickContinue();
        verifyFailedBookCarEmptyField();
        takeScreenshot("TC037_FailedBookTransferCarEmptyField");
        resetCarTransferPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }
    
    @Test
    @Feature("TC038 Failed book Transfer Car (Invalid Email)")
    public void TC038_FailedBookTransferCarInvalidEmail() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        clickDropOff();
        inputPickUpPoint("Houston");
        selectInputPoint();
        selectAirPort();
        selectDate();
        selectTime();
        searchButtonClick2();
        selectCars();
        inputBookingField("akun", "istts", "akun", "85246880675");
        clickContinue();
        verifyFailedBookCarInvalidEmail();
        takeScreenshot("TC038_FailedBookTransferCarInvalidEmail");
        resetCarTransferPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC039 Failed book Transfer Car (Invalid Phone Number)")
    public void TC039_FailedBookTransferCarInvalidPhone() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        clickDropOff();
        inputPickUpPoint("Houston");
        selectInputPoint();
        selectAirPort();
        selectDate();
        selectTime();
        searchButtonClick2();
        selectCars();
        inputBookingField("akun", "istts", "akunistts@gmail.com", "0");
        clickContinue();
        verifyFailedBookCarInvalidPhone();
        takeScreenshot("TC039_FailedBookTransferCarInvalidPhone");
        resetCarTransferPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }

    @Test
    @Feature("TC040 Success book Transfer Car")
    public void TC040_SuccessBookTransferCar() {
        String startTime = getCurrentTimestamp();
        attachTimestamp("Test Start Time", startTime);
        resetCarTransferPage();
        clickDropOff();
        inputPickUpPoint("Houston");
        selectInputPoint();
        selectAirPort();
        selectDate();
        selectTime();
        searchButtonClick2();
        selectCars();
        inputBookingField("akun", "istts", "akunistts@gmail.com", "85246888888");
        clickContinue();
        verifySuccessBookCar();
        takeScreenshot("TC040_SuccessBookTransferCar");
        resetCarTransferPage();

        String endTime = getCurrentTimestamp();
        attachTimestamp("Test End Time", endTime);
    }
    
    @Step("Navigate to cars page")
    private void navigateToCarTransferPage() {
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

    @Step("Click Drop Off")
    private void clickDropOff() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement carTransfer = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector("#drop")));
        carTransfer.click();
    }

    @Step("Input Pick Up Point")
    private void inputPickUpPoint(String place) {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpPoint = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div:nth-child(3) > div:nth-child(1) > div.airshbox-form__address_drop > div > div:nth-child(1) > div > div.thanos-input-pop.thanos-address.airshbox-address__airport > div.thanos-input > div > input")));
        pickUpPoint.click();
        pickUpPoint.clear();
        pickUpPoint.sendKeys(place);
    }
    
    @Step("Select Pick Up Point")
    private void selectInputPoint() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement pickUpPoint = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div:nth-child(3) > div:nth-child(1) > div.airshbox-form__address_drop > div > div:nth-child(1) > div > div.thanos-input-pop.thanos-address.airshbox-address__airport.is-active > div.select-panel-wrapper > div > div.thanos-select-content > div.el-scrollbar.thanos-scroller.thanos-select-scroller > div.el-scrollbar__wrap > div > div.address-fuzzy > ul > li:nth-child(3)")));
        pickUpPoint.click();
    }
    
    @Step("Select Airport")
    private void selectAirPort() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement AirPort = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div:nth-child(3) > div:nth-child(1) > div.airshbox-form__address_drop > div > div:nth-child(2) > div.relative > div.thanos-input-pop.airshbox-address__airport > div.thanos-input > div > input")));
        AirPort.click();

        WebElement Place = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div:nth-child(3) > div:nth-child(1) > div.airshbox-form__address_drop > div > div:nth-child(2) > div.relative > div.thanos-input-pop.airshbox-address__airport.is-active > div.select-panel-wrapper > div > div.thanos-select-content > div.el-scrollbar.thanos-scroller.thanos-select-scroller > div.el-scrollbar__wrap > div > div.thanos-airport-home > section:nth-child(1) > div > div:nth-child(1)")));
        Place.click();
    }

    @Step("Select Date")
    private void selectDate() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement clickDate = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div:nth-child(3) > div:nth-child(1) > div.airshbox-form__detail > div.airshbox-form__time > div > div.airshbox-calendar > div.thanos-input-pop.airshbox-calendar__input > div.thanos-input > div > input")));
        clickDate.click();

        WebElement selectDate = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div:nth-child(3) > div:nth-child(1) > div.airshbox-form__detail > div.airshbox-form__time > div > div.airshbox-calendar > div.thanos-input-pop.airshbox-calendar__input.is-active > div.select-panel-wrapper > div > div.c-calendar__body > div:nth-child(2) > div.c-calendar-month__days > ul:nth-child(3) > li:nth-child(5) > div > span")));
        selectDate.click();
    }

    @Step("Select Time")
    private void selectTime() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement clickTime = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div:nth-child(3) > div:nth-child(1) > div.airshbox-form__detail > div.airshbox-form__time > div > div.airshbox-timepicker > div:nth-child(1) > div > div.thanos-input > div > input")));
        clickTime.click();

        WebElement selectHour = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div:nth-child(3) > div:nth-child(1) > div.airshbox-form__detail > div.airshbox-form__time > div > div.airshbox-timepicker > div:nth-child(1) > div > div.select-panel-wrapper > div > div > div > div.el-scrollbar__wrap > div > div > div.el-scrollbar.thanos-scroller.scroll-container.hour.hide-bar > div.el-scrollbar__wrap > div > ul > li:nth-child(5)")));
        selectHour.click();

        WebElement selectMinutes = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div:nth-child(3) > div:nth-child(1) > div.airshbox-form__detail > div.airshbox-form__time > div > div.airshbox-timepicker > div:nth-child(1) > div > div.select-panel-wrapper > div > div > div > div.el-scrollbar__wrap > div > div > div:nth-child(2) > div.el-scrollbar__wrap > div > ul > li:nth-child(4)")));
        selectMinutes.click();
    }

    @Step("Select Cars")
    private void selectCars() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement selectCar = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div.list-layer > div.page-content-box.main.fr-lt > div.thanos-theme.list-main-view > div:nth-child(2) > div.group-content.fr-lc.bgf.is-fold.group-content-newversion")));
        selectCar.click();

        WebElement bookCar = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div.list-layer > div.page-content-box.main.fr-lt > div.thanos-theme.list-main-view > div:nth-child(2) > div.prds-wrapper > div > div.vnd-book.fr-rc > div.book-btn.fr-cc")));
        bookCar.click();
    }

    @Step("Click Continue")
    private void clickContinue() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement continues = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div.booking-page.booking-page-newversion > div > div.section-left > div.price-summary-wrap.price-summary-wrap-newversion.price-summary-wrap-left-newversion > div.book-btn")));
        Actions actions = new Actions(app.getDriver());
        actions.moveToElement(continues).perform();
        continues.click();
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

    @Step("Click Search")
    private void searchButtonClick2() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement search = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector(
                                "#__next > div:nth-child(2) > div.index-layer > div.index-banner-wrapper > div.index-searchBox > div > div:nth-child(3) > div:nth-child(1) > div.airshbox-form__detail > div.airshbox-form__button.box-solid")));
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

    @Step("input Booking Field")
    private void inputBookingField(String firstName, String lastName, String email, String phone) {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), java.time.Duration.ofSeconds(10));
        WebElement firstNameField = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector("#__next > div.booking-page.booking-page-newversion > div > div.section-left > div.contact-info > div.form-wrapper > div:nth-child(1) > div > input[type=text]")));
        Actions actions = new Actions(app.getDriver());
        actions.moveToElement(firstNameField).perform();
        firstNameField.clear();
        firstNameField.sendKeys(firstName);

        WebElement lastNameField = wait
            .until(ExpectedConditions
                .elementToBeClickable(By.cssSelector("#__next > div.booking-page.booking-page-newversion > div > div.section-left > div.contact-info > div.form-wrapper > div:nth-child(2) > div > input[type=text]")));
        lastNameField.clear();
        lastNameField.sendKeys(lastName);            

        WebElement emailField = wait
            .until(ExpectedConditions
                .elementToBeClickable(By.cssSelector("#__next > div.booking-page.booking-page-newversion > div > div.section-left > div.contact-info > div.form-wrapper > div.form-email-wrapper > div > div > input[type=text]")));
        emailField.clear();
        emailField.sendKeys(email);
                        
        WebElement phoneField = wait
            .until(ExpectedConditions
                .elementToBeClickable(By.cssSelector("#__next > div.booking-page.booking-page-newversion > div > div.section-left > div.contact-info > div.form-wrapper > div.form-phone-wrapper > div.tripui-online-input.form-phone > div > input[type=text]")));
        phoneField.clear();
        phoneField.sendKeys(phone);
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

    @Step("Verify Success Search Car Transfer Pick Up")
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

    @Step("Verify Success Search Car Transfer Drop Off")
    private void verifySuccessSearchCarTransferDropOff() {
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

    @Step("Verify Fail Book Car Transfer Empty Field")
    private void verifyFailedBookCarEmptyField() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div.booking-page.booking-page-newversion > div > div.section-left > div.contact-info > div.form-wrapper > div:nth-child(1) > div.tripui-online-input-help-text.error > div > span");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Fail Book Car Transfer Invalid Email")
    private void verifyFailedBookCarInvalidEmail() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div.booking-page.booking-page-newversion > div > div.section-left > div.contact-info > div.form-wrapper > div.form-email-wrapper > div > div.tripui-online-input-help-text.error > div > span");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Fail Book Car Transfer Invalid Phone")
    private void verifyFailedBookCarInvalidPhone() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div.booking-page.booking-page-newversion > div > div.section-left > div.contact-info > div.form-wrapper > div.form-phone-wrapper > div.tripui-online-input.form-phone > div.tripui-online-input-help-text.error > div > span");

        // Wait for the element to be visible
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successElementSelector));

        // Assert the element is displayed
        Assert.assertTrue(successElement.isDisplayed(), "Success element not displayed!");
    }

    @Step("Verify Fail Book Car Transfer Invalid Phone")
    private void verifySuccessBookCar() {
        driver = app.getDriver();

        // Wait for the new tab or page to load
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // Switch to the newly opened tab if applicable
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        By successElementSelector = By.cssSelector(
                "#__next > div > section > div.home-index-pc-box > main > div > div.trip-pc-col.gutter-row.trip-pc-col-xs-24.trip-pc-col-xs-order-2.trip-pc-col-md-16.trip-pc-col-md-order-1 > div > div.payment-column-wrapper > div.payment-column-wrapper-title > span.payment-column-wrapper-title-box");

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

    @Step("Reset Car Transfer Page")
    private void resetCarTransferBookPage() {
        driver.manage().deleteAllCookies();

        // Execute JavaScript to clear localStorage and sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");

        driver.navigate().to("https://id.trip.com/airport-transfers/list?lang=en-ID&language=en-ID&locale=en-ID&curr=IDR&pttype=18&ptgroup=18&udt=&dudt=2025-02-14%2004%3A30&adult=2&children=0&cid=26619&cnm=Houston&ctid=66&ctnm=United%20States&poiadr=&poilat=29.760193&poilng=-95.369392&poicoordtype=WGS84&poinm=Downtown%20Houston&poiref=5ECA3E3EB808E0CB3A00296A27989F7702BD56ABBDD303B2DAE996BB63E5DD63E449CB795045E4FFB2B310A5091824920B00110A26CD760C&stnadt=&stncd=IAH&stnddt=&stnlat=29.99022&stnlng=-95.336783&stnnm=George%20Bush%20Intercontinental%20Airport&stnno=&stnsubnm=&stnsubcd=&stnnorefs=&stnid=&poicityid=26619&carPlaceId=121196729&flightOrderId=&flightNo=&flightTime=&fromAirportCd=&fromAirportNm=&fromAirportSubnm=&fromAirportCid=0&toAirportCd=&toAirportNm=&toAirportSubnm=&toAirportCid=0");
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
