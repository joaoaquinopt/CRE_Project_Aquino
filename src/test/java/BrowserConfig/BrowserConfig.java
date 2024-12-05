package BrowserConfig;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BrowserConfig {
    private static final Logger logger = LoggerFactory.getLogger(BrowserConfig.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public BrowserConfig() {
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void initializeDriver(BrowserType browserType) {
        try {
            WebDriver webDriver = createDriver(browserType);
            driver.set(webDriver);
            startBrowser(webDriver);
            logger.info("Browser initialized: {}", browserType);
        } catch (Exception e) {
            logger.error("Failed to initialize browser: {}", e.getMessage());
            throw new RuntimeException("Browser initialization failed", e);
        }
    }

    private static WebDriver createDriver(BrowserType browserType) {
        return switch (browserType) {
            case CHROME -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new");
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(options);
            }
            case FIREFOX -> {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--headless");
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver(options);
            }
        };
    }

    private static void startBrowser(WebDriver webDriver) {
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    public static void quitDriver() {
        WebDriver currentDriver = getDriver();
        if (currentDriver != null) {
            try {
                currentDriver.quit();
            } finally {
                driver.remove();
            }
        }
    }

    public static String getBrowserName() {
        WebDriver driver = getDriver();
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        return caps.getBrowserName();
    }
}

