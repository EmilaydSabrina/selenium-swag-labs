package com.swag_labs.Cenario3_CarrinhoDeCompras;

import com.swag_labs.pages.LoginPage;
import com.swag_labs.pages.ProdutosPage;
import com.swag_labs.pages.CarrinhoPage;
import com.swag_labs.utils.GerenciamentoDriver;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CT04_PersistenciaDeProduto {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private static ProdutosPage produtosPage;
    private static CarrinhoPage carrinhoPage;

    private static String nomeProdutoSelecionado;
    private static String descricaoProdutoSelecionado;
    private static String precoProdutoSelecionado;

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
    @DisplayName("Adicionar produto ao carrinho e validar persistência ao navegar entre páginas")
    public void adicionarProdutoEValidarPersistenciaNoCarrinho() {
        nomeProdutoSelecionado = produtosPage.obterNomePrimeiroProduto();
        descricaoProdutoSelecionado = produtosPage.obterDescricaoPrimeiroProduto();
        precoProdutoSelecionado = produtosPage.obterPrecoPrimeiroProduto();

        produtosPage.adicionarPrimeiroProdutoAoCarrinho();

        Assertions.assertTrue(produtosPage.isBotaoRemoverVisivelPrimeiroProduto(),
            "Botão 'Remover' não apareceu após adicionar produto.");

        Assertions.assertEquals("1", produtosPage.obterQuantidadeItensNoCarrinho(),
            "Ícone do carrinho não mostra quantidade '1'.");

        produtosPage.clicarNomePrimeiroProduto();

        Assertions.assertTrue(produtosPage.isPaginaDeDetalhesVisivel(),
            "Página de detalhes do produto não está visível.");

        produtosPage.clicarBotaoVoltarAosProdutos();

        Assertions.assertTrue(produtosPage.isPaginaProdutosVisivel(),
            "Página de produtos não está visível após voltar.");

        Assertions.assertTrue(produtosPage.isBotaoRemoverVisivelPrimeiroProduto(),
            "Produto não persistiu no carrinho após navegação entre páginas.");

        Assertions.assertEquals("1", produtosPage.obterQuantidadeItensNoCarrinho(),
            "Ícone do carrinho não persiste com quantidade '1' após navegação.");

        carrinhoPage.clicarNoIconeCarrinho();

        Assertions.assertTrue(carrinhoPage.isPaginaCarrinhoVisivel(),
            "Página do carrinho não está visível.");

        Assertions.assertTrue(
            carrinhoPage.isProdutoVisivelNoCarrinho(nomeProdutoSelecionado, descricaoProdutoSelecionado, precoProdutoSelecionado),
            "Produto não está listado no carrinho após navegação.");
    }

    @AfterAll
    public static void tearDown() {
        GerenciamentoDriver.quitDriver();
    }
}

