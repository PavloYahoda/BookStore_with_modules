package pyah.bookstore.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import static pyah.bookstore.PropertiesReader.getMainProperty;

public class LoginPage {
    private final Page loginPage;
    private final Locator newUserButton;
    private final Locator loginButton;
    private final Locator userName;
    private final Locator password;
    private final Locator errorMessage;


    public LoginPage(Page loginPage) {
        this.loginPage = loginPage;
        this.newUserButton = loginPage.getByText("New User");
        this.loginButton = loginPage.locator("#login");
        this.userName = loginPage.locator("#userName");
        this.password = loginPage.locator("#password");
        this.errorMessage = loginPage.locator("//p[@id='name']");
    }

    public void goToRegisterPage(){
        loginPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        newUserButton.click();
    }

    public void login(){
        loginPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        userName.fill(getMainProperty("userName"));
        password.fill(getMainProperty("password"));
        loginButton.click();
    }

    public void loginAsTestUser(){
        loginPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        userName.fill(getMainProperty("testUserName"));
        password.fill(getMainProperty("password"));
        loginButton.click();
    }

    public String getErrorMessage(){
        loginPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        return errorMessage.textContent();
    }
}
