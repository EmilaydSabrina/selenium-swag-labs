package com.swag_labs.Cenario3_CarrinhoDeCompras;

import com.swag_labs.pages.LoginPage;
import com.swag_labs.pages.ProdutosPage;
import com.swag_labs.pages.CarrinhoPage;
import com.swag_labs.utils.GerenciamentoDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CT03_ValidarRemocaoDeProdutoTest {

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
    @DisplayName("Adicionar produto ao carrinho e validar atualizações do botão e do ícone")
    public void adicionarProdutoAoCarrinhoEValidar() {
        nomeProdutoSelecionado = produtosPage.obterNomePrimeiroProduto();
        descricaoProdutoSelecionado = produtosPage.obterDescricaoPrimeiroProduto();
        precoProdutoSelecionado = produtosPage.obterPrecoPrimeiroProduto();

        produtosPage.adicionarPrimeiroProdutoAoCarrinho();

        Assertions.assertTrue(produtosPage.isBotaoRemoverVisivelPrimeiroProduto(),
            "O botão 'Remover' não foi exibido após adicionar o produto.");

        Assertions.assertEquals("1", produtosPage.obterQuantidadeItensNoCarrinho(),
            "O ícone do carrinho não foi atualizado para '1' após adicionar o produto.");
    }

    @Test
    @Order(2)
    @DisplayName("Validar item listado no carrinho")
    public void validarItemNoCarrinho() {
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

    @Test
    @Order(3)
    @DisplayName("Remover produto do carrinho e validar atualizações do botão e do ícone")
    public void removerProdutoDoCarrinhoEValidar() {
        if (!loginPage.isPaginaDeProdutosVisivel()) {
            driver.navigate().to("https://www.saucedemo.com/inventory.html");
            Assertions.assertTrue(loginPage.isPaginaDeProdutosVisivel(), "Falha ao navegar para a página de produtos.");
        }

        produtosPage.removerPrimeiroProdutoDoCarrinho();

        Assertions.assertFalse(produtosPage.isBotaoRemoverVisivelPrimeiroProduto(),
            "O botão 'Remover' ainda está visível após remoção.");

        String quantidadeNoCarrinho = produtosPage.obterQuantidadeItensNoCarrinho();
        Assertions.assertTrue(quantidadeNoCarrinho.equals("0") || quantidadeNoCarrinho.isEmpty(),
            "O ícone do carrinho não foi atualizado para '0' ou removido após remover o produto.");

        carrinhoPage.clicarNoIconeCarrinho();

        Assertions.assertTrue(carrinhoPage.isPaginaCarrinhoVisivel(),
            "Não foi possível acessar a página do carrinho após remoção.");

        Assertions.assertEquals(0, carrinhoPage.obterQuantidadeItensNoCarrinho(),
            "O carrinho não está vazio após remover o produto.");

        Assertions.assertFalse(
            carrinhoPage.isProdutoVisivelNoCarrinho(nomeProdutoSelecionado, descricaoProdutoSelecionado, precoProdutoSelecionado),
            "Produto ainda está listado no carrinho após remoção."
        );
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
