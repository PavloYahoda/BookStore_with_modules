package pyah.bookstore.pageTests;

import org.junit.jupiter.api.Test;
import pyah.bookstore.base.BaseTestRunner;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePageTest extends BaseTestRunner {
    @Test
    public void bookStoreTest() throws InterruptedException {
        homePage.openUrl();
        homePage.clickTileBookStore();
        Thread.sleep(5000);
    }

    @Test
    public void alertsTest() throws InterruptedException {
        homePage.openUrl();
        homePage.clickTileAlerts();
        assertThat(page).hasURL(Pattern.compile(".*/alertsWindows"));
        Thread.sleep(5000);
    }

}
