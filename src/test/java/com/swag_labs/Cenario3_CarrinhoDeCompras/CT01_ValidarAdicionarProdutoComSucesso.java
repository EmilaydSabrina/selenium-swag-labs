package com.swag_labs.Cenario3_CarrinhoDeCompras;

import com.swag_labs.pages.LoginPage;
import com.swag_labs.pages.ProdutosPage;
import com.swag_labs.pages.CarrinhoPage;
import com.swag_labs.utils.GerenciamentoDriver;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CT01_ValidarAdicionarProdutoComSucesso {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private static ProdutosPage produtosPage;
    private static CarrinhoPage carrinhoPage;

    @BeforeAll
    public static void setUp() {
        driver = GerenciamentoDriver.getDriver("chrome");
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        produtosPage = new ProdutosPage(driver);
        carrinhoPage = new CarrinhoPage(driver);

        loginPage.acessarPagina();
        loginPage.preencherUsuario("standard_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        Assertions.assertTrue(loginPage.isPaginaDeProdutosVisivel(),
            "Login falhou ou página de produtos não está visível.");
    }

    @Test
    @Order(1)
    @DisplayName("Adicionar produto ao carrinho, validar botão, ícone e listagem no carrinho")
    public void adicionarEValidarProdutoNoCarrinhoCompleto() {
        String nomeProdutoSelecionado = produtosPage.obterNomePrimeiroProduto();
        String descricaoProdutoSelecionado = produtosPage.obterDescricaoPrimeiroProduto();
        String precoProdutoSelecionado = produtosPage.obterPrecoPrimeiroProduto();

        produtosPage.adicionarPrimeiroProdutoAoCarrinho();

        Assertions.assertTrue(produtosPage.isBotaoRemoverVisivelPrimeiroProduto(),
            "O botão 'Remover' não foi exibido após adicionar o produto.");

        Assertions.assertEquals("1", produtosPage.obterQuantidadeItensNoCarrinho(),
            "O ícone do carrinho não foi atualizado para '1' após adicionar o produto.");

        carrinhoPage.clicarNoIconeCarrinho();

        Assertions.assertTrue(carrinhoPage.isPaginaCarrinhoVisivel(),
            "Não foi possível acessar a página do carrinho.");

        Assertions.assertEquals(1, carrinhoPage.obterQuantidadeItensNoCarrinho(),
            "Quantidade de itens no carrinho está incorreta.");

        Assertions.assertTrue(
            carrinhoPage.isProdutoVisivelNoCarrinho(nomeProdutoSelecionado, descricaoProdutoSelecionado, precoProdutoSelecionado),
            "Produto listado no carrinho não corresponde ao selecionado."
        );
    }

    @AfterAll
    public static void tearDown() {
        GerenciamentoDriver.quitDriver();
    }
}
