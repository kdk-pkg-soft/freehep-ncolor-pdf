@echo off
CLS
setlocal

:: -- %~dp0 expands to the directory of this batch file
cd /d "%~dp0"
SET ANT_OPTS=-Xms128m -Xmx512m
call "..\..\..\..\bin\ant\bin\ant.bat" %*
echo Final Ant Build ExitCode: %ERRORLEVEL%

exit /b %ERRORLEVEL%
