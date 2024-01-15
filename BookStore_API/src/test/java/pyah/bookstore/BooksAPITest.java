package pyah.bookstore;


import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class BooksAPITest extends BaseTest {
    UserData userData = new UserData(Helper.randomFullName(), Helper.PASSWORD);

    @Test
    @Order(1)
    void createUser() {

        ExtractableResponse<Response> responseNewUser = postMethod(Helper.BASE_URL_ACCOUNT, userData, null, Helper.ENDPOINT_CREATE_USER);
        assertNotNull(responseNewUser.body().jsonPath().get("userID"));
        userData.setUserID(responseNewUser.body().jsonPath().getString("userID"));
    }

    @Test
    @Order(2)
    void getToken() {

        ExtractableResponse<Response> responseToken = postMethod(Helper.BASE_URL_ACCOUNT, userData, null, Helper.ENDPOINT_GENERATE_TOKEN);
        assertEquals(responseToken.body().jsonPath().get("status"), "Success");
        assertNotNull(responseToken.body().jsonPath().get("token"));

        userData.setToken(responseToken.body().jsonPath().getString("token"));
    }

    @Test
    @Order(3)
    void userLogin() {

        ExtractableResponse<Response> responseLogin = postMethod(Helper.BASE_URL_ACCOUNT, userData, userData.getToken(), Helper.ENDPOINT_LOGIN);
        assertEquals(responseLogin.body().jsonPath().get("userId"), userData.getUserID());
    }

    @Test
    @Order(4)
    void getUserById() {

        ExtractableResponse<Response> responseUserById = getMethod(Helper.BASE_URL_ACCOUNT, userData.getToken(), Helper.ENDPOINT_USER_ID + userData.getUserID());
        assertEquals(responseUserById.body().jsonPath().get("userId"), userData.getUserID());
        assertEquals(responseUserById.body().jsonPath().get("username"), userData.getUserName());
    }

    @Test
    @Order(5)
    void getAllBooks() {

        ExtractableResponse<Response> responseAllBooks = getMethod(Helper.BASE_URL_BOOK_STORE, null, Helper.ENDPOINT_BOOKS);
        List<String> allBooks = responseAllBooks.body().jsonPath().getList("books.isbn");
        userData.setAllBooks(allBooks);
        assertNotNull(userData.getAllBooks());
    }

    @Test
    @Order(6)
    void setUserBooks() {

        String body = "{\n" +
                "  \"userId\": \"" + userData.getUserID() + "\",\n" +
                "  \"collectionOfIsbns\": [\n" +
                "    {\n" +
                "      \"isbn\": \"" + userData.getAllBooks().get(0) + "\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"isbn\": \"" + userData.getAllBooks().get(1) + "\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"isbn\": \"" + userData.getAllBooks().get(2) + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        ExtractableResponse<Response> responseSetUserBook = postMethodWithStringPayload(Helper.BASE_URL_BOOK_STORE, body, userData.getToken(), Helper.ENDPOINT_BOOKS);
        assertEquals(responseSetUserBook.body().jsonPath().get("books.isbn[0]"), userData.getAllBooks().get(0));
        assertEquals(responseSetUserBook.body().jsonPath().get("books.isbn[1]"), userData.getAllBooks().get(1));
        assertEquals(responseSetUserBook.body().jsonPath().get("books.isbn[2]"), userData.getAllBooks().get(2));
    }

    @Test
    @Order(7)
    void getBookByISBN() {

        ExtractableResponse<Response> responseGetBookByIsbn = getMethod(Helper.BASE_URL_BOOK_STORE, userData.getToken(), Helper.ENDPOINT_BOOKS_BY_ISBN + userData.getAllBooks().get(2));
        assertEquals(responseGetBookByIsbn.body().jsonPath().get("isbn"), "9781449337711");
        assertEquals(responseGetBookByIsbn.body().jsonPath().get("title"), "Designing Evolvable Web APIs with ASP.NET");
        assertEquals(responseGetBookByIsbn.body().jsonPath().get("author"), "Glenn Block et al.");


//                .body("isbn", equalTo("9781449337711"))
//                .body("title", equalTo("Designing Evolvable Web APIs with ASP.NET"))
//                .body("subTitle", equalTo("Harnessing the Power of the Web"))
//                .body("author", equalTo("Glenn Block et al."))
//                .body("publish_date", equalTo("2020-06-04T09:12:43.000Z"))
//                .body("publisher", equalTo("O'Reilly Media"))
//                .body("pages", equalTo(238))
//                .body("description", containsString("Design and build Web APIs"))
//                .body("website", equalTo("http://chimera.labs.oreilly.com/books/1234000001708/index.html"));
    }

    @Test
    @Order(8)
    void getUserBooks() {

        ExtractableResponse<Response> responseUserBooks = getMethod(Helper.BASE_URL_ACCOUNT, userData.getToken(), Helper.ENDPOINT_USER_ID + userData.getUserID());
        assertEquals(responseUserBooks.body().jsonPath().get("books.isbn[0]"), userData.getAllBooks().get(0));
        assertEquals(responseUserBooks.body().jsonPath().get("books.isbn[1]"), userData.getAllBooks().get(1));
        assertEquals(responseUserBooks.body().jsonPath().get("books.isbn[2]"), userData.getAllBooks().get(2));
    }

    @Test
    @Order(9)
    void deleteUserBook() {

        String body = "{\n" +
                "  \"isbn\": \"" + userData.getAllBooks().get(0) + "\",\n" +
                "  \"userId\": \"" + userData.getUserID() + "\"\n" +
                "}";

        ExtractableResponse<Response> responseDeleteUserBook = deleteMethodWithStringPayload(Helper.BASE_URL_BOOK_STORE, body, userData.getToken(), Helper.ENDPOINT_BOOK);
        assertEquals(responseDeleteUserBook.statusCode(), 204);
    }

    @Test
    @Order(10)
    void isBookDeleted() {

        ExtractableResponse<Response> responseIsBookDeleted = getMethod(Helper.BASE_URL_ACCOUNT, userData.getToken(), Helper.ENDPOINT_USER_ID + userData.getUserID());
        assertFalse(responseIsBookDeleted.body().jsonPath().getList("books.isbn").contains(userData.getAllBooks().get(0)));
    }

    @Test
    @Order(11)
    void deleteAllUserBooks() {

        ExtractableResponse<Response> responseDeleteAllBooks = deleteMethodWithQueryParam(Helper.BASE_URL_BOOK_STORE, userData.getUserID(), userData.getToken(), Helper.ENDPOINT_BOOKS);
        assertEquals(responseDeleteAllBooks.statusCode(), 204);
    }

    @Test
    @Order(12)
    void areBooksDeleted() {

        ExtractableResponse<Response> responseAreBooksDeleted = getMethod(Helper.BASE_URL_ACCOUNT, userData.getToken(), Helper.ENDPOINT_USER_ID + userData.getUserID());
        assertNull(responseAreBooksDeleted.body().jsonPath().get("books.isbn[0]"));
    }

    @Test
    @Order(13)
    void deleteUser() {

        ExtractableResponse<Response> responseDeleteUser = deleteMethod(Helper.BASE_URL_ACCOUNT, userData.getToken(), Helper.ENDPOINT_USER_ID + userData.getUserID());
        assertEquals(responseDeleteUser.statusCode(), 204);
    }

    @Test
    @Order(14)
    void isUserDeleted() {

        ExtractableResponse<Response> responseIsUserDeleted = getMethod(Helper.BASE_URL_ACCOUNT, userData.getToken(), Helper.ENDPOINT_USER_ID + userData.getUserID());
        assertNull(responseIsUserDeleted.body().jsonPath().get("userId"));
        assertEquals(responseIsUserDeleted.body().jsonPath().get("message"), "User not found!");
    }
}

