package com.swag_labs.Cenario4_Checkout;

import com.swag_labs.pages.ProdutosPage;
import com.swag_labs.pages.CheckoutPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.*;

public class CT04_ValidarCheckoutSemProdutoTest {

    private WebDriver driver;
    private ProdutosPage produtosPage;
    private CheckoutPage checkoutPage;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        produtosPage = new ProdutosPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void tentarCheckoutSemProdutosNoCarrinho_deveFalhar() {
        String badge = produtosPage.obterQuantidadeItensNoCarrinho();
        int quantidade = badge.isEmpty() ? 0 : Integer.parseInt(badge);
        assertEquals(0, quantidade, "Carrinho já deveria estar vazio antes do teste.");
        produtosPage.clicarNoIconeCarrinho();

        try {
            driver.findElement(By.id("checkout")).click();
            checkoutPage.preencherDadosCheckout("João", "Silva", "12345");
            checkoutPage.clicarBotaoContinue();

            By mensagemErro = By.cssSelector(".error-message-container");

            boolean erroVisivel;
            try {
                erroVisivel = driver.findElement(mensagemErro).isDisplayed();
            } catch (NoSuchElementException e) {
                erroVisivel = false;
            }

            assertTrue(erroVisivel, "Erro esperado: o sistema deveria exibir uma mensagem de erro ao tentar checkout com carrinho vazio.");
        } catch (NoSuchElementException e) {
            fail("Botão de checkout não deveria estar habilitado com carrinho vazio.");
        }
    }
}
