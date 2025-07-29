@echo off
echo ========================================
echo    EXECUTANDO TESTES AUTOMATIZADOS
echo ========================================
echo.
echo Este script executara os testes em:
echo 1. Chrome
echo 2. Firefox
echo.
echo Iniciando execucao...
echo.

mvn test

echo.
echo ========================================
echo    EXECUCAO FINALIZADA
echo ========================================
echo.
echo Relatorios gerados em: target/cucumber-reports/
echo.
pause 