package automacao.projetos.portifolio;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class LoginPage {
    
    private AndroidDriver driver;
    private WebDriverWait wait;

    // Construtor: Prepara o driver e as esperas inteligentes (Explicit Wait)
    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Métodos de Interação com a Tela
    public void acessarMenuLogin() {
        // Clica no menu hambúrguer
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuIV"))).click();
        
        // O clique no Log In continua igual (se esse for o accessibility ID correto do botão)
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Login Menu Item"))).click();
    }

    public void realizarLogin(String usuario, String senha) {
        // Digita o usuário
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET"))).sendKeys(usuario);
        // Digita a senha
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET"))).sendKeys(senha);
        // Clica no botão de Login
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("com.saucelabs.mydemoapp.android:id/loginBtn"))).click();
    }

    public boolean verificarSeMenuLoginAindaExiste() {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuIV"))).click();
        
        try {
            // Tenta achar o botão de Log In
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId("Login Menu Item"))).isDisplayed();
        } catch (Exception e) {
            // Se der Timeout (ou seja, se o botão virou Log Out), ele cai aqui e retorna FALSO
            return false;
        }
    }
    
    public boolean verificarSeLogou() {
        // Se o login funcionar, o app vai para a tela de produtos
        String tituloTela = wait.until(ExpectedConditions.visibilityOfElementLocated(
            AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV"))).getText(); 
            // 💡 Nota: Já atualizei o seletor do título "Products" para o ID da versão 2.2.0 para o seu outro teste não quebrar!
            
        return tituloTela.equalsIgnoreCase("Products");
    }   
}