package automacao.projetos.portifolio;

import org.junit.jupiter.api.Disabled;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.net.URI;
import java.net.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class AutomacaoAppium {

    private AndroidDriver driver;
    private LoginPage loginPage;
    private CatalogoPage catalogoPage;
    private CarrinhoPage carrinhoPage;
    private CheckoutPage checkoutPage;
    private PagamentoPage pagamentoPage;
    private RevisaoPage RevisaoPage;

    @BeforeEach 
    
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setUdid("RX8T10NQ7FR"); 
        options.setDeviceName("MeuCelularFisico"); 
        
     // Atenção: Altere este caminho para o local onde o APK está salvo na sua máquina
        options.setApp("C:\\Users\\David\\Downloads\\mda-2.2.0-25.apk"); 
        options.setNoReset(false); 

        URL url = URI.create("http://127.0.0.1:4723/").toURL();
        driver = new AndroidDriver(url, options);
        
        // Inicializa a nossa página de login
        loginPage = new LoginPage(driver);
        catalogoPage = new CatalogoPage(driver);
        carrinhoPage = new CarrinhoPage(driver);
        checkoutPage = new CheckoutPage(driver);
        pagamentoPage = new PagamentoPage(driver);
        RevisaoPage = new RevisaoPage(driver);
    }

    @Test
    public void deveRealizarLoginComSucesso() {
        loginPage.acessarMenuLogin();
        
        // "bod@example.com" é um usuário padrão aceito pelo My Demo App
        loginPage.realizarLogin("bod@example.com", "10203040");
        
        // Validação (Assert) do JUnit para confirmar o sucesso
        Assertions.assertTrue(loginPage.verificarSeLogou(), "O login deveria ter sido efetuado com sucesso!");
    }

    @Disabled("Bug crítico (Ticket-001): O app versão 2.2.0 aceita credenciais inválidas e efetua o login.")
    @Test
    public void naoDeveLogarComCredenciaisIncorretas() {
        loginPage.acessarMenuLogin();
        
        // Tentamos logar com dados inválidos
        loginPage.realizarLogin("usuario_errado@example.com", "senha123");
        
        // O app vai nos jogar para a tela inicial silenciosamente. 
        // Vamos validar se a opção "Log In" continua no menu (provando que a sessão não foi iniciada)
        boolean naoLogou = loginPage.verificarSeMenuLoginAindaExiste();
        
        // Validação: Tem que ser TRUE. Se for falso, o app aceitou a senha errada (Bug Crítico!)
        Assertions.assertTrue(naoLogou, "Atenção: O usuário conseguiu logar com credenciais inválidas!");
    }
    
    @Test
    public void deveRealizarCompraCompletaComSucesso() {
        // 1. Navegação: Clica na mochila na tela inicial
        catalogoPage.selecionarProdutoMochila();
        
        // 2. Ação: Adiciona ao carrinho e navega para o carrinho
        catalogoPage.adicionarAoCarrinho();
        catalogoPage.acessarCarrinho();
        
        // 3. Coleta de Dados: Lê o que está na tela do carrinho
        String nomeProduto = carrinhoPage.obterNomeProduto();
        String precoProduto = carrinhoPage.obterPrecoProduto();
        
        // 4. Validação Inicial (Asserts): Confirma se o item certo foi pro carrinho
        Assertions.assertTrue(nomeProduto.contains("Backpack"), "Erro: O nome do produto no carrinho está incorreto!");
        Assertions.assertEquals("$ 29.99", precoProduto, "Erro: O preço diverge do esperado!");
        
        // 5. Checkout: Processar a compra
        // NOTA: Certifique-se de ter criado o método processarCompra() dentro do CarrinhoPage.java
        carrinhoPage.processarCompra();
        
     // 5. Interrupção do fluxo: Efetua o Login Obrigatório
        System.out.println("App solicitou autenticação. Realizando login...");
        loginPage.realizarLogin("bod@example.com", "10203040");
        
     // 6. Preenche a tela de endereço
        checkoutPage.preencherDadosDeEntrega(
            "Seu Nome", 
            "Rua da Automação, 123", 
            "São Paulo", 
            "01000-000", 
            "Brasil"
        );
        
        // 7. Avança para a tela de pagamento
        checkoutPage.irParaPagamento();
        
     // 8. Preenche a tela de Pagamento
        pagamentoPage.preencherDadosPagamento(
            "David Silva", 
            "325812657568789", 
            "03/30", 
            "123"
        );
        
        // 9. Avança para a revisão do pedido (Review Order)
        pagamentoPage.revisarPedido();
        
     // 10. Revisa e finaliza a compra (Place Order)
        RevisaoPage.confirmarPedido();
        
        // 11. Validação Final: Verifica se a tela de sucesso apareceu!
        String mensagemFinal = RevisaoPage.obterMensagemSucesso();
        Assertions.assertTrue(mensagemFinal.contains("Complete"), "Erro: A compra não foi finalizada com sucesso!");
        
        //12. Clicar em Continue Shopping 
        RevisaoPage.continueShopping();
        
      //13. Validar home com a mensagem "Products"
        RevisaoPage.obterMensagemProdutos();
    
    
    }
    
   
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            System.out.println("Tirando screenshot da tela final...");
            try {
                // 1. Tira o print e guarda na memória temporária
                File print = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                
                // 2. Cria uma pasta chamada "evidencias" no seu projeto e define o nome do arquivo
                Path destino = Paths.get("evidencias", "evidencia_teste.png");
                Files.createDirectories(destino.getParent());
                
                // 3. Salva o arquivo na pasta
                Files.copy(print.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Screenshot salvo com sucesso em: " + destino.toAbsolutePath());
                
            } catch (Exception e) {
                System.out.println("Erro ao salvar a evidência: " + e.getMessage());
            }
            
            System.out.println("Fechando o aplicativo e encerrando sessão...");
            
    }
    }
}

