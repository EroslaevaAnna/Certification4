package certification4;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ShoppingCartTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shoppingFlowTest() {
        LoginPage loginPage = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);

        // Шаг 1: Авторизоваться
        driver.get("https://www.saucedemo.com/");
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        assertEquals(true, loginPage.isLoginSuccessful());

        // Шаг 2: Выбрать 3 товара
        productPage.addThreeProductsToCart();

        // Шаг 3: Открыть корзину
        driver.get("https://www.saucedemo.com/cart.html");

         //Шаг 4: Убедиться, что в корзине 3 товара
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("cart_item_label"), 3)); // Условие: должно быть три элемента с классом 'cart_item'
        int itemCount = ShoppingCartPage.getItemCount();
        //   assertEquals(3, itemCount, "Количество товаров в корзине не соответствует ожидаемому.");

        // Шаг 5: Кликнуть кнопку «Купить»
           shoppingCartPage.clickBuyButton();
        // Заполнение полей формы: Имя, Фамилия, Индекс
        CheckoutFormPage checkoutFormPage = new CheckoutFormPage(driver);
        checkoutFormPage.fillCheckoutForm("Анна", "Тест", "12345");

        // Нажать кнопку "Продолжить"
        checkoutFormPage.clickContinueButton();

        // Проверка отображаемой суммы
        String totalAmount = checkoutFormPage.getTotalAmount();
        assertEquals("Total: $58.29", totalAmount, "Сумма в корзине неверна.");

        // Завершение покупки
        checkoutFormPage.finishPurchase();

        WebElement thankYouMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"checkout_complete_container\"]/h2")));
        String actualThankYouText = thankYouMessage.getText();
        String expectedThankYouText = "Thank you for your order!";
        assertEquals(actualThankYouText, expectedThankYouText, "Финиш не нажали");
    }
}
