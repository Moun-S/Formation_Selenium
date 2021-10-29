package test.java;

//import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestSuppSalarie {
    private WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void Setup() throws Exception {

        //Ajout du chemin du drive
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        //Initialisation du driver
        driver = new FirefoxDriver();
        //Supression des cookies
        //driver.manage().deleteAllCookies();
    }


    @Test(priority = 2)
    public void SuppSalarie() throws Exception {


        //Démarrage du navigateur
        driver.get("https://opensource-demo.orangehrmlive.com/");
        driver.manage().window().maximize();

        //Saisis des identifiants
        driver.findElement(By.id("txtUsername")).sendKeys("Admin");
        driver.findElement(By.id("txtPassword")).sendKeys(("admin123"));
        //Submit
        driver.findElement(By.name("Submit")).click();
        Thread.sleep(1000);

        //Clique du PIM/Add Employee
        driver.findElement(By.xpath("//b[contains(text(),'PIM')]")).click();
        driver.findElement(By.xpath("//a[@id='menu_pim_viewEmployeeList']")).click();

        //Saisir le nom de l'employer
        Thread.sleep(5000);

        //Attendre que le bouton Search soit visible i.e page chargée
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBtn")));

        //Attendre 5 secondes avant de cliquer
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Saisir le nom de l'employé
        driver.findElement(By.id("empsearch_employee_name_empName")).sendKeys("Eric Dupont");
        new Select(driver.findElement(By.id("empsearch_sub_unit"))).selectByValue("5");
        driver.findElement(By.id("searchBtn")).click();

        //Selection et suppression de l'employé
        driver.findElement(By.id("ohrmList_chkSelectAll")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.id("btnDelete")).click();
        driver.findElement(By.id("dialogDeleteBtn")).click();
        String expected = "No Records Found";
        String actual = driver.findElement(By.xpath("//td[contains(text(),'No Records Found')]")).getText();
        Assert.assertEquals(expected, actual);

        //File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //FileUtils.copyFile(scrFile, new File("/home/neo/Bureau/FORMATION_BITOO/10 - Selenium/screenshot.png-" + new Date()));

        Thread.sleep(2000);
    }

    @AfterClass(alwaysRun = true)
    public void TearDown() throws Exception {
        //driver.manage().deleteAllCookies();
        driver.close();
    }
}