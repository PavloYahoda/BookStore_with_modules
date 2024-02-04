package pyah.bookstore.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class BooksPage {
    private final Page booksPage;
    private final Locator loginButton;

    public BooksPage(Page booksPage, Locator loginButton) {
        this.booksPage = booksPage;
        this.loginButton = booksPage.locator("#login");
    }

    public void goToLoginPage(){
        booksPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        loginButton.click();
    }
}
