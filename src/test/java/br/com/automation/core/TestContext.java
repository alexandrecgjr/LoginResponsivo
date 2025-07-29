package br.com.automation.core;

import br.com.automation.config.BrowserConfig;
import br.com.automation.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestContext {
    
    private static final Logger logger = LoggerFactory.getLogger(TestContext.class);
    
    private static TestContext instance;
    private WebDriver driver;
    private LoginPage loginPage;
    
    private String usuarioAtual;
    private String senhaAtual;
    private String mensagemEsperada;
    
    private TestContext() {
        logger.info("Inicializando contexto de teste");
    }
    

    public static TestContext getInstance() {
        if (instance == null) {
            instance = new TestContext();
        }
        return instance;
    }
    

    public void inicializarDriver() {
        if (driver == null) {
            driver = BrowserConfig.createDriver();
            loginPage = new LoginPage(driver);
            logger.info("Driver inicializado com sucesso");
        }
    }
    

    public void finalizarDriver() {
        if (driver != null) {
            try {
                driver.quit();
                logger.info("Driver finalizado com sucesso");
            } catch (Exception e) {
                logger.error("Erro ao finalizar driver", e);
            } finally {
                driver = null;
                loginPage = null;
                limparDadosCompartilhados();
            }
        }
    }
    

    public WebDriver getDriver() {
        if (driver == null) {
            inicializarDriver();
        }
        return driver;
    }
    

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            inicializarDriver();
        }
        return loginPage;
    }
    

    public void setUsuarioAtual(String usuario) {
        this.usuarioAtual = usuario;
        logger.debug("Usuário atual definido: {}", usuario);
    }
    

    public String getUsuarioAtual() {
        return usuarioAtual;
    }
    

    public void setSenhaAtual(String senha) {
        this.senhaAtual = senha;
        logger.debug("Senha atual definida");
    }
    

    public String getSenhaAtual() {
        return senhaAtual;
    }
    

    public void setMensagemEsperada(String mensagem) {
        this.mensagemEsperada = mensagem;
        logger.debug("Mensagem esperada definida: {}", mensagem);
    }
    

    public String getMensagemEsperada() {
        return mensagemEsperada;
    }
    

    private void limparDadosCompartilhados() {
        usuarioAtual = null;
        senhaAtual = null;
        mensagemEsperada = null;
        logger.debug("Dados compartilhados limpos");
    }
    

    public void resetarContexto() {
        limparDadosCompartilhados();
        logger.info("Contexto de teste resetado");
    }
} 