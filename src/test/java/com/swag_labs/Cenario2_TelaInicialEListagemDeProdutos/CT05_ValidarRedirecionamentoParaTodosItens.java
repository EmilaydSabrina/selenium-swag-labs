package com.swag_labs.Cenario2_TelaInicialEListagemDeProdutos;

import com.swag_labs.pages.LoginPage;
import com.swag_labs.pages.ProdutosPage;
import com.swag_labs.utils.GerenciamentoDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CT05_ValidarRedirecionamentoParaTodosItens {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private static ProdutosPage produtosPage;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        driver = GerenciamentoDriver.getDriver("chrome");
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);
        produtosPage = new ProdutosPage(driver);

        loginPage.acessarPagina();
        loginPage.preencherUsuario("standard_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        Assertions.assertTrue(loginPage.isPaginaDeProdutosVisivel(),
            "Login falhou ou página de produtos não está visível.");
    }

    @Test
    @DisplayName("Validar os 6 produtos, clicar no nome, abrir menu e selecionar 'All Items'")
    public void validarProdutosENavegacaoPorMenu() {

        Assertions.assertTrue(produtosPage.todosProdutosValidos(),
            "Algum produto está com dados ausentes ou não está sendo exibido corretamente.");

        String nomeEsperado = produtosPage.obterNomePrimeiroProduto();
        produtosPage.clicarNomePrimeiroProduto();

        Assertions.assertTrue(produtosPage.isPaginaDeDetalhesVisivel(),
            "Página de detalhes do produto não está visível após o clique.");

        String nomeNaPaginaDetalhes = produtosPage.obterNomeProdutoNaPaginaDetalhes();
        Assertions.assertEquals(nomeEsperado, nomeNaPaginaDetalhes,
            "O nome do produto na página de detalhes não corresponde ao nome listado.");

    
        fecharMenuLateralSeAberto();


        WebElement btnMenu = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
        btnMenu.click();

        produtosPage.clicarOpcaoTodosOsItens();

        Assertions.assertTrue(produtosPage.todosProdutosValidos(),
            "Não foi redirecionado corretamente para a página com os produtos após selecionar 'All Items'.");
    }

    private void fecharMenuLateralSeAberto() {
        try {
            WebElement menu = driver.findElement(By.className("bm-menu"));
            if (menu.isDisplayed()) {
                WebElement btnClose = driver.findElement(By.id("react-burger-cross-btn"));
                wait.until(ExpectedConditions.elementToBeClickable(btnClose)).click();
                wait.until(ExpectedConditions.invisibilityOf(menu));
            }
        } catch (NoSuchElementException | TimeoutException e) {
            
        }
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
