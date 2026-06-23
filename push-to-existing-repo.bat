@echo off
title Push to Existing GitHub Repository
color 0E

echo ========================================
echo   PUSH TO EXISTING REPOSITORY
echo ========================================
echo.
echo This will push your Nexurea code to your existing repository:
echo   https://github.com/sadabanwar/javalld1practice
echo.
echo WARNING: This will ADD Nexurea code to your existing repository.
echo          The old blog code will still be there.
echo.
echo Current remote:
git remote -v
echo.

set /p CONTINUE="Continue with push? (Y/N): "
if /i not "%CONTINUE%"=="Y" (
    echo.
    echo Push cancelled.
    pause
    exit /b 0
)

echo.
echo ========================================
echo   Pushing to GitHub
echo ========================================
echo.
echo You will be prompted for:
echo   - Username: sadabanwar
echo   - Password: Your Personal Access Token
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
git push origin master

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Failed to push to GitHub!
    echo.
    echo Common issues:
    echo   1. Wrong credentials (use Personal Access Token)
    echo   2. Network connection
    echo   3. Repository access
    echo.
    pause
    exit /b 1
)

echo.
echo ========================================
echo   SUCCESS!
echo ========================================
echo.
echo Your code has been pushed to:
echo   https://github.com/sadabanwar/javalld1practice
echo.
echo Repository now contains:
echo   - Original blog code
echo   - NEW: Nexurea Entrepreneur Platform
echo   - 127 new files
echo   - 15,422+ lines of code
echo.
echo ========================================
echo.
pause
