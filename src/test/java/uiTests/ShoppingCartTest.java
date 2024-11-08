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
@Feature("Вход в магазин и путь до поокупки")
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
    @DisplayName ("Е2Е покупка 3 товаров")
    @Description("Тестирование процесса покупки в интернет-магазине SauceDemo")
    @Severity(SeverityLevel.CRITICAL)
    public void shoppingFlowTest() {

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

//        driver.get("https://www.saucedemo.com/cart.html");

        step("Проверка количества товаров в корзине");
//        wait.until(ExpectedConditions.numberOfElementsToBe(By.className ("cart_item_label"), 3));
//        int itemCount = shoppingCartPage.getItemCount();
//        //Assertions.assertEquals(3, itemCount, "Количество товаров в корзине не соответствует ожидаемому. Ожидалось: 3, фактически: " + itemCount);
//        //assertEquals(3, itemCount, "Количество товаров в корзине не соответствует ожидаемому.");
        int expectedCount = 3;
        int actualCount = shoppingCartPage.getItemCount();
        assertEquals(expectedCount, actualCount,
                "Количество товаров в корзине не соответствует ожидаемому. Ожидалось: "
                        + expectedCount + ", фактически: " + actualCount);

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
    @Owner("Anna")
    @TmsLink("ссылка на Jira")
    @Link(url = "ссылка на конфлюенс", name = "Требования")
    @DisplayName("Проверка успешной авторизации")
    @Tag("Позитивный")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginSuccess() {
        step("Открытие страницы входа");
        //driver.get("https://www.saucedemo.com/");

        step("Ввод имени пользователя");
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameField.sendKeys("standard_user");

        step("Ввод пароля");
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordField.sendKeys("secret_sauce");

        step("Нажатие кнопки входа");
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login-button\"]")));
        loginButton.click();

        step("Проверка успешного входа");
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("app_logo")));
        String actualText = successMessage.getText();
        String expectedText = "Swag Labs";
        Assertions.assertEquals(expectedText, actualText);
    }
    @Test
    @Owner("Anna")
    @TmsLink("ссылка на Jira")
    @Link(url = "ссылка на конфлюенс", name = "Требования")
    @DisplayName("Проверка авторизации заблокированного пользователя")
    @Tag("Негативный")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginNotSuccess() {
        step("Открытие страницы входа");
        driver.get("https://www.saucedemo.com/");

        step("Ввод имени заблокированного пользователя");
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameField.sendKeys("locked_out_user");

        step("Ввод пароля");
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordField.sendKeys("secret_sauce");

        step("Нажатие кнопки входа");
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login-button\"]")));
        loginButton.click();

        step("Проверка ошибки блокировки пользователя");
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3")));
        String actualErrorText = errorMessage.getText();
        String expectedErrorText = "Epic sadface: Sorry, this user has been locked out.";
        Assertions.assertEquals(expectedErrorText, actualErrorText);
    }

    @Step("{0}")
    public void step(String message) {
        Allure.addAttachment(message, "");
    }
}