package br.com.automation.steps;

import br.com.automation.core.TestContext;
import br.com.automation.pages.LoginPage;
import br.com.automation.utils.AllureUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginSteps {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);
    private TestContext context;
    private LoginPage loginPage;
    
    @Before
    public void setup(Scenario scenario) {
        logger.info("Iniciando cenário: {}", scenario.getName());
        context = TestContext.getInstance();
        context.inicializarDriver();
        loginPage = context.getLoginPage();
    }
    
    @After
    public void teardown(Scenario scenario) {
        logger.info("Finalizando cenário: {} - Status: {}", 
                   scenario.getName(), 
                   scenario.getStatus());
        
        if (scenario.isFailed()) {
            logger.error("Cenário falhou: {}", scenario.getName());
            AllureUtils.capturarScreenshotErro(context.getDriver(),
                "Falha no cenário: " + scenario.getName());
        } else {
            AllureUtils.captureScreenshot(context.getDriver(),
                "Sucesso - " + scenario.getName());
        }
        
        AllureUtils.anexarInformacoesNavegador(context.getDriver());
        
        context.finalizarDriver();
    }
    
    @Dado("que o usuário acessa a página de login")
    @Step("Acessar página de login")
    public void queOUsuarioAcessaAPaginaDeLogin() {
        Allure.addDescription("Acessa a página de login do sistema");
        loginPage.acessarPaginaLogin();
        Assert.assertTrue("Página de login não foi carregada corretamente", 
                         loginPage.isPaginaLoginCarregada());
        logger.info("Página de login acessada com sucesso");
        
        AllureUtils.captureScreenshot(context.getDriver(), "Página de Login");
    }
    
    @Quando("o usuário preenche o campo usuário com {string}")
    public void oUsuarioPreencheOCampoUsuarioCom(String usuario) {
        loginPage.preencherUsuario(usuario);
        context.setUsuarioAtual(usuario);
        logger.info("Campo usuário preenchido com: {}", usuario);
    }
    
    @Quando("preenche o campo senha com {string}")
    public void preencheOCampoSenhaCom(String senha) {
        loginPage.preencherSenha(senha);
        context.setSenhaAtual(senha);
        logger.info("Campo senha preenchido");
    }
    
    @Quando("clica no botão de login")
    public void clicaNoBotaoDeLogin() {
        loginPage.clicarBotaoLogin();
        logger.info("Botão de login clicado");
    }
    
    @Entao("deve visualizar a mensagem {string}")
    public void deveVisualizarAMensagem(String mensagemEsperada) {
        context.setMensagemEsperada(mensagemEsperada);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String mensagemAtual = loginPage.obterMensagemFlash();
        
        logger.info("Mensagem esperada: {}", mensagemEsperada);
        logger.info("Mensagem atual: {}", mensagemAtual);
        
        Assert.assertTrue("Mensagem não contém o texto esperado",
                         mensagemAtual.contains(mensagemEsperada));
        
        logger.info("Mensagem validada com sucesso");
    }
    
    @Quando("o usuário realiza login com usuário {string} e senha {string}")
    public void oUsuarioRealizaLoginComUsuarioESenha(String usuario, String senha) {
        loginPage.realizarLogin(usuario, senha);
        context.setUsuarioAtual(usuario);
        context.setSenhaAtual(senha);
        logger.info("Login realizado com usuário: {}", usuario);
    }
    
    @Entao("deve ser redirecionado para a área segura")
    public void deveSerRedirecionadoParaAAreaSegura() {
        String urlAtual = loginPage.getCurrentUrl();
        Assert.assertTrue("Não foi redirecionado para área segura",
                         urlAtual.contains("secure"));
        logger.info("Redirecionamento para área segura confirmado");
    }
    
    @Entao("deve permanecer na página de login")
    public void devePermanecerNaPaginaDeLogin() {
        String urlAtual = loginPage.getCurrentUrl();
        Assert.assertTrue("Não permaneceu na página de login",
                         urlAtual.contains("login"));
        logger.info("Permanência na página de login confirmada");
    }
    
    @Entao("deve exibir mensagem de erro")
    public void deveExibirMensagemDeErro() {
        Assert.assertTrue("Mensagem de erro não foi exibida",
                         loginPage.isMensagemErroVisivel());
        logger.info("Mensagem de erro confirmada");
    }
} 