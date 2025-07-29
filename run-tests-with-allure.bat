@echo off
echo ========================================
echo    EXECUTANDO TESTES COM ALLURE
echo ========================================
echo.

echo Executando testes em Chrome e Firefox...
mvn test

echo.
echo ========================================
echo    GERANDO RELATORIO ALLURE
echo ========================================
echo.

echo Gerando relatorio Allure em portugues...
mvn allure:report -Dallure.language=pt -Dallure.locale=pt_BR

echo.
echo ========================================
echo    PROCESSO FINALIZADO!
echo ========================================
echo.
echo Relatorios disponiveis:
echo - Allure: target/allure-report/index.html
echo - Cucumber: target/cucumber-reports/
echo.
echo Para abrir o relatorio Allure, execute: open-allure-report.bat
echo.
pause 