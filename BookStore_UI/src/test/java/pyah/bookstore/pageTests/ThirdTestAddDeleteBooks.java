package pyah.bookstore.pageTests;

import org.junit.jupiter.api.Test;
import pyah.bookstore.base.BaseTestRunner;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThirdTestAddDeleteBooks extends BaseTestRunner {

    @Test
    void addDeleteBooks() throws InterruptedException {
        homePage.openUrl();
        homePage.clickTileBookStore();
        assertThat(page).hasURL(Pattern.compile(".*/books"));
        booksPage.goToLoginPage();
        Thread.sleep(1000);
        assertThat(page).hasURL(Pattern.compile(".*/login"));
        loginPage.loginAsTestUser();
        assertThat(page).hasURL(Pattern.compile(".*/books"));
        Thread.sleep(1000);
        booksPage.openTestBookPage();
        Thread.sleep(2000);
        testBookPage.addBookToCollection();
        Thread.sleep(2000);
        testBookPage.goToProfile();
        Thread.sleep(2000);
        assertThat(page).hasURL(Pattern.compile(".*/profile"));
        Thread.sleep(2000);
        assertTrue(profilePage.isTestBookPresent());
        assertTrue(profilePage.getBookTitles(page).contains("Speaking JavaScript"));
        Thread.sleep(2000);
        profilePage.deleteAllBooks();
        Thread.sleep(2000);
//        profilePage.acceptDeletionModalWindow();
//        Thread.sleep(2000);
        profilePage.acceptDeletionAlert();
        assertTrue(profilePage.getBookTitles(page).isEmpty());
    }
}
