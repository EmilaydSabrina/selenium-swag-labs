package com.swag_labs.Cenario2_TelaInicialEListagemDeProdutos;

import com.swag_labs.pages.LoginPage;
import com.swag_labs.pages.ProdutosPage;
import com.swag_labs.utils.GerenciamentoDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CT04_ValidarRedirecionamentoVoltarAosProdutosTest {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private static ProdutosPage produtosPage;

    @BeforeAll
    public static void setUp() {
        driver = GerenciamentoDriver.getDriver("chrome");
        driver.manage().window().maximize();

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
    @DisplayName("Validar listagem, redirecionamento e retorno à página de produtos")
    public void validarRedirecionamentoEVoltar() {

        Assertions.assertTrue(produtosPage.todosProdutosValidos(),
            "Algum produto está com dados ausentes ou não está sendo exibido corretamente.");

        String nomeEsperado = produtosPage.obterNomePrimeiroProduto();

        produtosPage.clicarNomePrimeiroProduto();

        Assertions.assertTrue(produtosPage.isPaginaDeDetalhesVisivel(),
            "Página de detalhes do produto não está visível após o clique.");

        String nomeNaPaginaDetalhes = produtosPage.obterNomeProdutoNaPaginaDetalhes();
        Assertions.assertEquals(nomeEsperado, nomeNaPaginaDetalhes,
            "O nome do produto na página de detalhes não corresponde ao nome listado.");


        produtosPage.clicarBotaoVoltarAosProdutos();

        Assertions.assertTrue(produtosPage.isPaginaProdutosVisivel(),
            "Não retornou corretamente à página de listagem de produtos.");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

