package pyah.bookstore.pageTests;

import org.junit.jupiter.api.Test;
import pyah.bookstore.base.BaseTestRunner;

public class RegisterPageTest extends BaseTestRunner {
    @Test
    public void registerNewUserTest() throws InterruptedException {
        homePage.openUrl();
        homePage.clickTileBookStore();
        booksPage.goToLoginPage();
        loginPage.goToRegisterPage();
        registerPage.createNewUser();
    }
}
