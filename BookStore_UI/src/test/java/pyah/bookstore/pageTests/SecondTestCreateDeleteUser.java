package pyah.bookstore.pageTests;

import org.junit.jupiter.api.Test;
import pyah.bookstore.base.BaseTestRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecondTestCreateDeleteUser extends BaseTestRunner {

    @Test
    void createDeleteUserTest() throws InterruptedException {
        homePage.navigateToProfilePage();
        profilePage.clickToRegisterLink();
        registerPage.createNewUser();
        registerPage.acceptPopUp();
        registerPage.goBackToLogin();
        loginPage.login();
        profilePage.deleteAccount();
        profilePage.acceptDeleting();
        profilePage.acceptPopUpAfterDeleting();
        loginPage.login();



    }

}
