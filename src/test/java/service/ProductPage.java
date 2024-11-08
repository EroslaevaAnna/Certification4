package service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {
    private final WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver,  Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void addThreeProductsToCart() {

        WebElement addToCart1 = wait.until(ExpectedConditions.elementToBeClickable(By.id
                ("add-to-cart-sauce-labs-backpack")));
        addToCart1.click();
        WebElement addToCart2 = wait.until(ExpectedConditions.elementToBeClickable(By.id
                ("add-to-cart-sauce-labs-bolt-t-shirt")));
        addToCart2.click();
        WebElement addToCart3 = wait.until(ExpectedConditions.elementToBeClickable(By.id
                ("add-to-cart-sauce-labs-onesie")));
        addToCart3.click();
//
//        WebElement addToCart1 = wait.until(ExpectedConditions.elementToBeClickable
//                (By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")));
//       addToCart1.click();
//        WebElement addToCart2 = wait.until(ExpectedConditions.elementToBeClickable
//                (By.cssSelector("[data-test='add-to-cart-sauce-labs-bolt-t-shirt']")));
//        addToCart2.click();
//        WebElement addToCart3 = wait.until(ExpectedConditions.elementToBeClickable
//                (By.cssSelector("[data-test='add-to-cart-sauce-labs-onesie']")));
//        addToCart3.click();
    }
}

