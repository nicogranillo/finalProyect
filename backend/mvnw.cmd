@ECHO OFF
IF "%MAVEN_HOME%"=="" (
  SET MAVEN_HOME=%~dp0..\
)

IF "%JAVA_HOME%"=="" (
  FOR /F "delims=" %%i IN ('"%JAVA_HOME_DETECT%"') DO SET JAVA_HOME=%%i
)

"%MAVEN_HOME%\bin\mvn" %*