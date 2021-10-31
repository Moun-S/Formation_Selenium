package test.java;

//import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Date;

public class TestAddSalarie {

    private WebDriver driver;


    @BeforeClass(alwaysRun = true)
    public void Setup() throws Exception {

        //Ajout du chemin du driver
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");

        //Initialisation du driver
        driver = new FirefoxDriver();
        //Supression des cookies
        driver.manage().deleteAllCookies();
    }


    @Test(priority = 1)
    public void AddSalarie() throws Exception {

        //Démarrage du navigateur
        driver.get("https://opensource-demo.orangehrmlive.com/");

        driver.manage().window().maximize();

        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        //Saisis des identifiants
        driver.findElement(By.id("txtUsername")).sendKeys("Admin");
        driver.findElement(By.id("txtPassword")).sendKeys(("admin123"));

        //Submit
        driver.findElement(By.name("Submit")).click();

        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Thread.sleep(1000);

        //Clique du PIM/Add Employee
        driver.findElement(By.xpath("//b[contains(text(),'PIM')]")).click();
        driver.findElement(By.xpath("//input[@id='btnAdd']")).click();

        //Ajout d'un employer

        driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys("Éric");
        driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("DUPONT");
        driver.findElement(By.xpath("//input[@id='chkLogin']")).click();

        //Renseigner les informations
        driver.findElement(By.name("user_name")).sendKeys("ericdupont");
        driver.findElement(By.name("user_password")).sendKeys("Azerty1234");
        driver.findElement(By.name("re_password")).sendKeys("Azerty1234");

        //Save
        driver.findElement(By.xpath("//input[@id='btnSave']")).click();

        Thread.sleep(2000);

        //Cliquer sur "Job"
        driver.findElement(By.partialLinkText("Job")).click();

        //Vérifier que le bouton Edit est présent
        if (driver.findElement(By.id("btnSave")).isDisplayed()) {
            driver.findElement(By.id("btnSave")).click();
        }

        //Sélectionner le Sub Unit Quality Assurance
        Select subunit = new Select(driver.findElement(By.id("job_sub_unit")));
        subunit.selectByValue("5");

        //Save
        driver.findElement(By.cssSelector("input[value='Save']")).click();

        Thread.sleep(1500);

        //Rechercher l'employer dans la liste

        driver.findElement(By.xpath("//b[contains(text(),'PIM')]")).click();
        new Select(driver.findElement(By.id("empsearch_sub_unit"))).selectByValue("5");
        driver.findElement(By.id("searchBtn")).click();

        File scrFile2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile2, new File("/home/neo/Bureau/FORMATION_BITOO/10 - Selenium/screenshot.png-" + new Date()));

        Thread.sleep(2000);
    }

    @AfterClass(alwaysRun = true)
    public void TearDown() throws Exception {
        driver.manage().deleteAllCookies();
        driver.close();
    }
}