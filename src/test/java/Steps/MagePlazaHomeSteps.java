package Steps;

import BrowserConfig.*;
import Pages.MagePlazaHomePage;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.stream.Collectors;

@Epic("MagePlaza")
@Feature("Home Page Navigation")
public class MagePlazaHomeSteps {
    private MagePlazaHomePage magePlazaHomePage;
    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void setUp(Scenario scenario) {
        if (BrowserConfig.getDriver() == null) {
            BrowserType browserType = scenario.getName().toLowerCase().contains("firefox") ?
                    BrowserType.FIREFOX : BrowserType.CHROME;
            BrowserConfig.initializeDriver(browserType);
        }
        driver = BrowserConfig.getDriver();
        if (driver != null) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            magePlazaHomePage = new MagePlazaHomePage(driver);
        } else {
            throw new IllegalStateException("WebDriver not initialized properly");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                final byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot on failure");
                // Add page source
                String pageSource = driver.getPageSource();
                scenario.attach(pageSource.getBytes(), "text/html", "Page source on failure");
                // Add browser logs if available
                if (driver instanceof ChromeDriver) {
                    String logs = String.join("\n",
                            driver.manage().logs().get( LogType.BROWSER).getAll()
                                    .stream()
                                    .map( LogEntry::toString)
                                    .collect( Collectors.toList()));
                    scenario.attach(logs.getBytes(), "text/plain", "Browser logs");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BrowserConfig.quitDriver();
    }

    @Given("I am on the homepage")
    @Step("I am on the homepage of MagePlaza WebPage")
    @Description("Verify that I am on the homepage of MagePlaza WebPage successful")
    @Severity(SeverityLevel.CRITICAL)
    public void iAmOnHomePage() {
        magePlazaHomePage.navigateAndVerifyHomePage();
    }
}
