package testcases;

import common.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Random;

public class Registration extends BaseTest {
    WebDriver driver;
    Select selectDay, selectMonth, selectYear;
    String firstName, lastName, email, companyName, pass, confirmPass;



    @Test
    @Parameters({"browser", "url"})
    public void Tc_01_Register(String browser, String url) {
        //data
        firstName="Tony";
        lastName="Buoi Sang";
        email="tonybuoisang"+getRandomNumber()+"@gmail.com";
        companyName="Tony Buoi Sang company";
        pass="123456";
        confirmPass="123456";

        driver = getBrowserDriver(browser, url);

        log.info("click to register");
        driver.findElement(By.xpath("//a[text()='Register']")).click();

        log.info("click to gender male radio");
        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys(firstName);
        driver.findElement(By.id("LastName")).sendKeys(lastName);
//
//        selectDay = new Select(driver.findElement(By.name("DateOfBirthDay")));
//        selectDay.selectByVisibleText("10");
//
//        selectMonth = new Select(driver.findElement(By.name("DateOfBirthMonth")));
//        selectMonth.selectByVisibleText("February");
//
//        selectYear = new Select(driver.findElement(By.name("DateOfBirthYear")));
//        selectYear.selectByVisibleText("1990");
//
//        driver.findElement(By.id("Email")).sendKeys(email);
//        driver.findElement(By.id("Company")).sendKeys(companyName);
//
//        driver.findElement(By.id("Password")).sendKeys(pass);
//        driver.findElement(By.id("ConfirmPassword")).sendKeys(confirmPass);
//
//        driver.findElement(By.id("register-button")).click();
//
//        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='result']")).getText(), "Your registration completed");
//
//        driver.findElement(By.className("ico-logout")).click();


    }
    public int getRandomNumber()
    {
        Random random = new Random();
        return random.nextInt(9999);
    }
}
