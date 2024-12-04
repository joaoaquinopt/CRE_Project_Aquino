/*
 * Autor: João Aquino
 * Data de Criação: 2024-12-04
 * Versão: 1.0.0
 * Descrição: Classe de passos para o registro de utilizador.
 */
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

import static BrowserConfig.BrowserConfig.getDriver;

@Epic("User Management")
@Feature("User Registration")
public class UserRegistrationSteps {
    private WebDriver driver;
    private UserRegistrationPage userRegistrationPage;

    public UserRegistrationSteps() {
    }

    @Before
    public void setUp(Scenario scenario) {
        if (getDriver() == null) {
            BrowserType browserType = scenario.getName().toLowerCase().contains("firefox") ?
                    BrowserType.FIREFOX : BrowserType.CHROME;
            BrowserConfig.initializeDriver(browserType);
        }
        driver = getDriver();
        if (driver != null) {
            userRegistrationPage = new UserRegistrationPage(driver);
        } else {
            throw new IllegalStateException("WebDriver not initialized properly");
        }
    }

    @When("I navigate to the Create an account page")
    public void navigateToCreateAccountPage() {
        userRegistrationPage.navigateToCreateAccount();
    }

    @When("I fill in account registration with valid values")
    public void i_fill_in_account_registration_with_valid_values() {

        String firstName = TestDataGenerator.generateName( 5 );
        String lastName =  TestDataGenerator.generateName( 5 );
        String email = TestDataGenerator.generateRandomEmail();
        String password = TestDataGenerator.generateRandomPassword( 10 );

        userRegistrationPage.fillRegistrationForm(firstName, lastName, email, password);
    }

    @When("I click the Create an Account button")
    public void clickCreateAccountButton() {
            userRegistrationPage.clickCreateAccountButton();
    }

    @Then("I should see a success message")
    public void verifySuccessMessage() {
            userRegistrationPage.isRegistrationSuccessful();
    }


}
