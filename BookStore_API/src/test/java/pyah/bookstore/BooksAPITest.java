package pyah.bookstore;


import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class BooksAPITest extends BaseTest {
    UserData userData = new UserData(Helper.randomFullName(), Helper.PASSWORD);

    @Test
    void booksAPITest() throws JSONException {

    // POST createUser

        ExtractableResponse<Response> responseNewUser = postMethod(Helper.BASE_URL_ACCOUNT, userData, null, Helper.ENDPOINT_CREATE_USER);
        assertEquals(responseNewUser.statusCode(), 201);
        assertNotNull(responseNewUser.body().jsonPath().get("userID"));
        userData.setUserID(responseNewUser.body().jsonPath().getString("userID"));

    // GET getToken

        ExtractableResponse<Response> responseToken = postMethod(Helper.BASE_URL_ACCOUNT, userData, null, Helper.ENDPOINT_GENERATE_TOKEN);
        assertEquals(responseToken.statusCode(), 200);
        assertEquals(responseToken.body().jsonPath().get("status"), "Success");
        assertNotNull(responseToken.body().jsonPath().get("token"));

        userData.setToken(responseToken.body().jsonPath().getString("token"));

    // POST userLogin

        ExtractableResponse<Response> responseLogin = postMethod(Helper.BASE_URL_ACCOUNT, userData, userData.getToken(), Helper.ENDPOINT_LOGIN);
        assertEquals(responseLogin.statusCode(), 200);
        assertEquals(responseLogin.body().jsonPath().get("userId"), userData.getUserID());
        System.out.println("CHECK:  " + responseLogin.asPrettyString());

        String str = String.format(Helper.readDataFromFile(new File("src/test/resources/loginUserResponse.json")), userData.getUserID(), userData.getUserName(), Helper.PASSWORD, userData.getToken());
        JSONObject json = new JSONObject(str);
        System.out.println("CHECK:  " + str);
        JSONAssert.assertEquals(
                json.toString(),
                responseLogin.asPrettyString(),
                JSONCompareMode.LENIENT
        );

    // GET getUserById

        ExtractableResponse<Response> responseUserById = getMethod(Helper.BASE_URL_ACCOUNT, userData.getToken(), Helper.ENDPOINT_USER_ID + userData.getUserID());
        assertEquals(responseUserById.statusCode(), 200);
        assertEquals(responseUserById.body().jsonPath().get("userId"), userData.getUserID());
        assertEquals(responseUserById.body().jsonPath().get("username"), userData.getUserName());

    // GET getAllBooks

        ExtractableResponse<Response> responseAllBooks = getMethod(Helper.BASE_URL_BOOK_STORE, null, Helper.ENDPOINT_BOOKS);
        assertEquals(responseAllBooks.statusCode(), 200);
        List<String> allBooks = responseAllBooks.body().jsonPath().getList("books.isbn");
        userData.setAllBooks(allBooks);
        assertNotNull(userData.getAllBooks());

    // POST setUserBooks

        String bodyForSetBooks = "{\n" +
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

        ExtractableResponse<Response> responseSetUserBook = postMethodWithStringPayload(Helper.BASE_URL_BOOK_STORE, bodyForSetBooks, userData.getToken(), Helper.ENDPOINT_BOOKS);
        assertEquals(responseSetUserBook.statusCode(), 201);
        assertEquals(responseSetUserBook.body().jsonPath().get("books.isbn[0]"), userData.getAllBooks().get(0));
        assertEquals(responseSetUserBook.body().jsonPath().get("books.isbn[1]"), userData.getAllBooks().get(1));
        assertEquals(responseSetUserBook.body().jsonPath().get("books.isbn[2]"), userData.getAllBooks().get(2));

    // GET getBookByISBN

        ExtractableResponse<Response> responseGetBookByIsbn = getMethod(Helper.BASE_URL_BOOK_STORE, userData.getToken(), Helper.ENDPOINT_BOOKS_BY_ISBN + userData.getAllBooks().get(2));
        assertEquals(responseGetBookByIsbn.statusCode(), 200);
        assertEquals(responseGetBookByIsbn.body().jsonPath().get("isbn"), "9781449337711");
        assertEquals(responseGetBookByIsbn.body().jsonPath().get("title"), "Designing Evolvable Web APIs with ASP.NET");
        assertEquals(responseGetBookByIsbn.body().jsonPath().get("author"), "Glenn Block et al.");

    // GET getUserBooks

        ExtractableResponse<Response> responseUserBooks = getMethod(Helper.BASE_URL_ACCOUNT, userData.getToken(), Helper.ENDPOINT_USER_ID + userData.getUserID());
        assertEquals(responseUserBooks.statusCode(), 200);
        assertEquals(responseUserBooks.body().jsonPath().get("books.isbn[0]"), userData.getAllBooks().get(0));
        assertEquals(responseUserBooks.body().jsonPath().get("books.isbn[1]"), userData.getAllBooks().get(1));
        assertEquals(responseUserBooks.body().jsonPath().get("books.isbn[2]"), userData.getAllBooks().get(2));

    // DELETE deleteUserBook

        String bodyForDeleteUser = "{\n" +
                "  \"isbn\": \"" + userData.getAllBooks().get(0) + "\",\n" +
                "  \"userId\": \"" + userData.getUserID() + "\"\n" +
                "}";

        ExtractableResponse<Response> responseDeleteUserBook = deleteMethodWithStringPayload(Helper.BASE_URL_BOOK_STORE, bodyForDeleteUser, userData.getToken(), Helper.ENDPOINT_BOOK);
        assertEquals(responseDeleteUserBook.statusCode(), 204);

    // GET isBookDeleted

        ExtractableResponse<Response> responseIsBookDeleted = getMethod(Helper.BASE_URL_ACCOUNT, userData.getToken(), Helper.ENDPOINT_USER_ID + userData.getUserID());
        assertEquals(responseIsBookDeleted.statusCode(), 200);
        assertFalse(responseIsBookDeleted.body().jsonPath().getList("books.isbn").contains(userData.getAllBooks().get(0)));

    // DELETE deleteAllUserBooks

        ExtractableResponse<Response> responseDeleteAllBooks = deleteMethodWithQueryParam(Helper.BASE_URL_BOOK_STORE, userData.getUserID(), userData.getToken(), Helper.ENDPOINT_BOOKS);
        assertEquals(responseDeleteAllBooks.statusCode(), 204);

    // GET areBooksDeleted

        ExtractableResponse<Response> responseAreBooksDeleted = getMethod(Helper.BASE_URL_ACCOUNT, userData.getToken(), Helper.ENDPOINT_USER_ID + userData.getUserID());
        assertEquals(responseAreBooksDeleted.statusCode(), 200);
        assertNull(responseAreBooksDeleted.body().jsonPath().get("books.isbn[0]"));

    // DELETE deleteUser

        ExtractableResponse<Response> responseDeleteUser = deleteMethod(Helper.BASE_URL_ACCOUNT, userData.getToken(), Helper.ENDPOINT_USER_ID + userData.getUserID());
        assertEquals(responseDeleteUser.statusCode(), 204);

    // GET isUserDeleted

        ExtractableResponse<Response> responseIsUserDeleted = getMethod(Helper.BASE_URL_ACCOUNT, userData.getToken(), Helper.ENDPOINT_USER_ID + userData.getUserID());
        assertEquals(responseIsUserDeleted.statusCode(), 401);
        assertNull(responseIsUserDeleted.body().jsonPath().get("userId"));
        assertEquals(responseIsUserDeleted.body().jsonPath().get("message"), "User not found!");
    }
}

