package pyah.bookstore;

import java.util.List;

public class UserData {
    private String userName;
    private String password;
    private String token;
    private String userID;
    private String userBook;
    private List<String> allBooks;


    public UserData(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserData() {
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setAllBooks(List<String> allBooks){
        this.allBooks = allBooks;
    }

    public List<String> getAllBooks() {
        return allBooks;
    }

    public void setUserBook(String userBook) {
        this.userBook = userBook;
    }

    public String getUserBook() {
        return userBook;
    }
}

