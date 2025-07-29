package br.com.automation;

import br.com.automation.config.BrowserConfig;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"br.com.automation.steps"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/report.html",
        "json:target/cucumber-reports/report.json",
        "junit:target/cucumber-reports/report.xml"
    },
    monochrome = true,
    snippets = io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE,
    tags = "not @ignored"
)
public class TestRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);
    
    @BeforeClass
    public static void setup() {
        logger.info("=== INICIANDO EXECUÇÃO DOS TESTES ===");
        
        BrowserConfig.setBrowserFromSystemProperty();
        
        String browser = System.getProperty("browser", "chrome");
        logger.info("Navegador configurado: {}", browser.toUpperCase());
        
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            logger.info("Execução em modo headless ativada");
        }
        
        logger.info("=== CONFIGURAÇÃO INICIALIZADA ===");
    }
} 