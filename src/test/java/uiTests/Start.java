package uiTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Start {
    private WebDriver driver;

    @BeforeEach
    public void setup(){
        // открыли браузер ChromeDriver, EdgeDriver, FirefoxDriver
        driver = new ChromeDriver();
    }

    @AfterEach
    public void teardjwn() {
        // закрыли браузер
        if (driver != null) {
            driver.quit();

        }
    }
    @Test
    public void getDemo() {
        driver.get("https://ya.ru");
    }

}

