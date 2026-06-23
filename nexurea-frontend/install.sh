#!/bin/bash

# Nexurea Frontend Installation Script
# This script will set up and run the Nexurea frontend

echo "=========================================="
echo "  Nexurea Frontend Installation Script"
echo "=========================================="
echo ""

# Check if Node.js is installed
if ! command -v node &> /dev/null
then
    echo "❌ Node.js is not installed!"
    echo "Please install Node.js from https://nodejs.org/"
    exit 1
fi

echo "✅ Node.js version: $(node -v)"
echo "✅ npm version: $(npm -v)"
echo ""

# Check if .env exists
if [ ! -f .env ]; then
    echo "⚠️  .env file not found! Creating from template..."
    cat > .env << EOF
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_RAZORPAY_KEY=your_razorpay_key_here
EOF
    echo "✅ .env file created!"
    echo "⚠️  Please update the .env file with your actual values"
    echo ""
fi

# Check if node_modules exists
if [ ! -d "node_modules" ]; then
    echo "📦 Installing dependencies..."
    npm install

    if [ $? -eq 0 ]; then
        echo "✅ Dependencies installed successfully!"
    else
        echo "❌ Failed to install dependencies"
        exit 1
    fi
else
    echo "✅ Dependencies already installed"
fi

echo ""
echo "=========================================="
echo "  Installation Complete!"
echo "=========================================="
echo ""
echo "📝 Next Steps:"
echo ""
echo "1. Update .env file with your configuration:"
echo "   - REACT_APP_API_BASE_URL (your backend URL)"
echo "   - REACT_APP_RAZORPAY_KEY (your Razorpay key)"
echo ""
echo "2. Start the development server:"
echo "   npm start"
echo ""
echo "3. Build for production:"
echo "   npm run build"
echo ""
echo "📚 Documentation:"
echo "   - README.md - Project overview"
echo "   - SETUP_GUIDE.md - Complete setup guide"
echo "   - QUICK_REFERENCE.md - Quick reference"
echo "   - ARCHITECTURE.md - System architecture"
echo ""
echo "🚀 Ready to start developing!"
echo ""

# Ask if user wants to start the dev server
read -p "Do you want to start the development server now? (y/n) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
    echo ""
    echo "🚀 Starting development server..."
    echo "   The app will open at http://localhost:3000"
    echo ""
    npm start
fi
