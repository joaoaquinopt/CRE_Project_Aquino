/*
 * Autor: João Aquino
 * Data de Criação: 2024-12-04
 * Versão: 1.0.0
 * Descrição: Page Object para a página de pesquisa e visualização de produto.
 */

package Pages;

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
import static org.testng.AssertJUnit.assertFalse;
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
        } catch (Exception e) {
            handleException(e, "Erro ao pesquisar item na barra de pesquisa: ");
        }
    }

    public void listOfProductSearch(String itemName) {
        try {
            Thread.sleep( 1000 );
            wait.until( ExpectedConditions.visibilityOf( searchResults ) );
            List<WebElement> results = searchResults.findElements( By.tagName( "li" ) );
            assertFalse( "No results found", results.isEmpty() );
            boolean foundRunning = false;
            for (WebElement element : results) {
                if (element.getText().contains( "Running" )) {
                    foundRunning = true;
                    break;
                }
            }
            assertTrue( "No item contains 'Running'", foundRunning );
        } catch (Exception e) {
            handleException(e, "Erro ao verificar lista de produtos: ");
        }
    }

    public void clickOnSearchButton() {
        try {
            Thread.sleep( 1000 );
            wait.until( ExpectedConditions.visibilityOf( searchButton ) ).isDisplayed();
            searchButton.sendKeys( Keys.ENTER );
        } catch (Exception e) {
            handleException(e, "Erro ao clicar no botão de pesquisa: ");
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
        String urlAtual = driver.getCurrentUrl();
        String urlNormalized = urlAtual.toLowerCase().replace( "-", " " );
        String nameExpectedNormalized = name.toLowerCase();

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
        } catch (Exception e) {
            handleException(e, "Erro ao verificar detalhes do item: ");
        }
    }

    private void handleException(Exception e, String message) {
        System.err.println(message + e.getMessage());
    }
}
