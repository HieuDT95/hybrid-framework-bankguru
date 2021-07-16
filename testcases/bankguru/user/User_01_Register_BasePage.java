package bankguru.user;
import org.testng.annotations.Test;

import commons.BasePage;

import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class User_01_Register_BasePage {
    WebDriver driver;
    BasePage basePage;
    String username , password , loginPageUrl , CustomerID;

    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/BrowserDrivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait( 15, TimeUnit.SECONDS);
        driver.get("http://demo.guru99.com/v4/index.php");
        //basePage = new BasePage();
        basePage = BasePage.getBasePage();

    }


    @Test
    public void Login_01_Register_To_System() {
        loginPageUrl = basePage.getPageURL(driver);
        basePage.clickToElement(driver, "//a[text() = 'here']");
        basePage.sendKeyToElement(driver, "//input[@name = 'emailid']" , "hieudt695@gmail.com");
        basePage.clickToElement(driver, "//input[@name = 'btnLogin']");

        username = basePage.getElementText(driver, "//td[text() = 'User ID :']/following-sibling::td");
        password = basePage.getElementText(driver, "//td[text() = 'Password :']/following-sibling::td");

    }

    @Test
    public void Login_02_Login_To_System() {
        basePage.openPageURL(driver, loginPageUrl);

        basePage.sendKeyToElement(driver, "//input[@name = 'uid']", username);
        basePage.sendKeyToElement(driver, "//input[@name = 'password']", username);
        basePage.clickToElement(driver, "//input[@name = 'btnLogin']");

    }

    @Test
    public void Login_03_Create_New_Cusomter() {
        String Name , Address ,DOB, City , State , Pin , Mobile , Mail , Pass;


        Name = "HieuDT";
        Address = "HHH";
        DOB = "1995-06-10";
        City = "HN";
        State = "VN";
        Pin = "1000000";
        Mobile = "0962509547";
        Mail = "register" + basePage.getRandomNumber() + "@gmail.com" ;
        Pass = "123456";



        basePage.clickToElement(driver, "//a[text() = 'New Customer']");

        //Send value
        basePage.sendKeyToElement(driver, "//input[@name = 'name']", Name);
        basePage.sendKeyToElement(driver, "//textarea[@name = 'addr']", Address);
        basePage.sendKeyToElement(driver, "//input[@name = 'dob']", DOB);

        basePage.sendKeyToElement(driver, "//input[@name = 'city']", City);
        basePage.sendKeyToElement(driver, "//input[@name = 'state']", State);
        basePage.sendKeyToElement(driver, "//input[@name = 'pinno']", Pin);
        basePage.sendKeyToElement(driver, "//input[@name = 'telephoneno']", Mobile);
        basePage.sendKeyToElement(driver, "//input[@name = 'emailid']", Mail);
        basePage.sendKeyToElement(driver, "//input[@name = 'password']", Pass);

        basePage.clickToElement(driver, "//input[@name = 'sub']");

        Assert.assertEquals(basePage.getElementText(driver, "//p[@class = 'heading3']"), "Customer Registered Successfully!!!");

        //Verify value
        Assert.assertEquals(basePage.getElementText(driver, "//td[text() = 'Customer Name']/following-sibling::td"), Name);
        Assert.assertEquals(basePage.getElementText(driver, "//td[text() = 'Birthdate']/following-sibling::td"), DOB);
        Assert.assertEquals(basePage.getElementText(driver, "//td[text() = 'Address']/following-sibling::td"), Address);
        Assert.assertEquals(basePage.getElementText(driver, "//td[text() = 'City']/following-sibling::td"), City);
        Assert.assertEquals(basePage.getElementText(driver, "//td[text() = 'State']/following-sibling::td"), State);
        Assert.assertEquals(basePage.getElementText(driver, "//td[text() = 'Pin']/following-sibling::td"), Pin);
        Assert.assertEquals(basePage.getElementText(driver, "//td[text() = 'Mobile No.']/following-sibling::td"), Mobile);
        Assert.assertEquals(basePage.getElementText(driver, "//td[text() = 'Email']/following-sibling::td"), Mail);


        CustomerID = basePage.getElementText(driver, "//td[text() = 'Customer ID']/following-sibling::td");





    }

    @AfterClass
    public void afterClass() {
    }

}

