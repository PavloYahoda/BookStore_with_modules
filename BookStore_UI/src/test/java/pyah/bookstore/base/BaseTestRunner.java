package pyah.bookstore.base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import pyah.bookstore.annotations.TestPage;
import pyah.bookstore.pages.BooksPage;
import pyah.bookstore.pages.HomePage;
import pyah.bookstore.pages.LoginPage;
import pyah.bookstore.pages.RegisterPage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class BaseTestRunner {
    static Playwright playwright;
    static Browser browser;
    static BrowserContext browserContext;
    public Page page;
    @TestPage
    public HomePage homePage;
    public BooksPage booksPage;
    public LoginPage loginPage;
    public RegisterPage registerPage;

    @BeforeAll
    static void launchBrowser(){
        playwright = Playwright.create();
    }
    @BeforeEach
    void createContextAndPage(){
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920,1080));
        page = browserContext.newPage();
        initPage(this, page);
    }

    public  void initPage(Object object, Page page){
        Class<?> pageClass = object.getClass().getSuperclass();
        for(Field field : pageClass.getDeclaredFields()){
            if(field.isAnnotationPresent(TestPage.class)){
                Class<?>[] type = {Page.class};
                try {
                    field.set(this, field.getType().getConstructor(type).newInstance(page));
                } catch(NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException exception){
                    System.out.println("Cannot find constructor for called page" + field.getName());
                }
            }
        }
    }
    @AfterEach
    void closeContext(){
        browserContext.close();;
    }
    @AfterAll
    static void closeBrowser(){
        browser.close();
        playwright.close();
    }
}
