@echo off
title Nexurea Entrepreneur - Startup Script
color 0A

echo ========================================
echo    NEXUREA ENTREPRENEUR PLATFORM
echo           Startup Script
echo ========================================
echo.

REM Check if Java is installed
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH!
    echo.
    echo Please install Java JDK 8 or higher from:
    echo https://adoptium.net/
    echo.
    echo After installation, restart this script.
    pause
    exit /b 1
)

REM Check if Node.js is installed
where node >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Node.js is not installed or not in PATH!
    echo.
    echo Please install Node.js from:
    echo https://nodejs.org/
    echo.
    echo After installation, restart this script.
    pause
    exit /b 1
)

echo [OK] Java is installed
echo [OK] Node.js is installed
echo.

REM Check if MySQL is running
echo Checking MySQL connection...
mysql -u root -p -e "SELECT 1;" >nul 2>nul
if %errorlevel% neq 0 (
    echo.
    echo [WARNING] Could not connect to MySQL.
    echo Please make sure:
    echo   1. MySQL is installed and running
    echo   2. You have set up the database using DATABASE_SETUP.sql
    echo   3. application.properties has correct MySQL password
    echo.
    echo Press any key to continue anyway, or Ctrl+C to cancel...
    pause >nul
)

echo.
echo ========================================
echo   Starting Backend Server (Spring Boot)
echo ========================================
echo.
echo This will take 30-60 seconds on first run...
echo Backend will run on: http://localhost:8080
echo.

REM Start backend in a new window
start "Nexurea Backend Server" cmd /k "cd /d %~dp0 && mvnw.cmd spring-boot:run"

echo.
echo Waiting 30 seconds for backend to start...
timeout /t 30 /nobreak >nul

echo.
echo ========================================
echo   Starting Frontend Server (React)
echo ========================================
echo.
echo Frontend will run on: http://localhost:3000
echo Browser will open automatically!
echo.

REM Check if node_modules exists in frontend
if not exist "%~dp0nexurea-frontend\node_modules\" (
    echo [INFO] Installing frontend dependencies...
    echo This is a one-time process, takes 1-2 minutes...
    echo.
    cd /d "%~dp0nexurea-frontend"
    call npm install
    if %errorlevel% neq 0 (
        echo.
        echo [ERROR] Failed to install frontend dependencies!
        echo Please check your internet connection and try again.
        pause
        exit /b 1
    )
)

REM Start frontend in a new window
start "Nexurea Frontend Server" cmd /k "cd /d %~dp0nexurea-frontend && npm start"

echo.
echo ========================================
echo        STARTUP COMPLETE!
echo ========================================
echo.
echo Two new windows have opened:
echo   1. Backend Server (Spring Boot)
echo   2. Frontend Server (React)
echo.
echo Your browser should open automatically to:
echo   http://localhost:3000
echo.
echo If it doesn't open, manually visit:
echo   http://localhost:3000
echo.
echo LOGIN CREDENTIALS:
echo   Username: admin
echo   Password: admin123
echo.
echo To STOP the servers:
echo   Close both Command Prompt windows
echo   OR press Ctrl+C in each window
echo.
echo ========================================
echo.
echo Press any key to close this window...
pause >nul
