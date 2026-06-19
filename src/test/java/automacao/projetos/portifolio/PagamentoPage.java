package automacao.projetos.portifolio;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class PagamentoPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public PagamentoPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void preencherDadosPagamento(String nomeCartao, String numeroCartao, String validade, String cvv) {
        // Aguarda a tela de pagamento carregar e preenche o Nome
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET"))).sendKeys(nomeCartao);

        // Preenche o Número do Cartão
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cardNumberET")).sendKeys(numeroCartao);
        
        // Preenche a Data de Validade
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/expirationDateET")).sendKeys(validade);
        
        // Preenche o Código de Segurança (CVV)
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/securityCodeET")).sendKeys(cvv);
    }

    public void revisarPedido() {
        // Clica no botão "Review Order" (Revisar Pedido)
        wait.until(ExpectedConditions.elementToBeClickable(
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/paymentBtn"))).click();
    }
}