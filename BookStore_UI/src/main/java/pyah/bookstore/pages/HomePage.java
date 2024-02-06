package pyah.bookstore.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import static pyah.bookstore.PropertiesReader.getMainProperty;

public class HomePage {
    private final Page homePage;
    private final Locator tileBookStore;
    private final Locator tileAlerts;


    public HomePage(Page homePage) {
        this.homePage = homePage;
        this.tileBookStore = homePage.locator("//*[text()='Book Store Application']/ancestor::div[@class='card mt-4 top-card']");
        this.tileAlerts = homePage.getByText("Alerts");
    }

    public void openUrl(){
        homePage.navigate(getMainProperty("baseUrl"));
    }
    public void navigateToProfilePage(){homePage.navigate(getMainProperty("profile"));}

    public void clickTileBookStore(){
        homePage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        tileBookStore.scrollIntoViewIfNeeded();
        tileBookStore.click();
    }

    public void clickTileAlerts(){
        homePage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        tileAlerts.click();
    }

}
