package automacao.projetos.portifolio;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class CheckoutPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public CheckoutPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void preencherDadosDeEntrega(String nomeCompleto, String endereco, String cidade, String cep, String pais) {
        // Aguarda a tela de Checkout carregar esperando o primeiro campo ficar visível
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/fullNameET"))).sendKeys(nomeCompleto);

        // Preenche os demais campos (Substitua os IDs abaixo pelos valores corretos do Appium Inspector se necessário)
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/address1ET")).sendKeys(endereco);
        
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cityET")).sendKeys(cidade);
        
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/zipET")).sendKeys(cep);
        
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/countryET")).sendKeys(pais);
    }

    public void irParaPagamento() {
        // Rola a tela para baixo até encontrar o botão de pagamento e então clica
        wait.until(ExpectedConditions.elementToBeClickable(
            AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/paymentBtn\"))"
            )
        )).click();
    }
}