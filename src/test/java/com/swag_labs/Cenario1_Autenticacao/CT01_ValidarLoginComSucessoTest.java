package com.swag_labs.Cenario1_Autenticacao;

import com.swag_labs.utils.GerenciamentoDriver;
import com.swag_labs.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CT01_ValidarLoginComSucessoTest {

    private static LoginPage loginPage;

    @BeforeAll
    public static void setup() {
        WebDriver driver = GerenciamentoDriver.getDriver("chrome");
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Deve realizar login com usuário válido")
    public void deveRealizarLoginComSucesso() {
        loginPage.acessarPagina();
        loginPage.preencherUsuario("standard_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        assertTrue(loginPage.isPaginaDeProdutosVisivel(), 
            "O login falhou ou a página de produtos não foi carregada.");
    }

    @AfterAll
    public static void tearDown() {
        GerenciamentoDriver.quitDriver();
    }
}
