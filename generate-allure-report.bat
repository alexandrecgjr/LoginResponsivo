@echo off
echo ========================================
echo    GERANDO RELATORIO ALLURE
echo ========================================
echo.

echo Limpando relatorios anteriores...
if exist "target\allure-report" rmdir /s /q "target\allure-report"

echo.
echo Gerando relatorio Allure...
mvn allure:report

echo.
echo ========================================
echo    RELATORIO GERADO COM SUCESSO!
echo ========================================
echo.
echo Relatorio disponivel em: target/allure-report/index.html
echo.
echo Para abrir o relatorio, execute: open-allure-report.bat
echo.
pause 