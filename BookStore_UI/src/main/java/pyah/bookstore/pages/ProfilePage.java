package pyah.bookstore.pages;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
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

    public ProfilePage(Page profilePage) {
        this.profilePage = profilePage;
        this.registerLink = profilePage.locator("//a[@href='/register']");
        this.deleteAccountButton = profilePage.getByText("Delete Account");
        this.deleteAllBooksButton = profilePage.locator("//div[@class='text-right button di']//button[@id='submit' and text()='Delete All Books']");
        this.testBook = profilePage.getByText("Speaking JavaScript");
    }

    public void clickToRegisterLink(){
        profilePage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        registerLink.click();
    }

    public void deleteAccount(){
        profilePage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        PlaywrightAssertions.setDefaultAssertionTimeout(10000);
        profilePage.onDialog(dialog -> {
            assertEquals("alert", dialog.type());
            assertEquals("Do you want to delete your account?", dialog.message());
            dialog.accept();
        });
        deleteAccountButton.click();
        profilePage.waitForSelector(".modal-content");
        String modalMessage = profilePage.textContent(".modal-body");
        if (modalMessage.contains("Do you want to delete your account?")) {
            profilePage.click("#closeSmallModal-ok");
        } else {
            profilePage.click(".close");
        }
    }

    public boolean isTestBookPresent(){
        profilePage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        return testBook.isVisible();
    }

    public void deleteAllBooks(){
        profilePage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        PlaywrightAssertions.setDefaultAssertionTimeout(10000);
//        profilePage.onDialog(dialog -> {
//            assertEquals("alert", dialog.type());
//            assertEquals("All Books deleted.", dialog.message());
//            dialog.accept();
//        });
//        deleteAccountButton.click();
//        profilePage.waitForSelector(".modal-content");
//        String modalMessage = profilePage.textContent(".modal-body");
//        if (modalMessage.contains("Do you want to delete all books?")) {
//            profilePage.click("#closeSmallModal-ok");
//        } else {
//            profilePage.click(".close");
//        }
        deleteAccountButton.click();
    }

    public void acceptDeletionModalWindow(){
        profilePage.waitForSelector("//div[@class='modal-body']");
//        String modalMessage = profilePage.textContent(".modal-body");
//        if (modalMessage.contains("Do you want to delete all books?")) {
//            System.out.println("Modal message found: " + modalMessage);
//            profilePage.click("#closeSmallModal-ok");
//        } else {
//            System.out.println("Modal message not found or does not match expected message.");
//        }
        profilePage.click("//button[@id='closeSmallModal-ok']");
    }

    public void acceptDeletionAlert(){
        profilePage.onDialog(dialog -> {
            assertEquals("alert", dialog.type());
            assertEquals("All Books deleted.", dialog.message());
            dialog.accept();
        });
        acceptDeletionModalWindow();
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
