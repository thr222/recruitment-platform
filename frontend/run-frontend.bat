@echo off
setlocal
cd /d "%~dp0"

where node >nul 2>nul
if errorlevel 1 (
  echo [ERROR] Node.js is not available in PATH.
  pause
  exit /b 1
)

if not exist "%~dp0node_modules" (
  echo [INFO] node_modules not found, installing dependencies...
  call npm install
  if errorlevel 1 (
    echo [ERROR] npm install failed.
    pause
    exit /b 1
  )
)

echo [INFO] Starting frontend at http://127.0.0.1:5173
call npm run dev -- --host 127.0.0.1 --port 5173
set "EXIT_CODE=%ERRORLEVEL%"
if not "%EXIT_CODE%"=="0" (
  echo.
  echo [ERROR] Frontend exited with code %EXIT_CODE%.
  pause
)
exit /b %EXIT_CODE%
