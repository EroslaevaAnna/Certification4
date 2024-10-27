package certification4;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutFormPage {
    protected WebDriver driver;

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(css = ".btn_primary.cart_button")
    protected WebElement continueButton;

    @FindBy(css = ".summary_total_label")
    protected WebElement totalAmountLabel;

    @FindBy(id = "finish")
    protected WebElement finishButton;

    public CheckoutFormPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        postalCodeField.sendKeys(postalCode);
    }

    public void clickContinueButton() {
        continueButton.click();
    }

    public String getTotalAmount() {
        return totalAmountLabel.getText().trim();
    }

    public void finishPurchase() {
        finishButton.click();
    }
}