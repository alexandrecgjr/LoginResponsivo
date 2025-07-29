package br.com.automation.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class TestDataManager {
    
    private static final Logger logger = LoggerFactory.getLogger(TestDataManager.class);
    private static final String TEST_DATA_FILE = "configs/data/testData.json";
    
    private static TestDataManager instance;
    private JsonNode testData;
    private Map<String, Object> runtimeData;
    
    private TestDataManager() {
        loadTestData();
        runtimeData = new HashMap<>();
    }
    

    public static TestDataManager getInstance() {
        if (instance == null) {
            instance = new TestDataManager();
        }
        return instance;
    }
    

    private void loadTestData() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TEST_DATA_FILE)) {
            if (inputStream != null) {
                ObjectMapper mapper = new ObjectMapper();
                testData = mapper.readTree(inputStream);
                logger.info("Dados de teste carregados com sucesso");
            } else {
                logger.warn("Arquivo de dados de teste não encontrado: {}", TEST_DATA_FILE);
                testData = null;
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar dados de teste", e);
            testData = null;
        }
    }
    

    public String getTestData(String key) {
        if (testData != null && testData.has(key)) {
            String value = testData.get(key).asText();
            logger.debug("Dado de teste obtido: {} = {}", key, value);
            return value;
        }
        logger.warn("Dado de teste não encontrado: {}", key);
        return null;
    }
    

    public String getTestData(String key, String defaultValue) {
        String value = getTestData(key);
        return value != null ? value : defaultValue;
    }
    

    public void setRuntimeData(String key, Object value) {
        runtimeData.put(key, value);
        logger.debug("Dado de runtime definido: {} = {}", key, value);
    }
    

    public Object getRuntimeData(String key) {
        Object value = runtimeData.get(key);
        logger.debug("Dado de runtime obtido: {} = {}", key, value);
        return value;
    }
    

    public Object getRuntimeData(String key, Object defaultValue) {
        Object value = getRuntimeData(key);
        return value != null ? value : defaultValue;
    }
    

    public void clearRuntimeData() {
        runtimeData.clear();
        logger.debug("Dados de runtime limpos");
    }
    

    public Map<String, String> getDefaultUserData() {
        Map<String, String> userData = new HashMap<>();
        userData.put("usuario", getTestData("default.user", "tomsmith"));
        userData.put("senha", getTestData("default.password", "SuperSecretPassword!"));
        userData.put("senhaInvalida", getTestData("invalid.password", "123testando"));
        return userData;
    }
    

    public Map<String, String> getTestUrls() {
        Map<String, String> urls = new HashMap<>();
        urls.put("login", getTestData("urls.login", "https://the-internet.herokuapp.com/login"));
        urls.put("secure", getTestData("urls.secure", "https://the-internet.herokuapp.com/secure"));
        return urls;
    }
} 