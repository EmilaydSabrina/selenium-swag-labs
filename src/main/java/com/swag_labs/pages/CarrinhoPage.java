package com.swag_labs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CarrinhoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String TITULO_CARRINHO = "Your Cart";

    private By iconeCarrinho = By.className("shopping_cart_link");
    private By tituloPaginaCarrinho = By.cssSelector(".title");
    private By listaItensCarrinho = By.className("cart_item");
    private By nomeItemCarrinho = By.className("inventory_item_name");
    private By descricaoItemCarrinho = By.className("inventory_item_desc");
    private By precoItemCarrinho = By.className("inventory_item_price");
    private By botaoRemoverItem = By.xpath(".//button[contains(text(),'Remove')]");
    private By botaoCheckout = By.id("checkout");
    private By botaoContinueShopping = By.id("continue-shopping");
    private By mensagemErroCheckout = By.cssSelector("div.error-message-container.error");

    public CarrinhoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clicarNoIconeCarrinho() {
        wait.until(ExpectedConditions.elementToBeClickable(iconeCarrinho)).click();
    }

    public boolean isPaginaCarrinhoVisivel() {
        try {
            WebElement titulo = wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPaginaCarrinho));
            return TITULO_CARRINHO.equalsIgnoreCase(titulo.getText().trim());
        } catch (Exception e) {
            return false;
        }
    }

    public int obterQuantidadeItensNoCarrinho() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body"))); // Garante que a página foi carregada
            List<WebElement> itens = driver.findElements(listaItensCarrinho);
            return itens.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isProdutoVisivelNoCarrinho(String nomeEsperado, String descricaoEsperada, String precoEsperado) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(listaItensCarrinho));
            List<WebElement> itens = driver.findElements(listaItensCarrinho);

            for (WebElement item : itens) {
                String nome = item.findElement(nomeItemCarrinho).getText().trim();
                String descricao = item.findElement(descricaoItemCarrinho).getText().trim();
                String preco = item.findElement(precoItemCarrinho).getText().trim();

                if (nome.equals(nomeEsperado) && descricao.equals(descricaoEsperada) && preco.equals(precoEsperado)) {
                    return true;
                }
            }
        } catch (Exception ignored) {}
        return false;
    }

    public void removerItemDoCarrinho(String nomeProduto) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            List<WebElement> itens = driver.findElements(listaItensCarrinho);

            for (WebElement item : itens) {
                String nome = item.findElement(nomeItemCarrinho).getText().trim();
                if (nome.equals(nomeProduto)) {
                    WebElement botaoRemover = item.findElement(botaoRemoverItem);
                    wait.until(ExpectedConditions.elementToBeClickable(botaoRemover)).click();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao tentar remover item do carrinho: " + e.getMessage());
        }
    }

    public boolean isCarrinhoVazio() {
        return driver.findElements(listaItensCarrinho).isEmpty();
    }

    public void clicarBotaoCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoCheckout)).click();
    }

    public void clicarBotaoContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoContinueShopping)).click();
    }

    public boolean isMensagemErroCheckoutVisivel() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemErroCheckout)) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public String obterMensagemErroCheckout() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemErroCheckout)).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
}
