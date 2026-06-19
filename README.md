# 📱 Automação Mobile - My Demo App (E-commerce)

Este projeto é um portfólio de automação de testes mobile (Android) focado em um aplicativo de E-commerce real (My Demo App da Sauce Labs), desenvolvido para demonstrar conhecimentos avançados em Engenharia de Qualidade (QA).

## 🛠️ Tecnologias Utilizadas
* **Java 17**
* **Appium 2.x** (UiAutomator2)
* **JUnit 5** (Para orquestração e asserções)
* **Selenium WebDriver**

## 🏗️ Arquitetura e Padrões
O projeto foi estruturado utilizando o **Page Object Model (POM)**, garantindo que a lógica de testes fique separada do mapeamento de elementos, facilitando a manutenção e escalabilidade. 

As seguintes estratégias foram aplicadas:
* **Explicit Waits:** Uso de `WebDriverWait` para sincronização inteligente (sem uso de `Thread.sleep`).
* **Seletores Otimizados:** Priorização de buscas nativas com `-android uiautomator` e `accessibilityId` para maior estabilidade e velocidade.
* **Geração de Evidências:** Captura de tela automática (Screenshot) ao final da execução utilizando a interface `TakesScreenshot`.

## 🚀 Cenários Cobertos

1. **Jornada End-to-End (E2E) de Compra:**
   * Navegação pelo catálogo e seleção de produto via `UiSelector`.
   * Adição ao carrinho e validação de valores lidos em tela.
   * Interrupção de fluxo para Login Obrigatório.
   * Preenchimento de formulários complexos (Endereço e Cartão de Crédito).
   * Validação da tela de sucesso ("Checkout Complete").

2. **Testes de Segurança e Autenticação:**
   * Login com sucesso utilizando credenciais válidas.
   * **[BUG ENCONTRADO]** Validação de credenciais incorretas (Teste atualmente desabilitado via `@Disabled`).

## 🐞 Bug Crítico Mapeado na Versão 2.2.0
Durante o desenvolvimento da automação, o script identificou uma falha de segurança na versão `2.2.0` do aplicativo:
* **Comportamento Esperado:** O app deve bloquear o login com dados falsos.
* **Comportamento Atual:** O app aceita qualquer e-mail/senha incorretos, permitindo o login sem validação de backend. O script expôs essa falha e foi temporariamente ignorado para não quebrar a esteira.

## ⚙️ Como executar localmente

1. Clone o repositório.
2. Baixe o aplicativo [My Demo App Android](https://github.com/saucelabs/my-demo-app-android/releases) (Versão 2.2.0 ou superior).
3. Inicie o servidor do **Appium** via terminal (`appium`).
4. Abra a classe `AutomacaoAppium.java` e altere o caminho na linha `options.setApp("SEU_CAMINHO_AQUI")` para o local onde o APK foi salvo na sua máquina.
5. Conecte um emulador ou dispositivo físico e execute a classe como um **JUnit Test**.