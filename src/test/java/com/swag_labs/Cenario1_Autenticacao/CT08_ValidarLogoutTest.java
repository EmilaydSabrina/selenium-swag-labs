package com.swag_labs.Cenario1_Autenticacao;

import com.swag_labs.pages.LoginPage;
import com.swag_labs.pages.ProdutosPage;
import com.swag_labs.utils.GerenciamentoDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CT08_ValidarLogoutTest {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private static ProdutosPage produtosPage;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setup() {
        driver = GerenciamentoDriver.getDriver("chrome");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        loginPage = new LoginPage(driver);
        produtosPage = new ProdutosPage(driver);

        loginPage.acessarPagina();
        loginPage.preencherUsuario("standard_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        Assertions.assertTrue(loginPage.isPaginaDeProdutosVisivel(), "Login falhou ou página de produtos não visível.");
    }

    @Test
    @Order(1)
    @DisplayName("Validar que todos os 6 produtos estão listados com informações visíveis")
    public void validarProdutosListados() {
        Assertions.assertTrue(produtosPage.todosProdutosValidos(), "Nem todos os produtos estão listados corretamente.");
    }

    @Test
    @Order(2)
    @DisplayName("Realizar logout com sucesso")
    public void validarLogoutComSucesso() {
        produtosPage.clicarLogout();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        Assertions.assertTrue(loginPage.isPaginaLoginVisivel(), "Não retornou para a tela de login após logout.");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
