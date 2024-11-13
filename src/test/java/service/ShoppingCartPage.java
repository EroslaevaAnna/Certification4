package service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ShoppingCartPage {
    protected static WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(className = "cart_item_label")
    private static List<WebElement> itemsList;

    @FindBy(xpath = "//*[@id=\"checkout\"]")
    private WebElement buyButton;

    @FindBy(xpath = "//*[@id=\"shopping_cart_container\"]/a")
    private static WebElement clickCart;

    public  ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public static void goToCart() {clickCart.click();
    }

    public static int getItemCount() {
        return itemsList.size();
    }

    public void clickBuyButton() {
        buyButton.click();
    }
}