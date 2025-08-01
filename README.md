# Login Responsivo - Projeto de Testes Automatizados

Este projeto implementa testes automatizados para a funcionalidade de login do site ["The Internet"](https://the-internet.herokuapp.com/login), utilizando Selenium WebDriver, Cucumber e Java.

##  Descrição

O objetivo deste projeto é validar a funcionalidade de login do site "The Internet" através de testes automatizados, garantindo que o sistema se comporte conforme o esperado em cenários positivos e negativos. Os testes são escritos utilizando o padrão BDD (Behavior Driven Development) com Cucumber, permitindo uma melhor legibilidade e manutenção.


##  Tecnologias Utilizadas

- **Java 11+** 
- **Selenium WebDriver 4.15.0** 
- **Cucumber 7.14.0** 
- **JUnit 4.13.2** 
- **Allure Report**
- **Maven** 

##  Estrutura do Projeto

```
src/
└── test/
    ├── java/
    │   └── br/com/automation/
    │       ├── core/              # Classes base e utilitárias
    │       ├── pages/              # Page Objects (padrão Page Object Model)
    │       ├── steps/              # Step Definitions do Cucumber
    │       ├── config/             # Configurações do projeto
    │       ├── utils/              # Classes utilitárias
    │       ├── TestRunner.java     # Executor de testes
    │       └── ChromeFirefoxTestRunner.java
    └── resources/
        ├── features/               # Arquivos .feature do Cucumber
        ├── configs/                # Configurações de dados de teste
        ├── allure.properties       # Configurações do Allure
        ├── config.properties      # Configurações gerais do projeto
        └── logback-test.xml        # Configurações de logging
```

##  Execução

### Configuração do Ambiente

**Clone o repositório:**
```bash
git clone https://github.com/alexandrecgjr/LoginResponsivo.git
```

### Execução via Scripts

#### Execução completa (testes + relatórios)
```bash
run-tests-with-allure.bat
```

#### Execução apenas dos testes
```bash
test-execution.bat
```

### Execução via Maven

#### Executar todos os testes
```bash
mvn test
```

#### Executar testes com parâmetros específicos
```bash
# Executar no Firefox
mvn test -Dbrowser=firefox

# Executar em modo headless
mvn test -Dheadless=true

# Executar testes com tag específica
mvn test -Dcucumber.filter.tags="@positivo"
```

##  Relatórios de Teste

### Relatórios Cucumber
Os relatórios são gerados automaticamente na pasta `target/cucumber-reports/` nos formatos:
- HTML
- JSON
- XML

### Relatórios Allure

#### Gerar relatório Allure
```bash
generate-allure-report.bat
```

#### Abrir relatório Allure
```bash
open-allure-report.bat
```

O relatório Allure será gerado na pasta `target/allure-report/` e abrirá automaticamente no navegador padrão.



##  Casos de Teste Implementados

### Cenário Positivo
- Login com credenciais válidas
- Verificação da mensagem de sucesso

### Cenário Negativo
- Login com senha incorreta
- Verificação da mensagem de erro

##  Estrutura de Features

O projeto utiliza o padrão BDD com arquivos `.feature`:

```gherkin
# language: pt

Funcionalidade: Validação de Login
  Como um usuário padrão
  Quero acessar o sistema com login e senha
  Para validar se login será realizado com sucesso

  @positivo
  Cenário: Login com sucesso
    Dado que o usuário acessa a página de login
    Quando o usuário preenche o campo usuário com "tomsmith"
    E preenche o campo senha com "SuperSecretPassword!"
    E clica no botão de login
    Então deve visualizar a mensagem "You logged into a secure area!"
```

##  Arquitetura do Projeto

### Padrão Page Object Model (POM)
O projeto utiliza o padrão Page Object Model para melhor manutenção e reutilização de código:

- **BasePage**: Classe base com métodos comuns de interação com elementos
- **LoginPage**: Page Object específico para a página de login
- **Page Elements**: Localizadores de elementos encapsulados como atributos privados



### Relatórios e Logging
- **Allure**: Relatórios detalhados com captura de screenshots
- **SLF4J/Logback**: Logging estruturado com diferentes níveis

##  Autor

Alexandre C.
