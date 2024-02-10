package pyah.bookstore.pages;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfilePage {
    private final Page profilePage;
    private final Locator registerLink;
    private final Locator deleteAccountButton;
    private final Locator deleteAllBooksButton;
    private final Locator testBook;
    private final Locator okButton;
    private final Locator modalWindow;


    public ProfilePage(Page profilePage) {
        this.profilePage = profilePage;
        this.registerLink = profilePage.locator("//a[@href='/register']");
        this.deleteAccountButton = profilePage.getByText("Delete Account");
        this.deleteAllBooksButton = profilePage.locator("//div[@class='text-right button di']//button[@id='submit' and text()='Delete All Books']");
        this.testBook = profilePage.getByText("Speaking JavaScript");
        this.okButton = profilePage.locator("//button[@id='closeSmallModal-ok']");
        this.modalWindow = profilePage.locator(".modal-content");
    }

    public void clickToRegisterLink(){
        profilePage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        registerLink.click();
    }

    public void deleteAccount(){
        profilePage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        PlaywrightAssertions.setDefaultAssertionTimeout(10000);
        deleteAccountButton.click();
        System.out.println(modalWindow.isVisible());
        profilePage.waitForSelector(".modal-content");
        String modalMessage = profilePage.textContent(".modal-body");
        if (modalMessage.contains("Do you want to delete your account?")) {
            System.out.println(okButton.isEnabled());
            profilePage.click("#closeSmallModal-ok");
        } else {
            System.out.println("Modal message not found or does not match expected message.");
        }
    }

    public boolean isTestBookPresent(){
        profilePage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        return testBook.isVisible();
    }

    public void deleteAllBooks() {
        profilePage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        PlaywrightAssertions.setDefaultAssertionTimeout(10000);
        deleteAllBooksButton.click();
        System.out.println(modalWindow.isVisible());
        profilePage.waitForSelector(".modal-content");
        String modalMessage = profilePage.textContent(".modal-body");
        if (modalMessage.contains("Do you want to delete all books?")) {
            System.out.println(okButton.isEnabled());
            profilePage.click("#closeSmallModal-ok");
        } else {
            System.out.println("Modal message not found or does not match expected message.");
        }
    }

    public void acceptDeletionAlert() {
        profilePage.onceDialog(Dialog::accept);
    }

    public String getBookTitles(Page page) {
        return page.evaluate("() => {" +
                "const titles = [];" +
                "document.querySelectorAll('.rt-tbody .rt-tr-group .rt-td:nth-child(2) a').forEach(link => {" +
                "    titles.push(link.textContent.trim());" +
                "});" +
                "return titles;" +
                "}").toString();
    }
}
