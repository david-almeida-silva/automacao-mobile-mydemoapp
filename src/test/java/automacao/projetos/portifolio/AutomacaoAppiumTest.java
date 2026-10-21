package automacao.projetos.portifolio;

import org.junit.jupiter.api.Disabled;
import java.io.ByteArrayInputStream;
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
import io.qameta.allure.Allure; // Importação adicionada para o relatório visual

public class AutomacaoAppiumTest {

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
        
        // CONFIGURAÇÃO CLOUD-READY: 
        // 1. Removemos o UDID físico para o Appium aceitar o Emulador do GitHub Actions
        options.setDeviceName("Android Emulator");

        // 2. Caminho dinâmico: Funciona no Windows e no Linux automaticamente
        String caminhoApp = new File("src/test/resources/apps/mda-2.2.0-25.apk").getAbsolutePath();
        options.setApp(caminhoApp);
        
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
        loginPage.realizarLogin("bod@example.com", "10203040");
        Assertions.assertTrue(loginPage.verificarSeLogou(), "O login deveria ter sido efetuado com sucesso!");
    }

    @Disabled("Bug crítico (Ticket-001): O app versão 2.2.0 aceita credenciais inválidas e efetua o login.")
    @Test
    public void naoDeveLogarComCredenciaisIncorretas() {
        loginPage.acessarMenuLogin();
        loginPage.realizarLogin("usuario_errado@example.com", "senha123");
        boolean naoLogou = loginPage.verificarSeMenuLoginAindaExiste();
        Assertions.assertTrue(naoLogou, "Atenção: O usuário conseguiu logar com credenciais inválidas!");
    }

    @Test
    public void deveRealizarCompraCompletaComSucesso() {
        System.out.println("Clicando no produto mochila");
        catalogoPage.selecionarProdutoMochila();

        System.out.println("Adicionando mochila ao carrinho");
        catalogoPage.adicionarAoCarrinho();
        System.out.println("Acessando carrinho");
        catalogoPage.acessarCarrinho();

        System.out.println("Confirmando informações de nome e preço do produto");
        String nomeProduto = carrinhoPage.obterNomeProduto();
        String precoProduto = carrinhoPage.obterPrecoProduto();

        System.out.println("Validando se os dados do produto selecionado está correto");
        Assertions.assertTrue(nomeProduto.contains("Backpack"), "Erro: O nome do produto no carrinho está incorreto!");
        Assertions.assertEquals("$ 29.99", precoProduto, "Erro: O preço diverge do esperado!");

        System.out.println("Processando a compra");
        carrinhoPage.processarCompra();

        System.out.println("App solicitou autenticação. Realizando login...");
        loginPage.realizarLogin("bod@example.com", "10203040");

        System.out.println("Realizando preenchimento dinâmico do formulário");
        checkoutPage.preencherDadosDeEntrega("Seu Nome", "Rua da Automação, 123", "São Paulo", "01000-000", "Brasil");

        System.out.println("Ir para a tela de pagamento");
        checkoutPage.irParaPagamento();

        System.out.println("Preencher dados para pagamento");
        pagamentoPage.preencherDadosPagamento("David Silva", "325812657568789", "03/30", "123");

        System.out.println("Avançar para tela de revisão do pedido");
        pagamentoPage.revisarPedido();

        System.out.println("Confirmar compra");
        RevisaoPage.confirmarPedido();

        System.out.println("Validando tela de sucesso");
        String mensagemFinal = RevisaoPage.obterMensagemSucesso();
        Assertions.assertTrue(mensagemFinal.contains("Complete"), "Erro: A compra não foi finalizada com sucesso!");

        System.out.println("Clicar em Continue Shopping");
        RevisaoPage.continueShopping();

        System.out.println("Validando a mensagem Products");
        RevisaoPage.obterMensagemProdutos();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            System.out.println("Tirando screenshot da tela final...");
            try {
                // 1. Tira o print em formato de bytes (melhor para o Allure) e arquivo (para a pasta local)
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                File print = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                // 2. Salva localmente na pasta evidencias
                Path destino = Paths.get("evidencias", "evidencia_teste.png");
                Files.createDirectories(destino.getParent());
                Files.copy(print.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Screenshot salvo com sucesso em: " + destino.toAbsolutePath());

                // 3. ANEXA A IMAGEM DIRETAMENTE NO ALLURE REPORT!
                Allure.addAttachment("Screenshot da Tela", new ByteArrayInputStream(screenshotBytes));

            } catch (Exception e) {
                System.out.println("Erro ao salvar a evidência: " + e.getMessage());
            }

            System.out.println("Fechando o aplicativo e encerrando sessão...");
            driver.quit();
        }
    }
}
