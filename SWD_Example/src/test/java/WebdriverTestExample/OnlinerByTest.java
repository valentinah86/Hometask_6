package WebdriverTestExample;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Test
public class OnlinerByTest {

    private static final String BROWSER_PATH = "C:\\Program Files (x86)\\Opera\\launcher.exe";
    private Random random = new Random();

    private OperaOptions options = new OperaOptions().setBinary(BROWSER_PATH);
    private WebDriver driver = new OperaDriver(options);


    @BeforeClass
    public void openWebsite () throws Exception {
        try{
            driver.get("http://onliner.by");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void openLoginPage ()  {

        WebElement openLoginPage;
        openLoginPage = driver.findElement(By.className("auth-bar__item"));
        openLoginPage.click();
    }

    @Test (dependsOnMethods = {"openLoginPage"})
    public void login (){

        String login = "SOME_USERNAME";    //need to be changed to correct value
        String password = "SOME_PASSWORD"; //need to be changed to correct value

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement loginField = driver.findElement(new By.ByCssSelector("#auth-container__forms > div > div.auth-box__field > form > div:nth-child(1) > div:nth-child(1) > input"));
        loginField.click();
        loginField.sendKeys(login);

        WebElement passwordField;
        passwordField = driver.findElement(new By.ByCssSelector("#auth-container__forms > div > div.auth-box__field > form > div:nth-child(1) > div:nth-child(2) > input"));
        passwordField.click();
        passwordField.sendKeys(password);

        WebElement submitButton = driver.findElement(new By.ByCssSelector("#auth-container__forms > div > div.auth-box__field > form > div:nth-child(3) > div > button"));
        submitButton.click();

    }

    @Test (dependsOnMethods = {"login"})
    public void openCatalog() {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        WebElement element = driver.findElement(By.linkText("Каталог"));
        element.click();
    }

    @Test (dependsOnMethods = {"openCatalog"})
        public void selectCategory (){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> categories = driver.findElements(new By.ByXPath("//*[@id=\"container\"]/div/div/div/div/div/ul"));
        WebElement category = categories.get(random.nextInt(categories.size()));
        category.click();
    }

    @Test (dependsOnMethods = {"selectCategory"})

    public void selectSubCategory(){
        String XPath = "//*[@id=\"container\"]/div/div/div/div/div/div/div/div/div/div";
        List<WebElement> subcategories = driver.findElements(new By.ByXPath(XPath));
        List<WebElement> visibleSubcategories = subcategories.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
        WebElement subcategory = visibleSubcategories.get(random.nextInt(visibleSubcategories.size()));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        subcategory.click();

        List<WebElement> productsList = driver.findElements(By.className("schema-product__button"));
        if (productsList.size() == 0) {
            List<WebElement> frame = driver.findElements(new By.ByClassName("catalog-navigation-list__dropdown-list"));
            List<WebElement> visibleFrame = frame.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
            WebElement categories = visibleFrame.get(random.nextInt(visibleFrame.size()));
            categories.click();
        }
    }

    @Test (dependsOnMethods = "selectSubCategory")
    public void getProduct (){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            List<WebElement> productsList = driver.findElements(By.className("schema-product__button"));
            WebElement product = productsList.get(random.nextInt(productsList.size()));
            product.click();
    }

    @Test (dependsOnMethods = {"getProduct"})
    public void addProductToBracket() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> offersList = driver.findElements(By.linkText("В корзину"));
        WebElement someOffer = offersList.get(random.nextInt(offersList.size()));
        someOffer.click();
    }

    @Test (dependsOnMethods ={"addProductToBracket"})
    public void openBracket () {
        WebElement bracket = driver.findElement(new By.ByLinkText("Корзина"));
        bracket.click();
    }

    @Test (dependsOnMethods = {"openBracket"})
    public void checkBracket (){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> products = driver.findElements(new By.ByClassName("cart-panel"));
        Assert.assertEquals(1, products.size());
    }

    @AfterClass
    public void closeBrowser() {
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.quit();
    }

}
