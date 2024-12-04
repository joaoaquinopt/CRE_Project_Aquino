package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class AddProductToCartAndPurchasePage {
    private final WebDriver driver;
    private WebDriverWait wait;
    private Select select;

    public AddProductToCartAndPurchasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait( driver, Duration.ofSeconds( 10 ) );
        this.select = select;
        PageFactory.initElements( driver, this );
    }

    @FindBy(id = "product-addtocart-button")
    private WebElement addToCartButton;

    @FindBy(css = "[data-attribute-code='size']")
    private WebElement sizeOptions;

    @FindBy(css = "[data-attribute-code='color']")
    private WebElement colorOptions;

    @FindBy(css = ".action.showcart")
    private WebElement shoppingCartIcon;

    @FindBy(id = "top-cart-btn-checkout")
    private WebElement proceedToCheckOutButton;

    @FindBy(css = "li[id='shipping'] div[class='step-title']")
    private WebElement checkOutPageConfirmation;

    @FindBy(css = ".shipping-address-item.selected-item")
    private WebElement shippingAddressDetails;

    @FindBy(name = "ko_unique_1")
    private WebElement shippingMethod;

    @FindBy(css = "span[data-bind=\"i18n: 'Next'\"]")
    private WebElement nextButton;

    @FindBy(id = "checkmo")
    private WebElement paymentMethod;

    @FindBy(css = "div[class='payment-method _active'] button[title='Place Order'] span")
    private WebElement confirmOrderButton;

    @FindBy(css = "p:nth-child(2)")
    private WebElement orderConfirmationMessage;

    public void selectSize(String size) {
        WebElement sizeOption = sizeOptions.findElement(By.cssSelector("[data-option-label='" + size + "']"));

        sizeOption.click();
    }

    public void selectColor(String color) {
        WebElement colorOption = colorOptions.findElement(By.cssSelector("[data-option-label='" + color + "']"));

        colorOption.click();
    }

    public void addToShoppingCart() {
        selectSize( "M" );
        selectColor( "Blue" );
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.submit();
    }

    public void clickShoppingCartIcon() {
        try {
            // Sleep for 5 seconds (5000 milliseconds)
            Thread.sleep(5000); // This is the simple fixed wait
            wait.until(ExpectedConditions.elementToBeClickable(shoppingCartIcon));
            shoppingCartIcon.click();
        } catch (Exception e) {
            // Catch any other exceptions and print the error
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public void checkOutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckOutButton));
        proceedToCheckOutButton.click();

        wait.until(ExpectedConditions.visibilityOf(checkOutPageConfirmation));
        String actualText = checkOutPageConfirmation.getText();
        String expectedText = "Shipping Address";

        assertEquals(expectedText, actualText);
    }

    public void accountDetailsProceed() {

        wait.until(ExpectedConditions.visibilityOf(shippingAddressDetails));
        shippingAddressDetails.isDisplayed();

        wait.until(ExpectedConditions.visibilityOf(shippingMethod));
        shippingMethod.isDisplayed();

        wait.until(ExpectedConditions.visibilityOf(nextButton));
        nextButton.submit();
    }

    public void choosePaymentMethodCheck() {
        try {
            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable( paymentMethod ));
            paymentMethod.isDisplayed();
            paymentMethod.click();
        } catch (TimeoutException e) {
            System.err.println("Could not find a clickable element called chekmo" + e.getMessage());
            fail("Expected checkmo to be clickable " + e.getMessage());
        } catch (Exception e) {
            // Catch any other exceptions and print the error
            System.err.println("An error occurred: " + e.getMessage());
            fail("An error occurred: " + e.getMessage());
        }
    }

    public void confirmOrderButton() {
        try {
            // Sleep for 3 seconds (3000 milliseconds)
            Thread.sleep(3000); // This is the simple fixed wait
            wait.until(ExpectedConditions.elementToBeClickable( confirmOrderButton ));
            confirmOrderButton.click();
        } catch (TimeoutException e) {
            System.err.println("Could not find a clickable element called chekmo" + e.getMessage());
            fail("Expected checkmo to be clickable " + e.getMessage());

        }
        catch (Exception e) {
            // Catch any other exceptions and print the error
            System.err.println("An error occurred: " + e.getMessage());
            fail("An error occurred: " + e.getMessage());
        }
    }

    public void orderConfirmationMessage() {
        try {
            // Sleep for 3 seconds (3000 milliseconds)
            Thread.sleep(3000); // This is the simple fixed wait
            wait.until(ExpectedConditions.visibilityOf(orderConfirmationMessage));
            String actualText = orderConfirmationMessage.getText();
            String expectedText = "We'll email you an order confirmation with details and tracking info.";

            assertEquals(expectedText, actualText);
        } catch (TimeoutException e) {
            System.err.println("The order was not confirmation" + e.getMessage());
         }
        catch (Exception e) {
            // Catch any other exceptions and print the error
            System.err.println("An error occurred: " + e.getMessage());
            fail("An error occurred: " + e.getMessage());
        }
    }
}
