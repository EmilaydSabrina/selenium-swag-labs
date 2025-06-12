package com.swag_labs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By inputFirstName = By.id("first-name");
    private By inputLastName = By.id("last-name");
    private By inputPostalCode = By.id("postal-code");
    private By botaoContinue = By.id("continue");
    private By botaoCancel = By.id("cancel");

    private By nomeProduto = By.className("inventory_item_name");
    private By precoProduto = By.className("inventory_item_price");
    private By quantidadeProduto = By.className("cart_quantity");

    private By valorSubtotal = By.className("summary_subtotal_label");
    private By valorImposto = By.className("summary_tax_label");
    private By valorTotal = By.className("summary_total_label");

    private By botaoFinish = By.id("finish");
    private By mensagemObrigado = By.className("complete-header");
    private By mensagemDespacho = By.className("complete-text");
    private By botaoBackHome = By.id("back-to-products");


    private By mensagemErro = By.cssSelector("h3[data-test='error']");

    private By paginaResumo = By.cssSelector(".checkout_summary_container");

    public void preencherDadosCheckout(String firstName, String lastName, String zipCode) {
        driver.findElement(inputFirstName).clear();
        driver.findElement(inputFirstName).sendKeys(firstName);

        driver.findElement(inputLastName).clear();
        driver.findElement(inputLastName).sendKeys(lastName);

        driver.findElement(inputPostalCode).clear();
        driver.findElement(inputPostalCode).sendKeys(zipCode);
    }

    public void clicarBotaoContinue() {
        driver.findElement(botaoContinue).click();
    }

    public void clicarBotaoCancel() {
        driver.findElement(botaoCancel).click();
    }

    public String obterNomeProdutoResumo() {
        return driver.findElement(nomeProduto).getText().trim();
    }

    public String obterPrecoProdutoResumo() {
        return driver.findElement(precoProduto).getText().trim();
    }

    public String obterQuantidadeProdutoResumo() {
        return driver.findElement(quantidadeProduto).getText().trim();
    }

    public String obterValorSubtotal() {
        return driver.findElement(valorSubtotal).getText().trim();
    }

    public String obterValorImposto() {
        return driver.findElement(valorImposto).getText().trim();
    }

    public String obterValorTotal() {
        return driver.findElement(valorTotal).getText().trim();
    }

    public void clicarBotaoFinish() {
        driver.findElement(botaoFinish).click();
    }

    public boolean isPaginaCheckoutCompletoVisivel() {
        return driver.findElement(mensagemObrigado).getText().contains("Thank you for your order!");
    }

    public boolean isMensagemDespachoVisivel() {
        return driver.findElement(mensagemDespacho).getText().contains("Your order has been dispatched");
    }

    public void clicarBotaoBackHome() {
        driver.findElement(botaoBackHome).click();
    }

    
    public boolean isMensagemErroVisivel() {
        try {
            String textoErro = driver.findElement(mensagemErro).getText();
            return textoErro != null && !textoErro.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isPaginaResumoVisivel() {
        try {
            return driver.findElement(paginaResumo).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    
    public boolean isPaginaCheckoutVisivel() {
        try {
            
            WebElement container = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("checkout_info_container"))
            );
            return container.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean mensagemErroVisivel(String mensagemEsperada) {
        try {
            WebElement erro = wait.until(
                ExpectedConditions.visibilityOfElementLocated(mensagemErro)
            );
            String textoErro = erro.getText().trim();
            return textoErro.contains(mensagemEsperada);
        } catch (Exception e) {
            return false;
        }
    }

}
