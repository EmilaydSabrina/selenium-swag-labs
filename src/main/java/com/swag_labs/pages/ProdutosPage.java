package com.swag_labs.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ProdutosPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ProdutosPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private By listaDeProdutos = By.className("inventory_item");
    private By nomeProduto = By.className("inventory_item_name");
    private By descricaoProduto = By.className("inventory_item_desc");
    private By precoProduto = By.className("inventory_item_price");
    private By imagemProduto = By.cssSelector(".inventory_item img");
    private By botaoAdicionar = By.xpath(".//button[contains(text(),'Add to cart')]");
    private By botaoRemover = By.xpath(".//button[contains(text(),'Remove')]");
    private By badgeCarrinho = By.className("shopping_cart_badge");
    private By iconeCarrinho = By.className("shopping_cart_link");

    private By tituloDetalhes = By.className("inventory_details_name");
    private By botaoVoltarAosProdutos = By.id("back-to-products");

    private By botaoMenu = By.id("react-burger-menu-btn");
    private By opcaoTodosOsItens = By.id("inventory_sidebar_link");
    private By opcaoLogout = By.id("logout_sidebar_link");

    public boolean todosProdutosValidos() {
        List<WebElement> produtos = driver.findElements(listaDeProdutos);
        if (produtos.size() != 6) return false;

        for (WebElement produto : produtos) {
            boolean nomeVisivel = !produto.findElements(nomeProduto).isEmpty();
            boolean descricaoVisivel = !produto.findElements(descricaoProduto).isEmpty();
            boolean precoVisivel = !produto.findElements(precoProduto).isEmpty();
            boolean imagemVisivel = !produto.findElements(imagemProduto).isEmpty();
            boolean botaoVisivel = !produto.findElements(botaoAdicionar).isEmpty();

            if (!(nomeVisivel && descricaoVisivel && precoVisivel && imagemVisivel && botaoVisivel)) {
                return false;
            }
        }

        return true;
    }

    public boolean isPaginaProdutosVisivel() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(listaDeProdutos));
            return driver.findElements(listaDeProdutos).size() == 6;
        } catch (TimeoutException e) {
            return false;
        }
    }


    public String obterNomePrimeiroProduto() {
        List<WebElement> nomes = driver.findElements(nomeProduto);
        return nomes.isEmpty() ? "" : nomes.get(0).getText().trim();
    }

    public String obterDescricaoPrimeiroProduto() {
        List<WebElement> descricoes = driver.findElements(descricaoProduto);
        return descricoes.isEmpty() ? "" : descricoes.get(0).getText().trim();
    }

    public String obterPrecoPrimeiroProduto() {
        List<WebElement> precos = driver.findElements(precoProduto);
        return precos.isEmpty() ? "" : precos.get(0).getText().trim();
    }

    public void clicarNomePrimeiroProduto() {
        List<WebElement> nomes = driver.findElements(nomeProduto);
        if (!nomes.isEmpty()) nomes.get(0).click();
    }

    public void adicionarPrimeiroProdutoAoCarrinho() {
        List<WebElement> produtos = driver.findElements(listaDeProdutos);
        if (!produtos.isEmpty()) {
            WebElement primeiroProduto = produtos.get(0);
            WebElement botaoAdd = primeiroProduto.findElement(botaoAdicionar);
            botaoAdd.click();
            wait.until(ExpectedConditions.visibilityOf(primeiroProduto.findElement(botaoRemover)));
        }
    }

    public void removerPrimeiroProdutoDoCarrinho() {
        List<WebElement> produtos = driver.findElements(listaDeProdutos);
        if (!produtos.isEmpty()) {
            WebElement primeiroProduto = produtos.get(0);
            WebElement botaoRemoverElemento = primeiroProduto.findElement(botaoRemover);
            botaoRemoverElemento.click();
            wait.until(ExpectedConditions.visibilityOf(primeiroProduto.findElement(botaoAdicionar)));
        }
    }

    public boolean isBotaoRemoverVisivelPrimeiroProduto() {
        List<WebElement> produtos = driver.findElements(listaDeProdutos);
        if (produtos.isEmpty()) return false;
        WebElement primeiroProduto = produtos.get(0);
        return !primeiroProduto.findElements(botaoRemover).isEmpty();
    }

    public boolean isBotaoAdicionarVisivelPrimeiroProduto() {
        List<WebElement> produtos = driver.findElements(listaDeProdutos);
        if (produtos.isEmpty()) return false;
        WebElement primeiroProduto = produtos.get(0);
        return !primeiroProduto.findElements(botaoAdicionar).isEmpty();
    }


    public String obterQuantidadeItensNoCarrinho() {
        try {
            return driver.findElement(badgeCarrinho).getText().trim();
        } catch (NoSuchElementException e) {
            return "0";
        }
    }

    public void clicarNoIconeCarrinho() {
        wait.until(ExpectedConditions.elementToBeClickable(iconeCarrinho)).click();
    }


    public boolean isPaginaDeDetalhesVisivel() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(tituloDetalhes)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String obterNomeProdutoNaPaginaDetalhes() {
        try {
            return driver.findElement(tituloDetalhes).getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public void clicarBotaoVoltarAosProdutos() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoVoltarAosProdutos)).click();
    }


    public void abrirMenuLateral() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(opcaoLogout)); // Garante que o menu carregou
    }

    public void clicarOpcaoTodosOsItens() {
        abrirMenuLateral();
        wait.until(ExpectedConditions.elementToBeClickable(opcaoTodosOsItens)).click();
    }

    public void clicarLogout() {
        abrirMenuLateral();
        wait.until(ExpectedConditions.elementToBeClickable(opcaoLogout)).click();
    }
}
