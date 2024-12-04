package Pages;

import Utils.Exceptions.Exceptions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class SearchAndVisualizePage {
    protected final WebDriver driver;
    private final WebDriverWait wait;
    private static final Duration TIMEOUT = Duration.ofSeconds( 10 );

    public SearchAndVisualizePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait( driver, TIMEOUT );
        PageFactory.initElements( driver, this );
    }

    @FindBy(css = ".search.results")
    private static WebElement searchResults;

    @FindBy(id = "search")
    private static WebElement searchBarInput;

    @FindBy(css = "button[class='action search']")
    private static WebElement searchButton;

    @FindBy(css = "[data-ui-id='page-title-wrapper']")
    private static WebElement itemNameWrapper;

    @FindBy(css = "[data-price-type='finalPrice']")
    private static WebElement itemPriceWrapper;

    @FindBy(id = "tab-label-description-title")
    private static WebElement descriptionTab;

    public void searchItemUsingSearchBar(String itemName) {
        try {
            wait.until( ExpectedConditions.visibilityOf( searchBarInput ) );
            searchBarInput.clear();
            Thread.sleep( 1000 );
            searchBarInput.sendKeys( itemName );
        } catch (Exceptions.NavigationException | InterruptedException e) {
            throw new RuntimeException( e );
        }
    }

    public void listOfProductSearch(String itemName) {
        try {
            Thread.sleep( 1000 );
            wait.until( ExpectedConditions.visibilityOf( searchResults ) );
            List<WebElement> results = searchResults.findElements( By.tagName( "li" ) );
            assertTrue( "No results found", results.size() > 0 );
            boolean foundRunning = false;
            for (WebElement element : results) {
                if (element.getText().contains( "Running" )) {
                    foundRunning = true;
                    break;
                }
            }
            assertTrue( "No item contains 'Running'", foundRunning );
        } catch (Exceptions.NavigationException | InterruptedException e) {
            throw new RuntimeException( e );
        }
    }

    public void clickOnSearchButton() {
        try {
            Thread.sleep( 1000 );
            wait.until( ExpectedConditions.visibilityOf( searchButton ) ).isDisplayed();
            searchButton.sendKeys( Keys.ENTER );
        } catch (Exceptions.NavigationException | InterruptedException e) {
            throw new RuntimeException( e );
        }
    }

    public void selectItemFromSearchResults(String name) {
        wait.until( ExpectedConditions.visibilityOf( searchResults ) );
        List<WebElement> results = searchResults.findElements( By.tagName( "li" ) );
        for (WebElement element : results) {
            if (element.getText().contains( name )) {
                element.click();
                break;
            }
        }
    }

    public void shouldBeOnTheItemPage(String name) {
        String urlAtual = driver.getCurrentUrl(); // Obtém a URL atual

        // Normaliza a URL: converte para minúsculas e remove hífens
        String urlNormalized = urlAtual.toLowerCase().replace( "-", " " );

        // Normaliza o nome esperado: converte para minúsculas
        String nameExpectedNormalized = name.toLowerCase();

        // Verifica se a URL normalizada contém o nome esperado normalizado
        Assert.assertTrue( urlNormalized.contains( nameExpectedNormalized ),
                "The URL does not contain the name: " + name );
    }

    public void verifyItemDetails(String name, String price, String details) {
        try {
            wait.until( ExpectedConditions.visibilityOf( itemNameWrapper ) );
            assertEquals( name, itemNameWrapper.getText() );

            wait.until( ExpectedConditions.visibilityOf( itemPriceWrapper ) );
            assertEquals( price, itemPriceWrapper.getText() );

            wait.until( ExpectedConditions.visibilityOf( descriptionTab ) );
            assertEquals( details, descriptionTab.getText() );
        } catch (Exceptions.NavigationException e) {
            throw new RuntimeException( e );
        }
    }
}
