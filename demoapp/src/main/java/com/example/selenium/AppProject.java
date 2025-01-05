package com.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppProject {
    private WebDriver driver;
    public AppProject(){
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void openURL(String url){
        driver.get(url);;
    }

    public WebDriver getDriver(){
        return driver;
    }

    public void closBrowser(){
        if (driver!=null) {
            driver.quit();
        }
    }
}
