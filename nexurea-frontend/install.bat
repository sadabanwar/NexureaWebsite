@echo off
REM Nexurea Frontend Installation Script for Windows

echo ==========================================
echo   Nexurea Frontend Installation Script
echo ==========================================
echo.

REM Check if Node.js is installed
where node >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo X Node.js is not installed!
    echo Please install Node.js from https://nodejs.org/
    pause
    exit /b 1
)

echo OK Node.js version:
node -v
echo OK npm version:
npm -v
echo.

REM Check if .env exists
if not exist .env (
    echo Warning: .env file not found! Creating from template...
    (
        echo REACT_APP_API_BASE_URL=http://localhost:8080/api
        echo REACT_APP_RAZORPAY_KEY=your_razorpay_key_here
    ) > .env
    echo OK .env file created!
    echo Warning: Please update the .env file with your actual values
    echo.
)

REM Check if node_modules exists
if not exist node_modules (
    echo Installing dependencies...
    call npm install
    if %ERRORLEVEL% EQU 0 (
        echo OK Dependencies installed successfully!
    ) else (
        echo X Failed to install dependencies
        pause
        exit /b 1
    )
) else (
    echo OK Dependencies already installed
)

echo.
echo ==========================================
echo   Installation Complete!
echo ==========================================
echo.
echo Next Steps:
echo.
echo 1. Update .env file with your configuration:
echo    - REACT_APP_API_BASE_URL (your backend URL)
echo    - REACT_APP_RAZORPAY_KEY (your Razorpay key)
echo.
echo 2. Start the development server:
echo    npm start
echo.
echo 3. Build for production:
echo    npm run build
echo.
echo Documentation:
echo    - README.md - Project overview
echo    - SETUP_GUIDE.md - Complete setup guide
echo    - QUICK_REFERENCE.md - Quick reference
echo    - ARCHITECTURE.md - System architecture
echo.
echo Ready to start developing!
echo.

REM Ask if user wants to start the dev server
set /p REPLY="Do you want to start the development server now? (y/n) "
if /i "%REPLY%"=="y" (
    echo.
    echo Starting development server...
    echo    The app will open at http://localhost:3000
    echo.
    call npm start
)
