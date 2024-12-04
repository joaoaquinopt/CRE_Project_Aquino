/*
 * Autor: João Aquino
 * Data de Criação: 2024-12-04
 * Versão: 1.0.0
 * Descrição: Page Object para a página inicial da MagePlaza.
 */

package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MagePlazaHomePage {
    private static final Logger log = LoggerFactory.getLogger(MagePlazaHomePage.class);
    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String PAGE_URL = "https://magento-demo.mageplaza.com/default/";

    public MagePlazaHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        PageFactory.initElements(driver, this);
    }

    public void navigateAndVerifyHomePage() {
        navigateToHomePage();
        verifyHomePageLoaded();
    }

    private void navigateToHomePage() {
        try {
            driver.get(PAGE_URL);
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
        } catch (Exception e) {
            handleException(e, "Failed to navigate to homepage: ");
        }
    }

    protected void verifyHomePageLoaded() {
        try {
            wait.until(driver -> {
                boolean ready = ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState").equals("complete");
                boolean hasElements = !driver.findElements(By.tagName("body")).isEmpty();
                return ready && hasElements;
            });
        } catch (Exception e) {
            handleException(e, "Failed to verify page load: ");
        }
    }

    private void handleException(Exception e, String message) {
        System.err.println(message + e.getMessage());
        log.error(message, e);
    }

}
