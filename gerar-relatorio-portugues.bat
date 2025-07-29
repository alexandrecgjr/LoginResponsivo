@echo off
echo ========================================
echo    GERANDO RELATORIO ALLURE EM PORTUGUES
echo ========================================
echo.

echo Limpando relatorios anteriores...
if exist "target\allure-report" rmdir /s /q "target\allure-report"
if exist "target\allure-results" rmdir /s /q "target\allure-results"

echo.
echo Executando testes...
mvn test

echo.
echo Gerando relatorio Allure em portugues...
mvn allure:report -Dallure.language=pt -Dallure.locale=pt_BR

echo.
echo ========================================
echo    RELATORIO EM PORTUGUES GERADO!
echo ========================================
echo.
echo Relatorio disponivel em: target/allure-report/index.html
echo.
echo Para abrir o relatorio, execute: open-allure-report.bat
echo.
pause 