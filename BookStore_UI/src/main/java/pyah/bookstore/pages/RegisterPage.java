package pyah.bookstore.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class RegisterPage {
    private final Page registerPage;
    private final Locator firstName;
    private final Locator lastName;
    private final Locator userName;
    private final Locator password;
    private final Locator checkboxNotRobot;
    private final Locator registerButton;


    public RegisterPage(Page registerPage, Locator firstName, Locator lastName, Locator userName, Locator password, Locator checkboxNotRobot, Locator registerButton) {
        this.registerPage = registerPage;
        this.firstName = registerPage.locator("#firstname");
        this.lastName = registerPage.locator("#lastname");
        this.userName = registerPage.locator("#userName");
        this.password = registerPage.locator("#password");
        this.checkboxNotRobot = registerPage.locator("#recaptcha-anchor-label");
        this.registerButton = registerPage.locator("#register");
    }

    public void createNewUser() throws InterruptedException {
        registerPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        firstName.fill("John");
        lastName.fill("Gaspar");
        userName.fill("johngaspar");
        password.fill("Admin123!");
        checkboxNotRobot.setChecked(true);
        Thread.sleep(3000);
        registerButton.click();
    }
}

