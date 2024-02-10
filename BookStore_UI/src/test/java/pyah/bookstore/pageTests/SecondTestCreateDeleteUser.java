package pyah.bookstore.pageTests;

import org.junit.jupiter.api.Test;
import pyah.bookstore.base.BaseTestRunner;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecondTestCreateDeleteUser extends BaseTestRunner {

    @Test
    void createDeleteUserTest() throws InterruptedException {
        homePage.navigateToProfilePage();
        Thread.sleep(2000);
        assertThat(page).hasURL(Pattern.compile(".*/profile"));
        Thread.sleep(2000);
        profilePage.clickToRegisterLink();
        assertThat(page).hasURL(Pattern.compile(".*/register"));
        Thread.sleep(2000);
        registerPage.createNewUser();
        Thread.sleep(2000);
        registerPage.goBackToLogin();
        assertThat(page).hasURL(Pattern.compile(".*/login"));
        Thread.sleep(2000);
        loginPage.login();
        assertThat(page).hasURL(Pattern.compile(".*/profile"));
        Thread.sleep(2000);
        profilePage.deleteAccount();
        profilePage.acceptDeletionAlert();
        Thread.sleep(2000);
        assertThat(page).hasURL(Pattern.compile(".*/login"));
        Thread.sleep(2000);
        loginPage.login();
        assertEquals("Invalid username or password!", loginPage.getErrorMessage());
    }

}
