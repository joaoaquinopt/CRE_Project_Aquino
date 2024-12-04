package Steps;

import BrowserConfig.BrowserConfig;
import BrowserConfig.BrowserType;
import Pages.AddProductToCartAndPurchasePage;
import Pages.LoginPage;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class AddProductToCartAndPurchaseSteps {
    private static final Logger log = LoggerFactory.getLogger( AddProductToCartAndPurchaseSteps.class );
    private LoginPage loginPage;
    private AddProductToCartAndPurchasePage addProductToCartAndPurchasePage;
    private WebDriver driver;
    private WebDriverWait wait;

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
            addProductToCartAndPurchasePage = new AddProductToCartAndPurchasePage(driver);
        } else {
            throw new IllegalStateException("WebDriver not initialized properly");
        }
    }

    @When("I am authenticated with {string} and {string}")
    public void i_am_authenticated_with_and(String email, String password) {
        loginPage.getSignInButton();
        loginPage.fillEmailPassword( email, password );
        loginPage.clickSignInButton();
        loginPage.validateBasicCredentials(email, password);
    }

    @When("I click on the Add to cart button")
    @Step("Adding new product to cart button")
    public void i_click_on_the_add_to_cart_button() {
        addProductToCartAndPurchasePage.addToShoppingCart();
    }

    @When("I click on the cart icon")
    @Step("Clicking on the cart icon")
    public void i_click_on_the_cart_icon() {
        addProductToCartAndPurchasePage.clickShoppingCartIcon();
    }

    @When("I should see a clickable proceed to checkout button")
    @Step("Proceeding to checkout, clicking on the button")
    public void i_should_see_a_clickable_proceed_to_checkout_button() {
        addProductToCartAndPurchasePage.checkOutButton();
    }

    @When("I already have my account details filled")
    public void i_already_have_my_account_details_filled() {
        addProductToCartAndPurchasePage.accountDetailsProceed();
    }
    @When("I select a payment method")
    public void i_select_a_payment_method() {
        addProductToCartAndPurchasePage.choosePaymentMethodCheck();
    }
    @When("I confirm the order")
    public void i_confirm_the_order() {
        addProductToCartAndPurchasePage.confirmOrderButton();
    }
    @Then("I should see a confirmation message")
    public void i_should_see_a_confirmation_message() {
        addProductToCartAndPurchasePage.orderConfirmationMessage();
    }
}
