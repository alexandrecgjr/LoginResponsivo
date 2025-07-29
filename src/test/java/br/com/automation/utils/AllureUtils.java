package br.com.automation.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilitários para integração com Allure Report
 */
public class AllureUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(AllureUtils.class);
    
    /**
     * Captura screenshot e anexa ao relatório Allure
     * @param driver WebDriver
     * @param name Nome do screenshot
     */
    public static void captureScreenshot(WebDriver driver, String name) {
        try {
            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
                logger.info("Screenshot capturado: {}", name);
            } else {
                logger.warn("Driver não suporta captura de screenshot");
            }
        } catch (Exception e) {
            logger.error("Erro ao capturar screenshot: {}", name, e);
        }
    }
    
    /**
     * Captura screenshot com timestamp
     * @param driver WebDriver
     * @param description Descrição do screenshot
     */
    public static void captureScreenshotWithTimestamp(WebDriver driver, String description) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String name = String.format("%s_%s", description, timestamp);
        captureScreenshot(driver, name);
    }
    
    /**
     * Anexa texto ao relatório Allure
     * @param name Nome do anexo
     * @param content Conteúdo do texto
     */
    public static void attachText(String name, String content) {
        try {
            Allure.addAttachment(name, "text/plain", content);
            logger.info("Texto anexado: {}", name);
        } catch (Exception e) {
            logger.error("Erro ao anexar texto: {}", name, e);
        }
    }
    
    /**
     * Anexa arquivo ao relatório Allure
     * @param name Nome do anexo
     * @param filePath Caminho do arquivo
     */
    public static void attachFile(String name, String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                byte[] content = Files.readAllBytes(path);
                String mimeType = getMimeType(filePath);
                Allure.addAttachment(name, new ByteArrayInputStream(content));
                logger.info("Arquivo anexado: {} ({})", name, filePath);
            } else {
                logger.warn("Arquivo não encontrado: {}", filePath);
            }
        } catch (IOException e) {
            logger.error("Erro ao anexar arquivo: {}", filePath, e);
        }
    }
    
    /**
     * Anexa logs ao relatório Allure
     * @param logContent Conteúdo dos logs
     */
    public static void attachLogs(String logContent) {
        attachText("Logs de Execução", logContent);
    }
    
    /**
     * Anexa logs ao relatório Allure (em português)
     * @param logContent Conteúdo dos logs
     */
    public static void anexarLogs(String logContent) {
        attachText("Logs de Execução", logContent);
    }
    
    /**
     * Anexa informações do navegador ao relatório Allure
     * @param driver WebDriver
     */
    public static void attachBrowserInfo(WebDriver driver) {
        try {
            StringBuilder info = new StringBuilder();
            info.append("Navegador: ").append(driver.getClass().getSimpleName()).append("\n");
            info.append("URL Atual: ").append(driver.getCurrentUrl()).append("\n");
            info.append("Título: ").append(driver.getTitle()).append("\n");
            if (driver instanceof JavascriptExecutor) {
                info.append("User Agent: ").append(((JavascriptExecutor) driver).executeScript("return navigator.userAgent;")).append("\n");
            }
            
            attachText("Informações do Navegador", info.toString());
        } catch (Exception e) {
            logger.error("Erro ao anexar informações do navegador", e);
        }
    }
    
    /**
     * Anexa informações do navegador ao relatório Allure (em português)
     * @param driver WebDriver
     */
    public static void anexarInformacoesNavegador(WebDriver driver) {
        try {
            StringBuilder info = new StringBuilder();
            info.append("Navegador: ").append(driver.getClass().getSimpleName()).append("\n");
            info.append("URL Atual: ").append(driver.getCurrentUrl()).append("\n");
            info.append("Título da Página: ").append(driver.getTitle()).append("\n");
            if (driver instanceof JavascriptExecutor) {
                info.append("Agente do Usuário: ").append(((JavascriptExecutor) driver).executeScript("return navigator.userAgent;")).append("\n");
            }
            
            attachText("Informações do Navegador", info.toString());
        } catch (Exception e) {
            logger.error("Erro ao anexar informações do navegador", e);
        }
    }
    
    /**
     * Anexa dados de teste ao relatório Allure
     * @param testData Dados do teste
     */
    public static void attachTestData(String testData) {
        attachText("Dados do Teste", testData);
    }
    
    /**
     * Anexa dados de teste ao relatório Allure (em português)
     * @param testData Dados do teste
     */
    public static void anexarDadosTeste(String testData) {
        attachText("Dados do Teste", testData);
    }
    
    /**
     * Anexa configurações ao relatório Allure
     * @param config Configurações
     */
    public static void attachConfiguration(String config) {
        attachText("Configurações", config);
    }
    
    /**
     * Anexa configurações ao relatório Allure (em português)
     * @param config Configurações
     */
    public static void anexarConfiguracoes(String config) {
        attachText("Configurações do Sistema", config);
    }
    
    /**
     * Determina o MIME type baseado na extensão do arquivo
     * @param filePath Caminho do arquivo
     * @return MIME type
     */
    private static String getMimeType(String filePath) {
        String extension = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "json":
                return "application/json";
            case "xml":
                return "application/xml";
            case "html":
                return "text/html";
            case "css":
                return "text/css";
            case "js":
                return "application/javascript";
            case "txt":
                return "text/plain";
            case "log":
                return "text/plain";
            case "properties":
                return "text/plain";
            default:
                return "application/octet-stream";
        }
    }
    
    /**
     * Anexa screenshot com informações do teste
     * @param driver WebDriver
     * @param testName Nome do teste
     * @param stepName Nome do passo
     */
    public static void captureTestStepScreenshot(WebDriver driver, String testName, String stepName) {
        String screenshotName = String.format("%s - %s", testName, stepName);
        captureScreenshotWithTimestamp(driver, screenshotName);
    }
    
    /**
     * Anexa screenshot de erro
     * @param driver WebDriver
     * @param errorMessage Mensagem de erro
     */
    public static void captureErrorScreenshot(WebDriver driver, String errorMessage) {
        String screenshotName = String.format("ERRO - %s", errorMessage);
        captureScreenshotWithTimestamp(driver, screenshotName);
        attachText("Detalhes do Erro", errorMessage);
    }
    
    /**
     * Anexa screenshot de erro (em português)
     * @param driver WebDriver
     * @param errorMessage Mensagem de erro
     */
    public static void capturarScreenshotErro(WebDriver driver, String errorMessage) {
        String screenshotName = String.format("FALHA - %s", errorMessage);
        captureScreenshotWithTimestamp(driver, screenshotName);
        attachText("Detalhes da Falha", errorMessage);
    }
} 