/*
 * Autor: João Aquino
 * Data de Criação: 2024-12-04
 * Versão: 1.0.0
 * Descrição: Classe de passos para o ‘login’.
 */

package Steps;

import BrowserConfig.*;
import Pages.LoginPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.*;
import io.cucumber.java.Scenario;


@Epic("Login Page")
@Feature("Login on the Mage Plaza WebSite")
public class LoginSteps {
    private LoginPage loginPage;
    private WebDriver driver;

    public LoginSteps() {
    }

    @Before
    public void setUp(Scenario scenario) {
        if (BrowserConfig.getDriver() == null) {
            BrowserType browserType = scenario.getName().toLowerCase().contains("firefox") ?
                    BrowserType.FIREFOX : BrowserType.CHROME;
            BrowserConfig.initializeDriver(browserType);
        }
        driver = BrowserConfig.getDriver();
        if (driver != null) {

            loginPage = new LoginPage(driver);
        } else {
            throw new IllegalStateException("WebDriver not initialized properly");
        }
    }

    @When("I navigate to the Sign In page")
    public void iNavigateToTheSignInPage() {
        loginPage.getSignInButton();
    }

    @When("I fill in the {string} and {string}")
    public void iEnterCredentials(String email, String password) {
        loginPage.isLoginFormDisplayed();
        loginPage.clearLoginFields();
        loginPage.validateBasicCredentials(email, password);
        loginPage.fillEmailPassword( email, password );
    }

    @Then("I click on the Sign In button")
    public void iClickOnTheSignInButton() {
         loginPage.clickSignInButton();
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        loginPage.userLoggedIn();
    }

    @Then("I click on the logOut button")
    public void i_click_on_the_log_out_button() {
        loginPage.logOut();
    }

}
