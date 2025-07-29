@echo off
echo ========================================
echo    ABRINDO RELATORIO ALLURE
echo ========================================
echo.

if exist "target\allure-report\index.html" (
    echo Abrindo relatorio no navegador...
    start "" "target\allure-report\index.html"
    echo.
    echo Relatorio aberto com sucesso!
) else (
    echo.
    echo ERRO: Relatorio nao encontrado!
    echo.
    echo Execute primeiro: generate-allure-report.bat
    echo.
)

echo.
pause 