package pyah.bookstore.pages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import static pyah.bookstore.PropertiesReader.getMainProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;




public class RegisterPage {
    private final Page registerPage;
    private final Locator firstName;
    private final Locator lastName;
    private final Locator userName;
    private final Locator password;
    private final FrameLocator checkboxNotRobot;
    private final Locator registerButton;
    private final Locator backToLoginButton;



    public RegisterPage(Page registerPage) {
        this.registerPage = registerPage;
        this.firstName = registerPage.locator("#firstname");
        this.lastName = registerPage.locator("#lastname");
        this.userName = registerPage.locator("#userName");
        this.password = registerPage.locator("#password");
        this.checkboxNotRobot = registerPage.frameLocator("//*[@id=\"g-recaptcha\"]/div/div/iframe");
        this.registerButton = registerPage.locator("#register");
        this.backToLoginButton = registerPage.locator("#gotologin");
    }

    public void createNewUser() throws InterruptedException {
        registerPage.waitForLoadState(LoadState.DOMCONTENTLOADED);

        firstName.fill(getMainProperty("firstName"));
        lastName.fill(getMainProperty("lastName"));
        userName.fill(getMainProperty("userName"));
        password.fill(getMainProperty("password"));
        Thread.sleep(1000);
        checkboxNotRobot.locator(".recaptcha-checkbox-border").click();
        Thread.sleep(1000);
        registerPage.onceDialog(dialog -> {
            assertEquals("alert", dialog.type());
//            assertEquals("User Register Successfully.", dialog.message());
            dialog.accept();
        });
        registerButton.click();
    }

    public void goBackToLogin(){
        registerPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        backToLoginButton.click();
    }
}

