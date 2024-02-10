package pyah.bookstore.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBookPage {
    private final Page testBookPage;
    private final Locator addToCollectionButton;
    private final Locator profileTab;

    public TestBookPage(Page testBookPage) {
        this.testBookPage = testBookPage;
        this.addToCollectionButton = testBookPage.getByText("Add To Your Collection");
        this.profileTab = testBookPage.locator("//div[@class='element-list collapse show']//li[@id=\"item-3\"]");
    }

    public void addBookToCollection(){
        testBookPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        testBookPage.onceDialog(dialog -> {
            assertEquals("alert", dialog.type());
//            assertEquals("Book added to your collection.", dialog.message());
            dialog.accept();
        });
        addToCollectionButton.click();
    }

    public void goToProfile(){
        profileTab.click();
    }
}
