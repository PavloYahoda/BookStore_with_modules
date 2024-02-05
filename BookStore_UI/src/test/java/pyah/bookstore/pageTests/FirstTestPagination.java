package pyah.bookstore.pageTests;

import org.junit.jupiter.api.Test;
import pyah.bookstore.base.BaseTestRunner;

import static org.junit.jupiter.api.Assertions.*;

public class FirstTestPagination extends BaseTestRunner {
    @Test
    public void paginationTest(){
        homePage.openUrl();
        homePage.clickTileBookStore();

//  Step 1
        int booksBefore = booksPage.getBooksCount(page);
        int totalPagesBefore = booksPage.getTotalPages();
        String listOfBooksBefore = booksPage.getBookTitles(page);
        System.out.println(listOfBooksBefore);

//      Check that current page = 1
        assertEquals(1, booksPage.getCurrentPage());

//      Check state of buttons before
        assertTrue(booksPage.isNextButtonDisabled());
        assertTrue(booksPage.isPreviousButtonDisabled());

//      Change rows per page to 5
        booksPage.changeRowsPerPage("5");

//      Check count of books on the page
        assertEquals(5, booksPage.getBooksCount(page));

//      Check that count of books is changed
        assertNotEquals(booksBefore, booksPage.getBooksCount(page));

//      Check that total pages is changed
        assertNotEquals(totalPagesBefore, booksPage.getTotalPages());

//      Check that list of books on the page is changed
        assertNotEquals(listOfBooksBefore, booksPage.getBookTitles(page));
        System.out.println(booksPage.getBookTitles(page));

//      Check state of buttons after
        assertTrue(booksPage.isNextButtonEnabled());
        assertTrue(booksPage.isPreviousButtonDisabled());

//  Step 2

        String listOfBooks = booksPage.getBookTitles(page);
        totalPagesBefore = booksPage.getTotalPages();

        booksPage.clickNextPage();

//      Check that list of books on the page is changed
        assertNotEquals(listOfBooks, booksPage.getBookTitles(page));
        System.out.println(booksPage.getBookTitles(page));

//      Check that current page = 2
        assertEquals(2, booksPage.getCurrentPage());

//      Check that total pages is not changed
        assertEquals(totalPagesBefore, booksPage.getTotalPages());

//      Check count of rows on the page
        assertEquals(5, booksPage.getAllRowsOnTable(page));

//      Check state of buttons after
        assertTrue(booksPage.isNextButtonDisabled());
        assertTrue(booksPage.isPreviousButtonEnabled());

//  Step 3

        booksPage.clickPreviousPage();

//      Check that current page = 1
        assertEquals(1, booksPage.getCurrentPage());

//      Check state of buttons after
        assertTrue(booksPage.isNextButtonEnabled());
        assertTrue(booksPage.isPreviousButtonDisabled());

//  Step 4

//      Change rows per page to 10
        booksPage.changeRowsPerPage("10");

//      Check state of buttons like before
        assertTrue(booksPage.isNextButtonDisabled());
        assertTrue(booksPage.isPreviousButtonDisabled());

//      Check count of rows on the page
        assertEquals(10, booksPage.getAllRowsOnTable(page));

//      Check that list of books on the page is like before
        assertEquals(listOfBooksBefore, booksPage.getBookTitles(page));
        System.out.println(booksPage.getBookTitles(page));
    }
}
