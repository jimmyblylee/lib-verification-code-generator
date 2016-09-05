@echo off
set JAR_FILE=

pushd lib
  for /f "delims=" %%i in ('dir /b /a-d /s "vc-simple-image*.jar"') do (set JAR_FILE=%%i)
popd

java -jar %JAR_FILE% "output-result"