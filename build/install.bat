@echo off
title Installing verification-code-generator
call msg info "[INFO] Installing verification-code-generator" & echo.

set BASEDIR=%~dp0

pushd %BASEDIR%\..\src
  call mvn clean install -DperformTest=true -DperformSource=true
popd

call beep.bat
timeout /t 1 >NUL 
call beep.bat
timeout /t 1 >NUL 
call beep.bat
timeout /t 1 >NUL 
call beep.bat
timeout /t 1 >NUL 
call beep.bat
timeout /t 1 >NUL 

timeout /t 10 >NUL 