package com.swag_labs.Cenario2_TelaInicialEListagemDeProdutos;

import com.swag_labs.pages.LoginPage;
import com.swag_labs.pages.ProdutosPage;
import com.swag_labs.utils.GerenciamentoDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CT01_ValidarListagemComSucesso {

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
    @DisplayName("Validar que os 6 produtos estão listados com nome, preço, imagem e botão de adicionar")
    public void validarProdutosCompletos() {
        Assertions.assertTrue(produtosPage.todosProdutosValidos(),
            "Algum produto está com dados ausentes ou não está sendo exibido corretamente.");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
