package com.swag_labs.Cenario4_Checkout;

import com.swag_labs.pages.*;
import com.swag_labs.utils.GerenciamentoDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class CT02_ValidarCancelamentoCheckout {

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

        assertTrue(wait.until(driver -> produtosPage.isPaginaProdutosVisivel()), "Falha ao carregar a página de produtos.");

        produtosPage.adicionarPrimeiroProdutoAoCarrinho();
        assertTrue(produtosPage.isBotaoRemoverVisivelPrimeiroProduto(), "Botão 'Remover' não visível após adicionar.");
        assertEquals("1", produtosPage.obterQuantidadeItensNoCarrinho(), "Carrinho não atualizado.");

        carrinhoPage.clicarNoIconeCarrinho();
        assertTrue(wait.until(driver -> carrinhoPage.isPaginaCarrinhoVisivel()), "Falha ao carregar a página do carrinho.");

        carrinhoPage.clicarBotaoCheckout();

        checkoutPage.preencherDadosCheckout("Emilayd", "Silva", "1234567889780");
    }

    @Test
    public void validarResumoECancelamentoCheckout() {
        checkoutPage.clicarBotaoContinue();

        wait.until(driver -> checkoutPage.obterNomeProdutoResumo() != null && !checkoutPage.obterNomeProdutoResumo().isEmpty());

        assertFalse(checkoutPage.obterNomeProdutoResumo().isEmpty(), "Nome do produto no resumo ausente.");
        assertFalse(checkoutPage.obterPrecoProdutoResumo().isEmpty(), "Preço do produto no resumo ausente.");
        assertEquals("1", checkoutPage.obterQuantidadeProdutoResumo(), "Quantidade incorreta no resumo.");
        assertTrue(checkoutPage.obterValorSubtotal().toLowerCase().contains("item total"), "Subtotal ausente no resumo.");
        assertTrue(checkoutPage.obterValorImposto().toLowerCase().contains("tax"), "Imposto ausente no resumo.");
        assertTrue(checkoutPage.obterValorTotal().toLowerCase().contains("total"), "Total ausente no resumo.");

        checkoutPage.clicarBotaoCancel();

        wait.until(driver -> produtosPage.isPaginaProdutosVisivel());

        assertTrue(produtosPage.isPaginaProdutosVisivel(), "Não redirecionou para a página de produtos após cancelar checkout.");
        assertEquals("1", produtosPage.obterQuantidadeItensNoCarrinho(), "Produto não permaneceu no carrinho após cancelar.");
    }

    @AfterEach
    public void tearDown() {
        GerenciamentoDriver.quitDriver();
    }
}

