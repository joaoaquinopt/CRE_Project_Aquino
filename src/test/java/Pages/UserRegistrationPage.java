/*
 * Autor: João Aquino
 * Data de Criação: 2024-12-04
 * Versão: 1.0.0
 * Descrição: Page Object para a página de registro de utilizador.
 */

package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class UserRegistrationPage {
    private static final String SUCCESS_MESSAGE = "Thank you for registering with Main Website Store.";
    private final WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "firstname")
    private WebElement firstNameField;

    @FindBy(id = "lastname")
    private WebElement lastNameField;

    @FindBy(id = "email_address")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "password-confirmation")
    private WebElement confirmPasswordField;

    @FindBy(linkText = "Create an Account")
    private WebElement clickCreateAccountLink;

    @FindBy(id = "send2")
    private WebElement createAccountButton;

    @FindBy(css = "div[data-bind='html: $parent.prepareMessageForHtml(message.text)']")
    private WebElement successMessage;


    public UserRegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait( driver, Duration.ofSeconds( 10 ) );
        PageFactory.initElements( driver, this );
    }

    public void fillRegistrationForm(String firstName, String lastName, String email, String password) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(password);
    }

    private void enterFirstName(String firstName) {
        firstNameField.sendKeys( firstName );
    }

    private void enterLastName(String lastName) {
        lastNameField.sendKeys( lastName );
    }

    private void enterEmail(String email) {
        emailField.sendKeys( email );
    }

    private void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    private void enterConfirmPassword(String password) {
        confirmPasswordField.sendKeys(password);
    }

    public void clickCreateAccountButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable( createAccountButton ));
            createAccountButton.click();
        } catch (ElementClickInterceptedException | TimeoutException e) {
            handleException(e, "Failed to click Create Account button: ");
        }
    }

    public void navigateToCreateAccount() {
        wait.until(ExpectedConditions.elementToBeClickable( clickCreateAccountLink ));
        clickCreateAccountLink.click();
        waitForPageLoad();
    }

    public boolean isRegistrationSuccessful() {
        try {
            Thread.sleep(5000);
            boolean isSuccess = wait.until( ExpectedConditions.visibilityOfElementLocated( By.cssSelector( "div[data-bind='html: $parent.prepareMessageForHtml(message.text)']" ) ) )
                    .isDisplayed();
            String actualText = successMessage.getText();
            assertEquals( actualText, SUCCESS_MESSAGE, "Success message does not match expected text" );
            return isSuccess;
        } catch (TimeoutException | InterruptedException e) {
            return false;
        }
    }

    public void waitForPageLoad() {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            handleException(e, "Page failed to load in time: ");
        }
    }

    private void handleException(Exception e, String message) {
        System.err.println(message + e.getMessage());
    }

}
