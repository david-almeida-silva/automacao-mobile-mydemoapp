package automacao.projetos.portifolio;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class RevisaoPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public RevisaoPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void confirmarPedido() {
        // Clica no botão "Place Order" (Confirmar Pedido)
        // Nota: O My Demo App costuma reutilizar o ID 'paymentBtn' para o botão final nesta tela
        wait.until(ExpectedConditions.elementToBeClickable(
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/paymentBtn"))).click();
    }

    public String obterMensagemSucesso() {
        // Aguarda a tela de sucesso carregar e captura a mensagem "Checkout Complete"
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/completeTV"))).getText();
    }
    
    public void continueShopping() {
        // Clica no botão "Continue Shopping" 
        // Nota: O My Demo App costuma reutilizar o ID 'paymentBtn' para o botão final nesta tela
        wait.until(ExpectedConditions.elementToBeClickable(
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/shoopingBt"))).click();
    }
    
    public String obterMensagemProdutos() {
        // Aguarda a tela de sucesso carregar e captura a mensagem "Products"
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV"))).getText();
    
    } 
}