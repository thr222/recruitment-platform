@echo off
setlocal
cd /d "%~dp0"

if not exist "%~dp0backend\run-backend.bat" (
  echo [ERROR] Missing backend launcher: %~dp0backend\run-backend.bat
  pause
  exit /b 1
)

if not exist "%~dp0frontend\run-frontend.bat" (
  echo [ERROR] Missing frontend launcher: %~dp0frontend\run-frontend.bat
  pause
  exit /b 1
)

echo [INFO] Launching backend and frontend in separate windows...
start "Recruit Backend" cmd /k call "%~dp0backend\run-backend.bat"
timeout /t 3 /nobreak >nul
start "Recruit Frontend" cmd /k call "%~dp0frontend\run-frontend.bat"

echo [INFO] Backend window and frontend window have been started.
exit /b 0
