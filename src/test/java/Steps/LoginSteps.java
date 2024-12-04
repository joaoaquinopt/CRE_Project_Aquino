package Steps;

import BrowserConfig.*;
import Pages.LoginPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.Scenario;

import java.time.Duration;

@Epic("Login Page")
@Feature("Login on the Mage Plaza WebSite")
public class LoginSteps {
    private LoginPage loginPage;
    private WebDriver driver;
    private WebDriverWait wait;
    private final String firstName = "Vielka";
    private final String lastName = "Wood";

    public LoginSteps() {
    }

    @Before
    public void setUp(Scenario scenario) {
        // Check if driver exists, if not initialize it
        if (BrowserConfig.getDriver() == null) {
            BrowserType browserType = scenario.getName().toLowerCase().contains("firefox") ?
                    BrowserType.FIREFOX : BrowserType.CHROME;
            BrowserConfig.initializeDriver(browserType);
        }
        driver = BrowserConfig.getDriver();
        if (driver != null) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            loginPage = new LoginPage(driver);
        } else {
            throw new IllegalStateException("WebDriver not initialized properly");
        }
    }

    @When("I navigate to the Sign In page")
    @Step("Navigating to the Sign In page")
    public void iNavigateToTheSignInPage() {
        loginPage.getSignInButton();
    }

    @When("I fill in the {string} and {string}")
    @Step("Entering credentials - username: {0}, password: ****")
    public void iEnterCredentials(String email, String password) {
        loginPage.isLoginFormDisplayed();
        loginPage.clearLoginFields();
        loginPage.validateBasicCredentials(email, password);
        loginPage.fillEmailPassword( email, password );
    }

    @Then("I click on the Sign In button")
    @Step("Verify if open the Sign In form")
    public void iClickOnTheSignInButton() {
         loginPage.clickSignInButton();
    }

    @Then("I should be logged in successfully")
    @Step("Verifying successful login")
    public void iShouldBeLoggedInSuccessfully() {
        loginPage.userLoggedIn();
    }

    @Then("I click on the logOut button")
    @Step("Logout of the page")
    public void i_click_on_the_log_out_button() {
        loginPage.logOut();
    }

}
