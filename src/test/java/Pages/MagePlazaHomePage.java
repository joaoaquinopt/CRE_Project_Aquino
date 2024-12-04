package Pages;

import Steps.MagePlazaHomeSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MagePlazaHomePage {
    private static final Logger log = LoggerFactory.getLogger(MagePlazaHomePage.class);
    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final MagePlazaHomeSteps magePlazaHomeSteps;

    // Page URL
    private static final String PAGE_URL = "https://magento-demo.mageplaza.com/default/";

    public MagePlazaHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        this.magePlazaHomeSteps = new MagePlazaHomeSteps();
        PageFactory.initElements(driver, this);
    }

    @Step("Navigate to MagePlaza home page")
    public void navigateAndVerifyHomePage() {
        navigateToHomePage();
        verifyHomePageLoaded();
    }

    private void navigateToHomePage() {
        try {
            driver.get(PAGE_URL);
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
        } catch (Exception e) {
            log.error("Failed to navigate to homepage: {}", e.getMessage());
            throw new RuntimeException("Navigation failed", e);
        }
    }

    @Step("Verify Home Page Loaded")
    protected void verifyHomePageLoaded() {
        try {
            wait.until(driver -> {
                boolean ready = ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState").equals("complete");
                boolean hasElements = !driver.findElements(By.tagName("body")).isEmpty();
                return ready && hasElements;
            });
        } catch (Exception e) {
            log.error("Failed to verify page load: {}", e.getMessage());
            throw new RuntimeException("Homepage is not fully loaded", e); // Lança exceção
        }
    }

}
