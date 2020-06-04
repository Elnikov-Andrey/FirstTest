import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SystemShowMessageClientResaveTDSinExistedOrder {

    @FindBy(xpath = "//td[@tabindex=\"2\"]")
    public WebElement newProduct;

    private ChromeDriver driver;
    //public static String text;


    public static String randomValue(){
        Integer a = (int) (Math.random() * 1000);
        return a.toString();
    }
    public String takeNumberTDS(String text){
        Integer value = Integer.parseInt(text.replaceAll("[^0-9]", ""));
        return value.toString();
    }

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://rc.gelius.dp.ua/login");
    }

    @Test
    public void addToCartTest() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("a--e@meta.ua");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        //---Создаём техкарту
        driver.findElement(By.xpath("//a[@href='/products/newProduct']")).click();
        createTDS();

        //---Создаём заказ
        driver.findElement(By.xpath("//a[@href='/orders/newOrder']")).click();
        createOrder();

        /*driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//span[contains(@class,'price-box')][@data-product-id='244']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='mini-cart']")));
        Assert.assertEquals("1", driver.findElement(By.xpath("//div[@class='minicart-header']//span[@class='counter-number']")).getText());*/
    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }

    public String createTDS(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        //---Тип изделия
        driver.findElement(By.xpath("//select[@id='productType']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='productType']/option[@value='1'][contains(text(), \"Ящик (4 клапана)\")]")));
        driver.findElement(By.xpath("//select[@id='productType']/option[@value='1'][contains(text(), \"Ящик (4 клапана)\")]")).click();
        //---Внутренние размеры
        driver.findElement(By.xpath("//input[@id='innerLength']")).sendKeys(randomValue());
        driver.findElement(By.xpath("//input[@id='innerWidth']")).sendKeys(randomValue());
        driver.findElement(By.xpath("//input[@id='innerHeight']")).sendKeys(randomValue());
        //---Профиль
        driver.findElement(By.xpath("//select[@id='profile']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='profile']/option[@value='1'][contains(text(), \"B\")]")));
        driver.findElement(By.xpath("//select[@id='profile']/option[@value='1'][contains(text(), \"B\")]")).click();
        //---Марка картона
        driver.findElement(By.xpath("//select[@id='cardboardBrand']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='cardboardBrand']/option[@value='1'][contains(text(), \"КГ-4\")]")));
        driver.findElement(By.xpath("//select[@id='cardboardBrand']/option[@value='1'][contains(text(), \"КГ-4\")]")).click();
        //---Лицевой слой
        driver.findElement(By.xpath("//select[@id='faceLayer']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='faceLayer']/option[@value='1'][contains(text(), \"Белый\")]")));
        driver.findElement(By.xpath("//select[@id='faceLayer']/option[@value='1'][contains(text(), \"Белый\")]")).click();
        //---Лицевой слой
        driver.findElement(By.xpath("//select[@id='innerLayer']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='innerLayer']/option[@value='1'][contains(text(), \"Белый\")]")));
        driver.findElement(By.xpath("//select[@id='innerLayer']/option[@value='1'][contains(text(), \"Белый\")]")).click();
        //---Способ упаковки
        driver.findElement(By.xpath("//select[@id='packing']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='packing']/option[@value='1'][contains(text(), \"Без упаковки\")]")));
        driver.findElement(By.xpath("//select[@id='packing']/option[@value='1'][contains(text(), \"Без упаковки\")]")).click();
        driver.findElement(By.xpath("//div[@class='title-header-menu-item']/div/p[contains(text(), \"Сохранить\")]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='alert_component']/h4[@class='text-center'][contains(text(), \"успешно сохранена\")]")));
        WebElement s = driver.findElement(By.xpath("//div[@id='alert_component']/h4[@class='text-center'][contains(text(), \"успешно сохранена\")]"));
        String textFromMessage = s.getText();
        String numberTDS = takeNumberTDS(textFromMessage);
        System.out.println("number " + numberTDS);
        driver.findElement(By.xpath("//div[@class='title-header-menu-item']/div/p[contains(text(), \"В каталог гофропродукции\")]")).click();
        driver.findElement(By.xpath("//a[@href='/index']")).click();
        return numberTDS;
    }

    public void createOrder(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.xpath("//select[@id='deliveryAddress']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='deliveryAddress']/option[contains(text(), \"Самовывоз\")]")));
        driver.findElement(By.xpath("//select[@id='deliveryAddress']/option[contains(text(), \"Самовывоз\")]")).click();
        driver.findElement(By.xpath("//a[@title='Добавить изделие в заказ']")).click();

        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@tabindex=\"2\"]")));
        //driver.findElement(By.xpath("//table[@class='table table-bordered table-hover']/tbody/tr[1]/td[1]")).click();
        //driver.findElement(By.xpath("//a[@title='Добавить изделие в заказ']")).click();
        //Actions action = new Actions(driver);
        //newProduct.click();
        driver.findElement(By.xpath("//a[@title='Добавить изделие в заказ']")).click();
        List<WebElement> massiv = driver.findElements(By.xpath("//div[@class=\"react-bs-container-body\"]/table[@class=\"table table-bordered table-hover\"]/tbody/tr"));
        System.out.println(massiv.size());
        massiv.get(0).click();
        driver.findElement(By.xpath("//a[@title='Добавить изделие в заказ']")).click();
    }
}
