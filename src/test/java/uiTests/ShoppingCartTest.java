package uiTests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import service.CheckoutFormPage;
import service.LoginPage;
import service.ProductPage;
import service.ShoppingCartPage;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("магазин товаров")
@Feature("Процесс входа в магазин и выбора товаров")
public class ShoppingCartTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private ProductPage productPage;
    private ShoppingCartPage shoppingCartPage;
    private CheckoutFormPage checkoutFormPage;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
        checkoutFormPage = new CheckoutFormPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Link(url = "ссылка на конфлюенс", name = "Требования")
    @TmsLink("ссылка на Jira")
    @Owner("Anna")
    @DisplayName("Е2Е покупка 3 товаров пользователь standard_user")
    @Description("Тестирование процесса покупки пользователь standard_user")
    @Severity(SeverityLevel.CRITICAL)
    public void shoppingFlowTestStandart() {
        step("Авторизация");
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        assertTrue(loginPage.isLoginSuccessful(), "Не удалось авторизоваться");

        step("Выбор трех товаров");
        productPage.addThreeProductsToCart();

        step("Переход в корзину");
        shoppingCartPage.goToCart();
        WebElement yourCart = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//*[@id=\"header_container\"]/div[2]/span")));
        String actualYourCart = yourCart.getText();
        String expectedYourCart = "Your Cart";
        assertEquals(actualYourCart, expectedYourCart, "Вкорзину не зашли");

        step("Проверка количества товаров в корзине");
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("cart_item_label"), 3));
        int itemCount = shoppingCartPage.getItemCount();
        Assertions.assertEquals(3, itemCount, "Количество товаров в корзине не соответствует ожидаемому. Ожидалось: 3, фактически: " + itemCount);

        step("Клик по кнопке \"Купить\"");
        shoppingCartPage.clickBuyButton();

        step("Заполнение формы оформления заказа");
        checkoutFormPage.fillCheckoutForm("Анна", "Тест", "12345");

        step("Нажатие кнопки \"Продолжить\"");
        checkoutFormPage.clickContinueButton();

        step("Проверка итоговой суммы");
        String totalAmount = checkoutFormPage.getTotalAmount();
        assertEquals("Total: $58.29", totalAmount, "Сумма в корзине неверна.");

        step("Завершение покупки");
        checkoutFormPage.finishPurchase();

        step("Проверка сообщения об успешном завершении покупки");
        WebElement thankYouMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"checkout_complete_container\"]/h2")));
        String actualThankYouText = thankYouMessage.getText();
        String expectedThankYouText = "Thank you for your order!";
        assertEquals(actualThankYouText, expectedThankYouText, "Финиш не нажали");
    }
    @Test
    @Link(url = "ссылка на конфлюенс", name = "Требования")
    @TmsLink("ссылка на Jira")
    @Owner("Anna")
    @DisplayName("Е2Е покупка 3 товаров пользователь performance_glitch_user")
    @Description("Тестирование процесса покупки пользователь performance_glitch_user")
    @Severity(SeverityLevel.CRITICAL)
    public void shoppingFlowTestGlitch() {
        step("Авторизация");
        loginPage.enterUsername("performance_glitch_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        assertTrue(loginPage.isLoginSuccessful(), "Не удалось авторизоваться");

        step("Выбор трех товаров");
        productPage.addThreeProductsToCart();

        step("Переход в корзину");
        shoppingCartPage.goToCart();
        WebElement yourCart = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//*[@id=\"header_container\"]/div[2]/span")));
        String actualYourCart = yourCart.getText();
        String expectedYourCart = "Your Cart";
        assertEquals(actualYourCart, expectedYourCart, "Вкорзину не зашли");

        step("Проверка количества товаров в корзине");
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("cart_item_label"), 3));
        int itemCount = shoppingCartPage.getItemCount();
        Assertions.assertEquals(3, itemCount, "Количество товаров в корзине не соответствует ожидаемому. Ожидалось: 3, фактически: " + itemCount);

        step("Клик по кнопке \"Купить\"");
        shoppingCartPage.clickBuyButton();

        step("Заполнение формы оформления заказа");
        checkoutFormPage.fillCheckoutForm("Анна", "Тест", "12345");

        step("Нажатие кнопки \"Продолжить\"");
        checkoutFormPage.clickContinueButton();

        step("Проверка итоговой суммы");
        String totalAmount = checkoutFormPage.getTotalAmount();
        assertEquals("Total: $58.29", totalAmount, "Сумма в корзине неверна.");

        step("Завершение покупки");
        checkoutFormPage.finishPurchase();

        step("Проверка сообщения об успешном завершении покупки");
        WebElement thankYouMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"checkout_complete_container\"]/h2")));
        String actualThankYouText = thankYouMessage.getText();
        String expectedThankYouText = "Thank you for your order!";
        assertEquals(actualThankYouText, expectedThankYouText, "Финиш не нажали");
    }

    @Step("{0}")
    public void step(String message) {
        Allure.addAttachment(message, "");
    }
}