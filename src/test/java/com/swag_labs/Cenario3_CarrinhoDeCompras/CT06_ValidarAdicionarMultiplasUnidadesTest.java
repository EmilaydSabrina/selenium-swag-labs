package com.swag_labs.Cenario3_CarrinhoDeCompras;

import com.swag_labs.pages.LoginPage;
import com.swag_labs.pages.ProdutosPage;
import com.swag_labs.utils.GerenciamentoDriver;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CT06_ValidarAdicionarMultiplasUnidadesTest {

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
    @Order(1)
    @DisplayName("Validar adição de múltiplas unidades do mesmo produto (teste deve falhar pois não permitido)")
    public void validarAdicionarMultiplasUnidadesMesmoProduto() {
        produtosPage.adicionarPrimeiroProdutoAoCarrinho();

        Assertions.assertTrue(produtosPage.isBotaoRemoverVisivelPrimeiroProduto(),
            "Botão 'Remover' não apareceu após primeira adição.");

        boolean botaoAdicionarVisivel = produtosPage.isBotaoAdicionarVisivelPrimeiroProduto();

        Assertions.assertTrue(botaoAdicionarVisivel,
            "Botão 'Adicionar ao carrinho' não está visível para o mesmo produto após já ter sido adicionado - sistema não permite múltiplas unidades!");

        if (botaoAdicionarVisivel) {
            produtosPage.adicionarPrimeiroProdutoAoCarrinho();
        }

        String quantidadeNoCarrinho = produtosPage.obterQuantidadeItensNoCarrinho();
        Assertions.assertEquals("2", quantidadeNoCarrinho,
            "Quantidade no carrinho não é 2 após tentar adicionar o mesmo produto duas vezes.");
    }

    @AfterAll
    public static void tearDown() {
        GerenciamentoDriver.quitDriver();
    }
}
