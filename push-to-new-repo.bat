@echo off
title Push to New GitHub Repository
color 0A

echo ========================================
echo   PUSH TO NEW GITHUB REPOSITORY
echo ========================================
echo.
echo This script will help you push your code to a NEW GitHub repository.
echo.
echo BEFORE RUNNING THIS SCRIPT:
echo.
echo 1. Create a new repository on GitHub:
echo    Visit: https://github.com/new
echo.
echo 2. Repository settings:
echo    - Name: nexurea-entrepreneur (or your choice)
echo    - Description: Digital University Platform with Affiliate Marketing
echo    - Public or Private: Your choice
echo    - DO NOT check any initialization boxes
echo    - Click "Create repository"
echo.
echo 3. Copy your repository URL
echo    Example: https://github.com/sadabanwar/nexurea-entrepreneur.git
echo.
echo ========================================
echo.

set /p CONTINUE="Have you created the repository? (Y/N): "
if /i not "%CONTINUE%"=="Y" (
    echo.
    echo Please create the repository first, then run this script again.
    pause
    exit /b 0
)

echo.
set /p REPO_URL="Enter your repository URL: "

if "%REPO_URL%"=="" (
    echo.
    echo [ERROR] Repository URL cannot be empty!
    pause
    exit /b 1
)

echo.
echo ========================================
echo   Updating Git Remote
echo ========================================
echo.

echo Removing old remote...
git remote remove origin 2>nul

echo Adding new remote: %REPO_URL%
git remote add origin %REPO_URL%

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Failed to add remote!
    pause
    exit /b 1
)

echo.
echo ========================================
echo   Pushing to GitHub
echo ========================================
echo.
echo This will push all your code to GitHub.
echo You will be prompted for:
echo   - Username: Your GitHub username
echo   - Password: Your Personal Access Token (NOT your password!)
echo.
echo If you don't have a Personal Access Token:
echo   1. Go to: https://github.com/settings/tokens
echo   2. Generate new token (classic)
echo   3. Select scope: repo
echo   4. Copy and use it as password
echo.

pause

echo.
echo Pushing code...
git push -u origin master

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Failed to push to GitHub!
    echo.
    echo Common issues:
    echo   1. Wrong credentials (use Personal Access Token, not password)
    echo   2. Repository URL incorrect
    echo   3. No write access to repository
    echo.
    echo To get Personal Access Token:
    echo   Visit: https://github.com/settings/tokens
    echo.
    pause
    exit /b 1
)

echo.
echo ========================================
echo   SUCCESS!
echo ========================================
echo.
echo Your code has been pushed to GitHub!
echo.
echo View your repository at:
echo %REPO_URL:.git=%
echo.
echo Repository Stats:
echo   - 127 files
echo   - 15,422+ lines of code
echo   - Complete documentation
echo.
echo Next steps:
echo   1. Add repository description
echo   2. Add topics/tags
echo   3. Add screenshots (optional)
echo   4. Share with world!
echo.
echo ========================================
echo.
pause
