package br.com.automation.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BrowserConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(BrowserConfig.class);
    
    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE
    }
    
    private static BrowserType browserType = BrowserType.CHROME;
    

    public static void setBrowserType(BrowserType type) {
        browserType = type;
        logger.info("Navegador configurado para: {}", type);
    }

    public static BrowserType getBrowserType() {
        return browserType;
    }
    

    public static WebDriver createDriver() {
        logger.info("Criando driver para navegador: {}", browserType);
        
        switch (browserType) {
            case CHROME:
                return createChromeDriver();
            case FIREFOX:
                return createFirefoxDriver();
            case EDGE:
                return createEdgeDriver();
            default:
                logger.warn("Navegador não reconhecido, usando Chrome como padrão");
                return createChromeDriver();
        }
    }
    

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        // Configurações para melhor performance e estabilidade
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        
        // Configurações para execução headless (opcional)
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless");
        }
        
        return new ChromeDriver(options);
    }
    

    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        options.addArguments("--start-maximized");
        
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless");
        }
        
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless");
        }
        
        return new EdgeDriver(options);
    }
    

    public static void setBrowserFromSystemProperty() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        
        switch (browser) {
            case "firefox":
                setBrowserType(BrowserType.FIREFOX);
                break;
            case "edge":
                setBrowserType(BrowserType.EDGE);
                break;
            case "chrome":
            default:
                setBrowserType(BrowserType.CHROME);
                break;
        }
    }
} 