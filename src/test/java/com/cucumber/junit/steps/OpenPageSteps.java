package com.cucumber.junit.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;
import static org.openqa.selenium.firefox.GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY;

public class OpenPageSteps {
    private final static String CHROME_DRIVER_PATH = "./src/main/resources/chromedriver.exe";
    private final static String FF_DRIVER_PATH = "./src/main/resources/geckodriver.exe";
    private final static int IMPLICIT_WAIT_TIMEOUT = 5;
    private final static int PAGE_LOAD_TIMEOUT = 20;
    private final static String ROZETKA_URL = "https://rozetka.com.ua/";
    private final static String ROZETKA_EXPECTED_LOGO = "Rozetka Logo";
    private final static String BOOKDEPOSITORY_URL = "https://www.bookdepository.com/";
    private final static String BOOKDEPOSITORY_EXPECTED_LOGO = "Bookdepository.com";
    private final static String ROZETKA_LOGO_XPATH = "//img[@alt='Rozetka Logo']";
    private final static String BOOKDEPOSITORY_LOGO_XPATH = "//div[@class='brand-wrap']/descendant::img[@alt='Bookdepository.com']";

    WebDriver driver;

    @Given("I open the (.*) home page")
    public void openHomePage(String page) {
        String browser = System.getProperty("browser");
        if (browser.equalsIgnoreCase("FF")) {
            System.setProperty(GECKO_DRIVER_EXE_PROPERTY, FF_DRIVER_PATH);
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty(CHROME_DRIVER_EXE_PROPERTY, CHROME_DRIVER_PATH);
            driver = new ChromeDriver();
        } else {
            throw new IllegalStateException(String.format("%s browser is not supported", browser));
        }
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, SECONDS);
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, SECONDS);

        if (page.equalsIgnoreCase("Rozetka")) {
            driver.get(ROZETKA_URL);
        } else if (page.equalsIgnoreCase("Bookdepository")) {
            driver.get(BOOKDEPOSITORY_URL);
        } else throw new IllegalStateException("Verification of this page is not supported");
    }

    @When("I verify the (.*) logo")
    public void checkSourcePageLogo(String resourceName) {
        if (resourceName.equalsIgnoreCase("Rozetka")) {
            verifyPageLogo(ROZETKA_LOGO_XPATH, ROZETKA_EXPECTED_LOGO);
        } else if (resourceName.equalsIgnoreCase("Bookdepository")) {
            verifyPageLogo(BOOKDEPOSITORY_LOGO_XPATH, BOOKDEPOSITORY_EXPECTED_LOGO);
        } else throw new IllegalStateException("Verification of this page is not supported");
    }

    private void verifyPageLogo(String xpath, String expectedLogoText) {
        WebElement pageLogo = driver.findElement(By.xpath(xpath));
        String logo = pageLogo.getAttribute("alt");
        assertEquals(expectedLogoText, logo, "Wrong page logo");
    }

    @Then("I close the browser")
    public void closeTheBrowser() {
        driver.quit();
    }
}
