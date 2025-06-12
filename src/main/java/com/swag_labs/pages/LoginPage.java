package com.swag_labs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void acessarPagina() {
        driver.get("https://www.saucedemo.com/");
    }

    public void preencherUsuario(String usuario) {
        WebElement campoUsuario = driver.findElement(By.id("user-name"));
        campoUsuario.clear();
        campoUsuario.sendKeys(usuario);
    }

    public void preencherSenha(String senha) {
        WebElement campoSenha = driver.findElement(By.id("password"));
        campoSenha.clear();
        campoSenha.sendKeys(senha);
    }

    public void clicarLogin() {
        WebElement botaoLogin = driver.findElement(By.id("login-button"));
        botaoLogin.click();
    }

    public boolean isPaginaDeProdutosVisivel() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean mensagemErroVisivel(String mensagemEsperada) {
        try {
            WebElement erro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
            String textoErro = erro.getText().trim();
            System.out.println("Mensagem de erro capturada: '" + textoErro + "'");
            return textoErro.contains(mensagemEsperada);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPaginaLoginVisivel() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
            return driver.findElement(By.id("login-button")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
