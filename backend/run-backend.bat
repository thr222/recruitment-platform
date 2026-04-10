@echo off
setlocal
cd /d "%~dp0"

set "PROJECT_BACKEND_DIR=%~dp0"
set "JAVA_HOME=D:\Java\JDK17"
set "MAVEN_CMD=D:\bishe\apache-maven-3.9.9\bin\mvn.cmd"

if not exist "%JAVA_HOME%\bin\java.exe" (
  echo [ERROR] JDK 17 not found: %JAVA_HOME%
  pause
  exit /b 1
)

if not exist "%MAVEN_CMD%" (
  echo [ERROR] Maven not found: %MAVEN_CMD%
  pause
  exit /b 1
)

set "PATH=%JAVA_HOME%\bin;%PATH%"

echo [INFO] Using JAVA_HOME=%JAVA_HOME%
echo [INFO] Backend root=%PROJECT_BACKEND_DIR%
echo [INFO] Installing backend modules to local Maven repo...
call "%MAVEN_CMD%" -f "%PROJECT_BACKEND_DIR%pom.xml" -pl recruit-boot -am -DskipTests install
if errorlevel 1 (
  echo [ERROR] Backend build failed.
  pause
  exit /b 1
)

echo [INFO] Starting backend at http://127.0.0.1:8080
cd /d "%PROJECT_BACKEND_DIR%recruit-boot"
call "%MAVEN_CMD%" spring-boot:run
set "EXIT_CODE=%ERRORLEVEL%"
if not "%EXIT_CODE%"=="0" (
  echo.
  echo [ERROR] Backend exited with code %EXIT_CODE%.
  pause
)
exit /b %EXIT_CODE%
