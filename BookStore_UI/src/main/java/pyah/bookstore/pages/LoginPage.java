package pyah.bookstore.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class LoginPage {
    private final Page loginPage;
    private final Locator newUserButton;

    public LoginPage(Page loginPage, Locator newUserButton) {
        this.loginPage = loginPage;
        this.newUserButton = loginPage.getByText("New User");
    }

    public void goToRegisterPage(){
        loginPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        newUserButton.click();
    }
}
