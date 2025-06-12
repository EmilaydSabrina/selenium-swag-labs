package com.swag_labs.Cenario4_Checkout;

import com.swag_labs.pages.*;
import com.swag_labs.utils.GerenciamentoDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class CT01_ValidarCheckoutComSucesso {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProdutosPage produtosPage;
    private CarrinhoPage carrinhoPage;
    private CheckoutPage checkoutPage;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        driver = GerenciamentoDriver.getDriver("chrome");
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);
        produtosPage = new ProdutosPage(driver);
        carrinhoPage = new CarrinhoPage(driver);
        checkoutPage = new CheckoutPage(driver);

        loginPage.preencherUsuario("standard_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        boolean produtosVisiveis = wait.until(driver -> produtosPage.isPaginaProdutosVisivel());
        assertTrue(produtosVisiveis, "Falha ao carregar a página de produtos após login.");
    }

    @Test
    public void validarFluxoCheckoutCompleto() {
        assertTrue(produtosPage.todosProdutosValidos(), "Produtos não estão exibidos corretamente.");

        String nomeProduto = produtosPage.obterNomePrimeiroProduto();
        String descricaoProduto = produtosPage.obterDescricaoPrimeiroProduto();
        String precoProduto = produtosPage.obterPrecoPrimeiroProduto();

        produtosPage.adicionarPrimeiroProdutoAoCarrinho();
        assertTrue(produtosPage.isBotaoRemoverVisivelPrimeiroProduto(), "Botão 'Remover' não visível após adicionar.");

        assertEquals("1", produtosPage.obterQuantidadeItensNoCarrinho(), "Ícone do carrinho não foi atualizado.");

        carrinhoPage.clicarNoIconeCarrinho();

        wait.until(driver -> carrinhoPage.isPaginaCarrinhoVisivel());

        assertTrue(carrinhoPage.isPaginaCarrinhoVisivel(), "Página do carrinho não foi carregada.");
        assertEquals(1, carrinhoPage.obterQuantidadeItensNoCarrinho(), "Carrinho não tem 1 item.");
        assertTrue(carrinhoPage.isProdutoVisivelNoCarrinho(nomeProduto, descricaoProduto, precoProduto), "Produto não aparece corretamente no carrinho.");

        carrinhoPage.clicarBotaoCheckout();

        checkoutPage.preencherDadosCheckout("Emilayd", "Silva", "1234567889780");
        checkoutPage.clicarBotaoContinue();

        wait.until(driver -> checkoutPage.obterNomeProdutoResumo().equals(nomeProduto));

        assertEquals(nomeProduto, checkoutPage.obterNomeProdutoResumo(), "Nome do produto no resumo incorreto.");
        assertEquals(precoProduto, checkoutPage.obterPrecoProdutoResumo(), "Preço do produto no resumo incorreto.");
        assertEquals("1", checkoutPage.obterQuantidadeProdutoResumo(), "Quantidade incorreta.");
        assertTrue(checkoutPage.obterValorSubtotal().toLowerCase().contains("item total"), "Subtotal ausente.");
        assertTrue(checkoutPage.obterValorImposto().toLowerCase().contains("tax"), "Imposto ausente.");
        assertTrue(checkoutPage.obterValorTotal().toLowerCase().contains("total"), "Total ausente.");

        checkoutPage.clicarBotaoFinish();

        wait.until(driver -> checkoutPage.isPaginaCheckoutCompletoVisivel());

        assertTrue(checkoutPage.isPaginaCheckoutCompletoVisivel(), "Mensagem de finalização ausente.");
        assertTrue(checkoutPage.isMensagemDespachoVisivel(), "Mensagem de despacho ausente.");

        checkoutPage.clicarBotaoBackHome();

        wait.until(driver -> produtosPage.isPaginaProdutosVisivel());

        assertTrue(produtosPage.isPaginaProdutosVisivel(), "Não redirecionou para a página de produtos.");
        assertEquals("0", produtosPage.obterQuantidadeItensNoCarrinho(), "Carrinho não está vazio.");
    }

    @AfterEach
    public void tearDown() {
        GerenciamentoDriver.quitDriver();
    }
}
