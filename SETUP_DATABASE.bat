@echo off
title Nexurea - Database Setup
color 0B

echo ========================================
echo    NEXUREA ENTREPRENEUR DATABASE SETUP
echo ========================================
echo.

REM Check if MySQL is in PATH
where mysql >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] MySQL is not found in system PATH!
    echo.
    echo MySQL must be installed first.
    echo.
    echo Please do ONE of the following:
    echo.
    echo OPTION 1: Install MySQL
    echo   Download from: https://dev.mysql.com/downloads/installer/
    echo   Then run this script again.
    echo.
    echo OPTION 2: Use MySQL Workbench (GUI - Easier!)
    echo   1. Open MySQL Workbench
    echo   2. File ^> Open SQL Script
    echo   3. Select: DATABASE_SETUP.sql
    echo   4. Click Execute (Lightning Bolt icon)
    echo.
    echo OPTION 3: Add MySQL to PATH
    echo   If MySQL is installed but not in PATH:
    echo   1. Find MySQL installation (usually C:\Program Files\MySQL\MySQL Server 8.0\bin)
    echo   2. Add it to Windows PATH environment variable
    echo   3. Restart this Command Prompt
    echo.
    echo For detailed instructions, read: DATABASE_SETUP_GUIDE.md
    echo.
    pause
    exit /b 1
)

echo [OK] MySQL found in system PATH
echo.

REM Try to find MySQL binary path
for /f "tokens=*" %%i in ('where mysql 2^>nul') do set MYSQL_PATH=%%i

echo MySQL Location: %MYSQL_PATH%
echo.

echo ========================================
echo   Running Database Setup Script
echo ========================================
echo.
echo This script will:
echo   1. Create database: nexurea_university
echo   2. Create default admin user
echo   3. Add sample course packages
echo.

echo Please enter your MySQL root password when prompted.
echo.

REM Run the SQL script
mysql -u root -p < "%~dp0DATABASE_SETUP.sql"

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Failed to execute database setup script!
    echo.
    echo Common issues:
    echo   1. Wrong MySQL root password
    echo   2. MySQL service not running
    echo   3. Permission denied
    echo.
    echo Solutions:
    echo   1. Make sure you entered the correct MySQL root password
    echo   2. Check MySQL service is running: Services ^> MySQL80
    echo   3. Run this script as Administrator
    echo.
    echo For detailed help, read: DATABASE_SETUP_GUIDE.md
    echo.
    pause
    exit /b 1
)

echo.
echo ========================================
echo   DATABASE SETUP SUCCESSFUL!
echo ========================================
echo.
echo Database Name: nexurea_university
echo Default Admin Username: admin
echo Default Admin Password: admin123
echo.
echo Sample data added:
echo   - 4 Course Packages (₹499, ₹999, ₹1499, ₹1999)
echo   - Admin user with ROLE_ADMIN
echo   - Sample course videos
echo.
echo IMPORTANT: Update MySQL password in backend config
echo   File: src\main\resources\application.properties
echo   Line 3: spring.datasource.password=YOUR_PASSWORD
echo.
echo ========================================
echo   NEXT STEP
echo ========================================
echo.
echo 1. Update application.properties with your MySQL password
echo 2. Run: START_WEBSITE.bat
echo 3. Open: http://localhost:3000
echo 4. Login: admin / admin123
echo.
echo Press any key to exit...
pause >nul
