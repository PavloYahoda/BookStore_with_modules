package pyah.bookstore.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;


public class BooksPage {
    private final Page booksPage;
    private final Locator loginButton;
    private final Locator nextButton;
    private final Locator previousButton;
    private final Locator rowsPerPage;
    private final Locator currentPage;
    private final Locator totalPages;


    public BooksPage(Page booksPage) {
        this.booksPage = booksPage;
        this.loginButton = booksPage.locator("#login");
        this.nextButton = booksPage.locator("//button[text()='Next']");
        this.previousButton = booksPage.locator("//button[text()='Previous']");
        this.rowsPerPage = booksPage.locator("//select[@aria-label='rows per page']");
        this.currentPage = booksPage.locator("//input[@aria-label='jump to page']");
        this.totalPages = booksPage.locator("//span[@class='-totalPages']");
    }

    public void goToLoginPage(){
        booksPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        loginButton.click();
    }
    public int getBooksCount(Page page) {
        return (int) page.evaluate("() => {" +
                "const rows = document.querySelectorAll('.rt-tr-group .rt-tr');" +
                "return Array.from(rows).filter(row => row.textContent.trim() !== '').length;" +
                "}");
    }
    public int getAllRowsOnTable(Page page){
        return (int) page.evaluate("() => document.querySelectorAll('.rt-tr-group .rt-tr').length");
    }
    public int getCurrentPage(){
        return Integer.parseInt(currentPage.getAttribute("value"));
    }
    public int getTotalPages(){
       return Integer.parseInt(totalPages.textContent());
    }
    public void changeRowsPerPage(String rowsPerPage){
        this.rowsPerPage.selectOption(rowsPerPage);
    }
    public boolean isNextButtonDisabled(){
        return nextButton.isDisabled();
    }
    public boolean isNextButtonEnabled(){
        return nextButton.isEnabled();
    }
    public boolean isPreviousButtonDisabled(){
        return previousButton.isDisabled();
    }
    public boolean isPreviousButtonEnabled(){
        return previousButton.isEnabled();
    }
    public String getBookTitles(Page page) {
        return page.evaluate("() => {" +
                "const titles = [];" +
                "document.querySelectorAll('.rt-tr-group .rt-td:nth-child(2) a').forEach(link => {" +
                "    titles.push(link.textContent.trim());" +
                "});" +
                "return titles;" +
                "}").toString();
    }
    public void clickNextPage(){
        nextButton.click();
    }
    public void clickPreviousPage(){
        previousButton.click();
    }

}
