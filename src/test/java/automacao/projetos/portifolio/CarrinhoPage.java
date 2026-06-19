package automacao.projetos.portifolio;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class CarrinhoPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public CarrinhoPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String obterNomeProduto() {
        // Captura o texto com o nome do produto no carrinho
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/titleTV"))).getText();
    }

    public String obterPrecoProduto() {
        // Captura o texto com o preço total do produto no carrinho
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/priceTV"))).getText();
    }
    
    public void processarCompra() {
        // Clica no ícone do carrinho no canto superior direito
        wait.until(ExpectedConditions.elementToBeClickable(
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartBt"))).click();
}
}    