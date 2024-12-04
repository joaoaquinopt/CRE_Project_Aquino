package Steps;

import BrowserConfig.BrowserConfig;
import BrowserConfig.BrowserType;
import Pages.UserRegistrationPage;
import Utils.TestDataGenerator;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static BrowserConfig.BrowserConfig.getDriver;

@Epic("User Management")
@Feature("User Registration")
public class UserRegistrationSteps {
    private static final Logger log = LoggerFactory.getLogger(UserRegistrationSteps.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private UserRegistrationPage userRegistrationPage;

    public UserRegistrationSteps() {
    }

    @Before
    public void setUp(Scenario scenario) {
        // Check if driver exists, if not initialize it
        if (getDriver() == null) {
            BrowserType browserType = scenario.getName().toLowerCase().contains("firefox") ?
                    BrowserType.FIREFOX : BrowserType.CHROME;
            BrowserConfig.initializeDriver(browserType);
        }
        driver = getDriver();
        if (driver != null) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            userRegistrationPage = new UserRegistrationPage(driver); // Inicializa userRegistrationPage aqui
        } else {
            throw new IllegalStateException("WebDriver not initialized properly");
        }
    }

    @When("I navigate to the Create an account page")
    @Step("Navigating to Create Account page")
    @Description("Navigate to the account creation page")
    @Severity(SeverityLevel.NORMAL)
    public void navigateToCreateAccountPage() {
        userRegistrationPage.navigateToCreateAccount();
    }

    @When("I fill in account registration with valid values")
    @Step("Fill registration form with valid values")
    @Description("Fill in all required registration fields with valid data")
    @Severity(SeverityLevel.CRITICAL)
    public void i_fill_in_account_registration_with_valid_values() {

        String firstName = TestDataGenerator.generateName( 5 );
        String lastName =  TestDataGenerator.generateName( 5 );
        String email = TestDataGenerator.generateRandomEmail();
        String password = TestDataGenerator.generateRandomPassword( 10 );

        userRegistrationPage.fillRegistrationForm(firstName, lastName, email, password);
    }

    @When("I click the Create an Account button")
    @Step("Click Create Account button")
    @Description("Submit the registration form")
    @Severity(SeverityLevel.CRITICAL)
    public void clickCreateAccountButton() {
            userRegistrationPage.clickCreateAccountButton();
    }

    @Then("I should see a success message")
    @Step("Verify success message")
    @Description("Verify that the registration was successful")
    @Severity(SeverityLevel.CRITICAL)
    public void verifySuccessMessage() {
            userRegistrationPage.isRegistrationSuccessful();
    }


}
