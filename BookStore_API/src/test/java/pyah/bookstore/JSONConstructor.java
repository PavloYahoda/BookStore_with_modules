package pyah.bookstore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONConstructor {

    public static String getCredentials(){
        JSONObject loginBody = new JSONObject();
        try{
            loginBody.put("username", Helper.randomFullName());
            loginBody.put("password", Helper.PASSWORD);
        } catch(Exception e){
            e.getStackTrace();
        }
        return loginBody.toString();
    }

    public static String getBooksForUser(UserData userData){

//        String bodyForSetBooks = "{\n" +
//                "  \"userId\": \"" + userData.getUserID() + "\",\n" +
//                "  \"collectionOfIsbns\": [\n" +
//                "    {\n" +
//                "      \"isbn\": \"" + userData.getAllBooks().get(0) + "\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"isbn\": \"" + userData.getAllBooks().get(1) + "\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"isbn\": \"" + userData.getAllBooks().get(2) + "\"\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";

        JSONArray array = new JSONArray();
        JSONObject jo1 = new JSONObject();
        try {
            jo1.put("isbn", userData.getAllBooks().get(0));
        }catch (Exception e){
            e.getStackTrace();
        }
        JSONObject jo2 = new JSONObject();
        try {
            jo2.put("isbn", userData.getAllBooks().get(1));
        }catch (Exception e){
            e.getStackTrace();
        }
        JSONObject jo3 = new JSONObject();
        try {
            jo3.put("isbn", userData.getAllBooks().get(2));
        }catch (Exception e){
            e.getStackTrace();
        }
        array.put(jo1);
        array.put(jo2);
        array.put(jo3);

        JSONObject booksForUser = new JSONObject();
        try{
            booksForUser.put("userId", userData.getUserID());
            booksForUser.put("collectionOfIsbns", array);
        } catch(Exception e){
            e.getStackTrace();
        }
        System.out.println("CHECK user books:  " + booksForUser);

        return booksForUser.toString();

    }

    public static String getBodyForDeleteUser(UserData userData){
//        String bodyForDeleteUser = "{\n" +
//                "  \"isbn\": \"" + userData.getAllBooks().get(0) + "\",\n" +
//                "  \"userId\": \"" + userData.getUserID() + "\"\n" +
//                "}";

        JSONObject deleteBook = new JSONObject();
        try {
            deleteBook.put("isbn", userData.getAllBooks().get(0));
            deleteBook.put("userId", userData.getUserID());
        }catch(Exception e){
            e.getStackTrace();
        }
        return deleteBook.toString();
    }

}
