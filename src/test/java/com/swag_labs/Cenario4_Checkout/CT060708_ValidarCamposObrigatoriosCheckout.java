package com.swag_labs.Cenario4_Checkout;

import com.swag_labs.pages.*;
import com.swag_labs.utils.GerenciamentoDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class CT060708_ValidarCamposObrigatoriosCheckout {

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

        assertTrue(wait.until(d -> produtosPage.isPaginaProdutosVisivel()), "Falha ao carregar página produtos.");

        produtosPage.adicionarPrimeiroProdutoAoCarrinho();
        assertEquals("1", produtosPage.obterQuantidadeItensNoCarrinho(), "Carrinho deve ter 1 item.");

        produtosPage.clicarNoIconeCarrinho();
        assertTrue(wait.until(d -> carrinhoPage.isPaginaCarrinhoVisivel()), "Página carrinho não visível.");

        carrinhoPage.clicarBotaoCheckout();
        assertTrue(wait.until(d -> checkoutPage.isPaginaCheckoutVisivel()), "Página checkout não visível.");
    }

    @Test
    public void validarCamposObrigatoriosCheckoutVazios() {
        checkoutPage.preencherDadosCheckout("", "", "");
        checkoutPage.clicarBotaoContinue();

        assertTrue(checkoutPage.mensagemErroVisivel("Error: First Name is required"), "Erro First Name obrigatório não exibido");

        checkoutPage.preencherDadosCheckout("Emilayd", "", "");
        checkoutPage.clicarBotaoContinue();

        assertTrue(checkoutPage.mensagemErroVisivel("Error: Last Name is required"), "Erro Last Name obrigatório não exibido");

        checkoutPage.preencherDadosCheckout("Emilayd", "Silva", "");
        checkoutPage.clicarBotaoContinue();

        assertTrue(checkoutPage.mensagemErroVisivel("Error: Postal Code is required"), "Erro Postal Code obrigatório não exibido");
    }

    @AfterEach
    public void tearDown() {
        GerenciamentoDriver.quitDriver();
    }
}
