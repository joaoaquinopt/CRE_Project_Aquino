package Steps;

import BrowserConfig.*;
import Pages.LoginPage;
import Pages.SearchAndVisualizePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Epic("Search and Visualize Page")
@Feature("User should Search and Visualize Itens")
public class SearchAndVisualizeSteps {

    private LoginPage loginPage;
    private WebDriver driver;
    private WebDriverWait wait;
    private SearchAndVisualizePage searchAndVisualizePage;

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
            searchAndVisualizePage = new SearchAndVisualizePage(driver);
        } else {
            throw new IllegalStateException("WebDriver not initialized properly");
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @When("I enter {string} in the search bar")
    @Step("Search a product in the search bar")
    public void i_enter_in_the_search_bar(String itemName) {
        searchAndVisualizePage.searchItemUsingSearchBar(itemName);
    }

    @When("I click the Search button icon")
    @Step("Execute the search")
    public void i_click_the_search_button_icon() {
        searchAndVisualizePage.clickOnSearchButton();
    }

    @Then("I should see a list of products related to {string}")
    @Step("List of the product that was searched")
    public void i_should_see_a_list_of_products_related_to(String itemName) {
        searchAndVisualizePage.listOfProductSearch(itemName);
    }

    @When("I click on the item that contain {string}")
    @Step("Click on the product")
    public void i_click_on_the_item_that_contain(String name) {
        searchAndVisualizePage.selectItemFromSearchResults(name);
    }

    @Then("I should be redirected to the product details page for {string}")
    @Step("Redirected to the product details page")
    public void i_should_be_redirected_to_the_product_details_page_for(String name) {
        searchAndVisualizePage.shouldBeOnTheItemPage(name);
    }

    @Then("I should see the {string}, {string}, {string}")
    @Step("Visualize the product details")
    public void i_should_see_the(String name, String price, String description) {
        searchAndVisualizePage.verifyItemDetails(name, price, description);
    }

}
