# 📱 Automação Mobile - My Demo App (E-commerce)

[![Testes Mobile](https://github.com/david-almeida-silva/automacao-mobile-mydemoapp/blob/main/.github/workflows/ci-mobile.yml)](https://github.com/david-almeida-silva/automacao-mobile-mydemoapp/actions)
[🔗 Acessar Dashboard do Allure Report](https://david-almeida-silva.github.io/automacao-mobile-mydemoapp/)

Este projeto é um portfólio de automação de testes mobile (Android) focado em um aplicativo de E-commerce real (My Demo App da Sauce Labs), desenvolvido para demonstrar conhecimentos avançados em Engenharia de Qualidade (QA).

## ☁️ Integração Contínua (CI/CD) e Relatório Visual
Este projeto roda em nuvem através do **GitHub Actions**. A cada novo push na branch principal:
1. Uma máquina virtual Ubuntu é provisionada.
2. Um emulador Android nativo (Nexus 6 - API 29) é iniciado via *hardware acceleration* (KVM).
3. O Appium Server sobe em *background*.
4. Os testes E2E são executados e um relatório visual é gerado e publicado automaticamente no GitHub Pages utilizando o **Allure Report**, incluindo *screenshots* em caso de falhas.

## 🛠️ Tecnologias Utilizadas
- **Java 17**
- **Appium 2.x** (UiAutomator2)
- **JUnit 5** (Para orquestração e asserções)
- **Selenium WebDriver**
- **GitHub Actions** (CI/CD e Emulação em Nuvem)
- **Allure Report** (Reporte de Testes e Evidências Visuais)

## 🏗️ Arquitetura e Padrões
O projeto foi estruturado utilizando o **Page Object Model (POM)**, garantindo que a lógica de testes fique separada do mapeamento de elementos, facilitando a manutenção e escalabilidade. 

As seguintes estratégias foram aplicadas:
- **Explicit Waits e Scroll Automático:** Uso de `WebDriverWait` e `UiScrollable` para sincronização inteligente e navegação em telas dinâmicas (sem uso de `Thread.sleep` arbitrários).
- **Seletores Otimizados:** Priorização de buscas nativas com `-android uiautomator`, `xpath` e `accessibilityId` para maior estabilidade.
- **Geração de Evidências:** Captura de tela automática (Screenshot) ao final da execução e anexada diretamente no dashboard do Allure.

## 🚀 Cenários Cobertos

1. **Jornada End-to-End (E2E) de Compra:**
   * Navegação pelo catálogo e seleção de produto via `UiSelector`.
   * Adição ao carrinho e validação de valores lidos em tela.
   * Interrupção de fluxo para Login Obrigatório.
   * Preenchimento de formulários complexos (Endereço e Cartão de Crédito) utilizando scroll nativo.
   * Validação da tela de sucesso ("Checkout Complete").

2. **Testes de Segurança e Autenticação:**
   * Login com sucesso utilizando credenciais válidas.
   * **[BUG ENCONTRADO]** Validação de credenciais incorretas (Teste atualmente desabilitado via `@Disabled`).

## 🐞 Bug Crítico Mapeado na Versão 2.2.0
Durante o desenvolvimento da automação, o script identificou uma falha de segurança na versão `2.2.0` do aplicativo:
- **Comportamento Esperado:** O app deve bloquear o login com dados falsos.
- **Comportamento Atual:** O app aceita qualquer e-mail/senha incorretos, permitindo o login sem validação de backend. O script expôs essa falha e foi temporariamente ignorado para não quebrar a esteira.

## ⚙️ Como executar localmente

1. Clone o repositório.
2. Baixe o aplicativo [My Demo App Android](https://github.com/saucelabs/my-demo-app-android/releases) (Versão 2.2.0 ou superior).
3. Inicie o servidor do **Appium** via terminal (`appium`).
4. Abra a classe `AutomacaoAppiumTest.java` e certifique-se de que o caminho do app nas configurações iniciais aponte para o arquivo `.apk` correto na sua máquina (o projeto já busca na pasta `src/test/resources/apps/`).
5. Conecte um emulador ou dispositivo físico e execute a classe como um **JUnit Test**.