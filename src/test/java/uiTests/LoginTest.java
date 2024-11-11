package uiTests;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Shutterbug.shootPage(driver).save();
    }

    @AfterEach
    public  void tearDown () {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Проверка успешной авторизации")
    @Tag("Позитивный")
    public void testLoginSuccess() {
        driver.get("https://www.saucedemo.com/");
        WebElement usernameField = wait.until(visibilityOfElementLocated(By.id("user-name")));
        usernameField.sendKeys("standard_user");
        WebElement passwordField = wait.until(visibilityOfElementLocated(By.id("password")));
        passwordField.sendKeys("secret_sauce");
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login-button\"]")));
        loginButton.click();
        WebElement successMessage = wait.until(visibilityOfElementLocated(By.className("app_logo")));
        String actualText = successMessage.getText();
        String expectedText = "Swag Labs";
        assertEquals(expectedText, actualText);
    }

    @Test
    @DisplayName("Проверка авторизации заблокированного пользователя")
    @Tag("Негативный")
    public void testLoginNotSuccess() {
        driver.get("https://www.saucedemo.com/");
        WebElement usernameField = wait.until(visibilityOfElementLocated(By.id("user-name")));
        usernameField.sendKeys("locked_out_user");
        WebElement passwordField = wait.until(visibilityOfElementLocated(By.id("password")));
        passwordField.sendKeys("secret_sauce");
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login-button\"]")));
        loginButton.click();
        WebElement successMessage = wait.until(visibilityOfElementLocated(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3")));
        String actualErrorText = successMessage.getText();
        String expectedErrorText = "Epic sadface: Sorry, this user has been locked out.";
        assertEquals(expectedErrorText, actualErrorText);
    }
}
