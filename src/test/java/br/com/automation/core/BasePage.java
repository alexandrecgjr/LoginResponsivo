package br.com.automation.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


public abstract class BasePage {
    
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final JavascriptExecutor js;
    protected final Logger logger;
    
    private static final int DEFAULT_TIMEOUT = 10;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.js = (JavascriptExecutor) driver;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
    

    protected WebElement waitForElementVisible(By locator) {
        try {
            logger.debug("Aguardando elemento visível: {}", locator);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Timeout aguardando elemento visível: {}", locator);
            throw e;
        }
    }
    

    protected WebElement waitForElementClickable(By locator) {
        try {
            logger.debug("Aguardando elemento clicável: {}", locator);
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            logger.error("Timeout aguardando elemento clicável: {}", locator);
            throw e;
        }
    }
    

    protected WebElement waitForElementPresent(By locator) {
        try {
            logger.debug("Aguardando elemento presente: {}", locator);
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Timeout aguardando elemento presente: {}", locator);
            throw e;
        }
    }
    

    protected void fillField(By locator, String text) {
        try {
            WebElement element = waitForElementClickable(locator);
            element.clear();
            element.sendKeys(text);
            logger.debug("Campo preenchido: {} com valor: {}", locator, text);
        } catch (Exception e) {
            logger.error("Erro ao preencher campo: {}", locator, e);
            throw e;
        }
    }
    

    protected void clickElement(By locator) {
        try {
            WebElement element = waitForElementClickable(locator);
            element.click();
            logger.debug("Elemento clicado: {}", locator);
        } catch (Exception e) {
            logger.error("Erro ao clicar no elemento: {}", locator, e);
            throw e;
        }
    }
    

    protected void clickElementWithJS(By locator) {
        try {
            WebElement element = waitForElementPresent(locator);
            js.executeScript("arguments[0].click();", element);
            logger.debug("Elemento clicado com JavaScript: {}", locator);
        } catch (Exception e) {
            logger.error("Erro ao clicar no elemento com JavaScript: {}", locator, e);
            throw e;
        }
    }
    

    protected String getElementText(By locator) {
        try {
            WebElement element = waitForElementVisible(locator);
            String text = element.getText().trim();
            logger.debug("Texto obtido do elemento {}: {}", locator, text);
            return text;
        } catch (Exception e) {
            logger.error("Erro ao obter texto do elemento: {}", locator, e);
            throw e;
        }
    }
    

    protected boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)) != null;
        } catch (TimeoutException | NoSuchElementException e) {
            logger.debug("Elemento não está visível: {}", locator);
            return false;
        }
    }
    

    protected void navigateTo(String url) {
        try {
            driver.get(url);
            logger.info("Navegando para: {}", url);
        } catch (Exception e) {
            logger.error("Erro ao navegar para: {}", url, e);
            throw e;
        }
    }
    

    protected String getPageTitle() {
        return driver.getTitle();
    }
    

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    

    protected void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            logger.debug("Aguardou {} segundos", seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Interrupção durante espera", e);
        }
    }
} 