package com.swag_labs.Cenario1_Autenticacao;

import com.swag_labs.utils.GerenciamentoDriver;
import com.swag_labs.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CT03_ValidarLoginComUsuarioBloqueado {

    private static LoginPage loginPage;

    @BeforeAll
    public static void setup() {
        WebDriver driver = GerenciamentoDriver.getDriver("chrome");
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Não deve realizar login com usuário bloqueado")
    public void naoDeveLogarComUsuarioBloqueado() {
        loginPage.acessarPagina();
        loginPage.preencherUsuario("locked_out_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        String mensagemErroEsperada = "Epic sadface: Sorry, this user has been locked out.";
        assertTrue(loginPage.mensagemErroVisivel(mensagemErroEsperada),
            "A mensagem de erro esperada não foi exibida ou o login foi aceito incorretamente.");
    }

    @AfterAll
    public static void tearDown() {
        GerenciamentoDriver.quitDriver();
    }
}

