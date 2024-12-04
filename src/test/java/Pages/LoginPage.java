package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static BrowserConfig.BrowserConfig.getBrowserName;
import static org.testng.Assert.assertEquals;

import java.net.URI;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Duration TIMEOUT = Duration.ofSeconds( 10 );

    private static final int MAX_EMAIL_LENGTH = 50;
    private static final int MAX_PASSWORD_LENGTH = 100;

    @FindBy(id = "email")  // Update this locator according to your application
    private WebElement emailField;

    @FindBy(id = "pass")   // Update this locator according to your application
    private WebElement passwordField;

    @FindBy(linkText = "Sign In")  // Update this locator according to your application
    private WebElement clickSignIn;

    @FindBy(id = "send2")
    private WebElement signInButton;

    @FindBy(css = ".customer-name button")
    private WebElement changeButton;

    @FindBy(linkText = "My Account")
    private WebElement myAccount;

    @FindBy(css = "div[class='box box-information'] p")
    private WebElement contactInfoBox;

    @FindBy(xpath = "//div[@aria-hidden='false']//a[normalize-space()='Sign Out']")
    private WebElement signOutButton;


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait( driver, TIMEOUT );
        PageFactory.initElements( driver, this );
    }

    public void fillEmailPassword(String email, String password) {
        wait.until( ExpectedConditions.visibilityOf( emailField ) );
        emailField.sendKeys( email );
        wait.until( ExpectedConditions.visibilityOf( passwordField ) );
        passwordField.sendKeys( password );
    }

    public void clickSignInButton() {
        wait.until( ExpectedConditions.elementToBeClickable( signInButton ) );
        signInButton.click();
    }

    public void userLoggedIn() {
        try {
            Thread.sleep(5000);
            wait.until( ExpectedConditions.elementToBeClickable( changeButton ) );
            changeButton.click();
            wait.until( ExpectedConditions.elementToBeClickable( myAccount ) );
            myAccount.click();

            String expectedUrl = "https://magento-demo.mageplaza.com/default/customer/account/";
            wait.until( ExpectedConditions.urlToBe( expectedUrl ) );

            String currentUrl = driver.getCurrentUrl();
            Assert.assertEquals( currentUrl, expectedUrl, "URL incorreta!" );

            wait.until( ExpectedConditions.visibilityOf( contactInfoBox ) );
            String nome = contactInfoBox.getText().split( "\n" )[0];

            String browser = getBrowserName();
            switch (browser) {
                case
                        "chrome" -> Assert.assertEquals(nome, "Vielka Wood", "Nome incorreto!");
                case
                        "firefox" -> Assert.assertEquals(nome, "James Obrien", "Nome incorreto!");
                default
                        -> throw new IllegalArgumentException( "Browser não suportado: " + browser );
            };
        } catch (Exception e) {
            throw new RuntimeException( "Error validating login", e );
        }
    }

    public void clearLoginFields() {
        emailField.clear();
        passwordField.clear();
    }

    public boolean isLoginFormDisplayed() {
        return wait.until( ExpectedConditions.visibilityOf( emailField ) ).isDisplayed() &&
                wait.until( ExpectedConditions.visibilityOf( passwordField ) ).isDisplayed() &&
                wait.until( ExpectedConditions.visibilityOf( signInButton ) ).isDisplayed();

    }

    public void getSignInButton() {
        wait.until( ExpectedConditions.elementToBeClickable( clickSignIn ) );
        clickSignIn.click();
    }

    public void validateBasicCredentials(String email, String password) {
        if (email == null || email.trim().isEmpty() || email.length() > MAX_EMAIL_LENGTH) {
            throw new IllegalArgumentException( "Invalid email format" );
        }

        if (password == null || password.trim().isEmpty() || password.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException( "Invalid password format" );
        }
    }

    public void logOut() {
        try {
            Thread.sleep(3000);
            wait.until( ExpectedConditions.elementToBeClickable( changeButton ) );
            changeButton.click();
            wait.until( ExpectedConditions.elementToBeClickable( signOutButton ) );
            signOutButton.click();
        } catch (Exception e) {
            throw new RuntimeException( "Erro ao fazer logout", e );
        }
    }

}
