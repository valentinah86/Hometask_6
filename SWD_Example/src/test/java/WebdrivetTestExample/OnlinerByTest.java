package WebdrivetTestExample;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Test
public class OnlinerByTest {

    private static final String BROWSER_PATH = "C:\\Program Files (x86)\\Opera\\launcher.exe";
    private Random random = new Random();

    private OperaOptions options = new OperaOptions().setBinary(BROWSER_PATH);
    private WebDriver driver = new OperaDriver(options);




    @BeforeClass
    public void openWebsite () throws Exception {
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        String url = "http://onliner.by";
        try{
            driver.get(url);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test (enabled = false)
    public void openLoginPage ()  {

        WebElement openLoginPage;
        openLoginPage = driver.findElement(By.className("auth-bar__item"));
        openLoginPage.click();
    }

    @Test (dependsOnMethods = {"openLoginPage"}, enabled = false)
    public void login (){
        String login = "someLogin";
        String password = "somePassword";

        WebElement loginField;
        loginField = driver.findElement(new By.ByCssSelector("#auth-container__forms "));
        loginField.sendKeys(login);

        WebElement passwordField;
        passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(password);
        passwordField.submit();
    }


    @Test
    public void openCatalog() {
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        WebElement element = driver.findElement(By.linkText("Каталог"));
        element.click();
    }

    @Test (dependsOnMethods = {"openCatalog"})
        public void selectCategory (){
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        List<WebElement> categories = driver.findElements(new By.ByXPath("//*[@id=\"container\"]/div/div/div/div/div/ul"));
        WebElement category = categories.get(random.nextInt(categories.size()));
        category.click();
    }

    @Test (dependsOnMethods = {"selectCategory"}) //нужен доп. шаг иногда, иначе тест падает - ПОПРАВИТЬ!!!

    public void selectSubCategory(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        List<WebElement> subcategories = driver.findElements(new By.ByXPath("//*[@id=\"container\"]/div/div/div/div/div/div/div"));
        WebElement subcategory = subcategories.get(random.nextInt(subcategories.size()));
        subcategory.click();
    }

    @Test (dependsOnMethods = "selectSubCategory")
    public void getProduct (){
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        List<WebElement> productsList = driver.findElements(By.className("schema-product__button"));
        WebElement product = productsList.get(random.nextInt(productsList.size()));
        product.click();
    }

    @Test (dependsOnMethods = {"getProduct"})
    public void addProductToBracket() {
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        List<WebElement> offersList = driver.findElements(By.linkText("В корзину"));
        WebElement someOffer = offersList.get(random.nextInt(offersList.size()));
        someOffer.click();
    }


    @Test (enabled = false)
    public void checkBracket () throws Exception {
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);


    }


 @AfterClass
    public void closeBrowser() throws Exception{
        try {
            driver.quit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
