package com.swag_labs.Cenario1_Autenticacao;

import com.swag_labs.utils.GerenciamentoDriver;
import com.swag_labs.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CT02_ValidarLoginComSenhaIncorreta {

    private static WebDriver driver;
    private static LoginPage loginPage;

    @BeforeAll
    public static void setup() {
        driver = GerenciamentoDriver.getDriver("chrome"); // Sugestão: usar variável de ambiente
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @Test
    @DisplayName("Não deve realizar login com senha incorreta")
    public void naoDeveRealizarLoginComSenhaIncorreta() {
        loginPage.acessarPagina();
        loginPage.preencherUsuario("standard_user");
        loginPage.preencherSenha("123456");
        loginPage.clicarLogin();

        String mensagemEsperada = "Epic sadface: Username and password do not match any user in this service";
        assertTrue(loginPage.mensagemErroVisivel(mensagemEsperada),
            "A mensagem de erro esperada não foi exibida ou o login foi aceito incorretamente.");
    }

    @AfterAll
    public static void tearDown() {
        GerenciamentoDriver.quitDriver();
    }
}
