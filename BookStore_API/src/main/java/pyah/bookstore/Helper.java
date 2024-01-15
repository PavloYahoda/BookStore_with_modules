package pyah.bookstore;


import com.github.javafaker.Faker;

public class Helper{

    public static final String PASSWORD = "Admin123!";
    public static final String BASE_URL_ACCOUNT = "https://demoqa.com/Account/v1";
    public static final String BASE_URL_BOOK_STORE = "https://demoqa.com/BookStore/v1";
    public static final String ENDPOINT_CREATE_USER = "/User";
    public static final String ENDPOINT_LOGIN = "/Login";
    public static final String ENDPOINT_GENERATE_TOKEN = "/GenerateToken";
    public static final String ENDPOINT_USER_ID = "/User/";
    public static final String ENDPOINT_BOOKS = "/Books";
    public static final String ENDPOINT_BOOK = "/Book";
    public static final String ENDPOINT_BOOKS_BY_ISBN = "/Book?ISBN=";
    public static String randomFullName(){
        Faker faker = new Faker();
        return faker.name().fullName();
    }
    public static String randomFirstName(){

        Faker faker = new Faker();
        return faker.name().firstName();
    }
    public static String randomLastName(){
        Faker faker = new Faker();
        return faker.name().lastName();
    }
    public static String randomStreetAddress(){
        Faker faker = new Faker();
        return faker.address().streetAddress();
    }
}
