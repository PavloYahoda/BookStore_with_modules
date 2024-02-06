package pyah.bookstore.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class ProfilePage {
    private final Page profilePage;
    private final Locator registerLink;
    private final Locator deleteAccountButton;

    public ProfilePage(Page profilePage) {
        this.profilePage = profilePage;
        this.registerLink = profilePage.locator("//a[@href='/register']");
        this.deleteAccountButton = profilePage.getByText("Delete Account");
    }

    public void clickToRegisterLink(){
        profilePage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        registerLink.click();
    }

    public void deleteAccount(){
        deleteAccountButton.click();
    }
    public void acceptDeleting(){
        profilePage.onDialog(dialog -> dialog.accept());
    }
    public void acceptPopUpAfterDeleting(){
        profilePage.onDialog(dialog -> dialog.accept());
    }
}
