package br.com.automation.pages;

import br.com.automation.core.BasePage;
import org.openqa.selenium.By;


public class LoginPage extends BasePage {
    
    private final By campoUsuario = By.id("username");
    private final By campoSenha = By.id("password");
    private final By botaoLogin = By.cssSelector("button[type='submit']");
    private final By mensagemFlash = By.id("flash");
    private final By tituloPagina = By.cssSelector("h2");
    
    private static final String LOGIN_URL = "https://the-internet.herokuapp.com/login";
    
    public LoginPage(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }
    

    public void acessarPaginaLogin() {
        navigateTo(LOGIN_URL);
        waitForElementPresent(campoUsuario);
        logger.info("Página de login acessada com sucesso");
    }

    public void preencherUsuario(String usuario) {
        fillField(campoUsuario, usuario);
        logger.info("Campo usuário preenchido com: {}", usuario);
    }
    

    public void preencherSenha(String senha) {
        fillField(campoSenha, senha);
        logger.info("Campo senha preenchido");
    }

    public void clicarBotaoLogin() {
        clickElement(botaoLogin);
        logger.info("Botão de login clicado");
    }
    

    public void realizarLogin(String usuario, String senha) {
        preencherUsuario(usuario);
        preencherSenha(senha);
        clicarBotaoLogin();
        logger.info("Login realizado para o usuário: {}", usuario);
    }
    

    public String obterMensagemFlash() {
        String mensagem = getElementText(mensagemFlash);
        logger.info("Mensagem flash obtida: {}", mensagem);
        return mensagem;
    }
    

    public String obterTituloPagina() {
        String titulo = getElementText(tituloPagina);
        logger.info("Título da página: {}", titulo);
        return titulo;
    }
    

    public boolean isPaginaLoginCarregada() {
        boolean carregada = isElementVisible(campoUsuario) && 
                           isElementVisible(campoSenha) && 
                           isElementVisible(botaoLogin);
        logger.debug("Página de login carregada: {}", carregada);
        return carregada;
    }
    

    public boolean isMensagemErroVisivel() {
        boolean erroVisivel = isElementVisible(mensagemFlash);
        if (erroVisivel) {
            String mensagem = obterMensagemFlash();
            logger.info("Mensagem de erro detectada: {}", mensagem);
        }
        return erroVisivel;
    }
    

    public void limparCampos() {
        try {
            driver.findElement(campoUsuario).clear();
            driver.findElement(campoSenha).clear();
            logger.debug("Campos de login limpos");
        } catch (Exception e) {
            logger.error("Erro ao limpar campos", e);
        }
    }
} 