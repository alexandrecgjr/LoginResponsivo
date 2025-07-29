package br.com.automation;

import br.com.automation.config.BrowserConfig;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runner que executa testes em Chrome e Firefox sequencialmente
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"br.com.automation.steps"},
    plugin = {
        "pretty",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        "html:target/cucumber-reports/report-chrome.html",
        "json:target/cucumber-reports/report-chrome.json",
        "junit:target/cucumber-reports/report-chrome.xml"
    },
    monochrome = true,
    snippets = io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE,
    tags = "not @ignored"
)
public class ChromeFirefoxTestRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(ChromeFirefoxTestRunner.class);
    
    @BeforeClass
    public static void setup() {
        logger.info("=== INICIANDO EXECUÇÃO DOS TESTES EM CHROME E FIREFOX ===");
        
        // Configura o Chrome como primeiro navegador
        BrowserConfig.setBrowserType(BrowserConfig.BrowserType.CHROME);
        logger.info("Navegador configurado: CHROME");
        
        logger.info("=== CONFIGURAÇÃO INICIALIZADA ===");
    }
    
    @AfterClass
    public static void teardown() {
        logger.info("=== EXECUÇÃO NO CHROME FINALIZADA ===");
        
        // Executa os mesmos testes no Firefox
        executeTestsInFirefox();
        
        logger.info("=== TODOS OS TESTES FINALIZADOS ===");
    }
    
    /**
     * Executa os testes no Firefox
     */
    private static void executeTestsInFirefox() {
        try {
            logger.info("=== INICIANDO EXECUÇÃO NO FIREFOX ===");
            
            // Configura o Firefox
            BrowserConfig.setBrowserType(BrowserConfig.BrowserType.FIREFOX);
            logger.info("Navegador configurado: FIREFOX");
            
            // Executa os testes no Firefox usando JUnit
            org.junit.runner.JUnitCore junit = new org.junit.runner.JUnitCore();
            junit.run(FirefoxTestRunner.class);
            
            logger.info("=== EXECUÇÃO NO FIREFOX FINALIZADA ===");
            
        } catch (Exception e) {
            logger.error("Erro ao executar testes no Firefox", e);
        }
    }
}

/**
 * Runner específico para Firefox
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"br.com.automation.steps"},
    plugin = {
        "pretty",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        "html:target/cucumber-reports/report-firefox.html",
        "json:target/cucumber-reports/report-firefox.json",
        "junit:target/cucumber-reports/report-firefox.xml"
    },
    monochrome = true,
    snippets = io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE,
    tags = "not @ignored"
)
class FirefoxTestRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(FirefoxTestRunner.class);
    
    @BeforeClass
    public static void setup() {
        logger.info("=== CONFIGURANDO FIREFOX ===");
        BrowserConfig.setBrowserType(BrowserConfig.BrowserType.FIREFOX);
    }
} 