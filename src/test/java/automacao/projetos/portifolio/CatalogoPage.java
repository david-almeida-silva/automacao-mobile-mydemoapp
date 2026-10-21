package automacao.projetos.portifolio;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class CatalogoPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public CatalogoPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selecionarProdutoMochila() {
        // Clica na mochila usando a estratégia nativa do Android (Mais rápido)
    	wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productIV\").instance(0)")
            )).click();
        }

    public void adicionarAoCarrinho() {
        // Clica no botão azul "Add to Cart"
        wait.until(ExpectedConditions.elementToBeClickable(
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartBt"))).click();
    }

    public void acessarCarrinho() {
    // 1. Pausa para a animação do "Add to cart" finalizar
    try { Thread.sleep(1000); } catch (Exception e) {}
    
    // 2. Clica no RelativeLayout (cartRL) que é o botão real, e não apenas na imagem
    wait.until(ExpectedConditions.elementToBeClickable(
        AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartRL"))).click();
        
    // 3. Pausa para aguardar a tela do carrinho carregar completamente
    try { Thread.sleep(1500); } catch (Exception e) {}


    
    
    
        
    }
    
}