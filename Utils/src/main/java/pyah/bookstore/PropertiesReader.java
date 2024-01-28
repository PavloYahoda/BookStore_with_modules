package pyah.bookstore;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesReader {

    public static String getProperty(String key){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try (InputStream resourceStream = loader.getResourceAsStream("test.properties")) {
            properties.load(resourceStream);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        } return "";
    }

    public static String getPropertyFromFile(String key){
        try (InputStream inputStream = Files.newInputStream(Paths.get("src/test/resources/test.properties"))) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        } return "";
    }
}
