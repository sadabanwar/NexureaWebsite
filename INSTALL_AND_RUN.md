# 🚀 Install and Run Nexurea Entrepreneur Locally

## ⚠️ Prerequisites to Install First

Your system needs these tools to run the website. Install them in this order:

### 1. Install Java Development Kit (JDK)
**Required for Backend**

**Download:** https://www.oracle.com/java/technologies/javase/jdk8-downloads.html
- Or use OpenJDK: https://adoptium.net/
- Install JDK 8 or higher
- During installation, it will set PATH automatically

**Verify installation:**
```cmd
java -version
```
Should show: `java version "1.8.0"` or higher

---

### 2. Install MySQL
**Required for Database**

**Download:** https://dev.mysql.com/downloads/mysql/
- Choose Windows MSI Installer
- During installation:
  - Set root password (remember it!)
  - Default port: 3306
  - Start MySQL as Windows Service

**Verify installation:**
```cmd
mysql --version
```

---

### 3. Install Node.js
**Required for Frontend**

**Download:** https://nodejs.org/en/download/
- Choose Windows Installer (.msi)
- Install LTS version (recommended)
- This includes npm automatically

**Verify installation:**
```cmd
node --version
npm --version
```

---

## 📦 Once Prerequisites Are Installed

### Step 1: Setup Database (5 minutes)

**Open Command Prompt as Administrator:**

```cmd
# Login to MySQL
mysql -u root -p
# Enter your root password

# Run this SQL:
CREATE DATABASE nexurea_university;
exit;

# Import the setup script
cd C:\Users\sadab.anwar\Downloads\javalld1practice-remote
mysql -u root -p nexurea_university < DATABASE_SETUP.sql
```

**Database is now ready!** ✅

---

### Step 2: Configure Backend (2 minutes)

**Edit this file:**
```
C:\Users\sadab.anwar\Downloads\javalld1practice-remote\src\main\resources\application.properties
```

**Update these lines:**
```properties
# Line 3 - Your MySQL password
spring.datasource.password=YOUR_MYSQL_ROOT_PASSWORD

# Lines 22-23 - Razorpay keys (get from https://dashboard.razorpay.com)
razorpay.key.id=rzp_test_YOUR_KEY_ID
razorpay.key.secret=YOUR_KEY_SECRET
```

**For now, you can use test Razorpay keys to get started quickly.**

---

### Step 3: Start Backend (3 minutes)

**Open Command Prompt:**

```cmd
cd C:\Users\sadab.anwar\Downloads\javalld1practice-remote

# First time: Install dependencies (takes 2-3 minutes)
mvnw.cmd clean install -DskipTests

# Start the backend server
mvnw.cmd spring-boot:run
```

**Wait for this message:**
```
Started RestfulBlogBackendApplication in X seconds
```

**Backend is now running on:** http://localhost:8080 ✅

**Keep this window open!**

---

### Step 4: Setup Frontend (2 minutes)

**Open a NEW Command Prompt (keep backend running):**

```cmd
cd C:\Users\sadab.anwar\Downloads\javalld1practice-remote\nexurea-frontend

# Install dependencies (first time only, takes 1-2 minutes)
npm install
```

**Create .env file:**

Create a file: `C:\Users\sadab.anwar\Downloads\javalld1practice-remote\nexurea-frontend\.env`

**Add this content:**
```env
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_RAZORPAY_KEY=rzp_test_YOUR_KEY_ID
```

---

### Step 5: Start Frontend (1 minute)

**In the same Command Prompt:**

```cmd
npm start
```

**Wait for browser to open automatically!**

**Frontend is now running on:** http://localhost:3000 ✅

**Your website is LIVE!** 🎉

---

## 🌐 Access Your Website

### Main Website
**URL:** http://localhost:3000

### Login as Admin
**URL:** http://localhost:3000/login
- **Username:** admin
- **Password:** admin123

### View All Pages
- **Home:** http://localhost:3000/
- **About:** http://localhost:3000/about
- **Courses:** http://localhost:3000/courses
- **Register:** http://localhost:3000/register
- **Dashboard:** http://localhost:3000/dashboard (after login)
- **Admin Panel:** http://localhost:3000/admin (admin only)

### Backend API
**URL:** http://localhost:8080/api
**API Docs:** http://localhost:8080/swagger-ui.html

---

## 🎯 Quick Test Flow

### Test the Complete System:

1. **Open:** http://localhost:3000
2. **Click:** "Register" (top right)
3. **Fill form** with test data
4. **Login** with your credentials
5. **Go to:** Courses page
6. **Click:** "Enroll Now" on any package
7. **Payment:** Use test card `4111 1111 1111 1111`
8. **View:** My Courses to watch videos
9. **Check:** Dashboard for your referral link
10. **Test:** Admin panel with admin/admin123

---

## 🛑 How to Stop the Servers

### Stop Frontend
- Go to the Command Prompt running `npm start`
- Press `Ctrl + C`
- Type `Y` and press Enter

### Stop Backend
- Go to the Command Prompt running `mvnw.cmd spring-boot:run`
- Press `Ctrl + C`

---

## 🔄 How to Restart

### Restart Backend
```cmd
cd C:\Users\sadab.anwar\Downloads\javalld1practice-remote
mvnw.cmd spring-boot:run
```

### Restart Frontend
```cmd
cd C:\Users\sadab.anwar\Downloads\javalld1practice-remote\nexurea-frontend
npm start
```

---

## 🐛 Common Issues and Solutions

### Issue: "java is not recognized"
**Solution:**
- Java is not installed or not in PATH
- Install JDK from: https://adoptium.net/
- Restart Command Prompt after installation

### Issue: "npm is not recognized"
**Solution:**
- Node.js is not installed
- Install from: https://nodejs.org/
- Restart Command Prompt after installation

### Issue: "Access denied for user 'root'@'localhost'"
**Solution:**
- MySQL password is incorrect
- Update password in `application.properties` line 3
- Or reset MySQL root password

### Issue: "Unknown database 'nexurea_university'"
**Solution:**
- Database not created
- Run: `mysql -u root -p < DATABASE_SETUP.sql`

### Issue: "Port 8080 already in use"
**Solution:**
- Another program is using port 8080
- Stop that program, or change port in `application.properties`

### Issue: "Port 3000 already in use"
**Solution:**
- Type `Y` when asked to use a different port
- Or stop the program using port 3000

### Issue: Backend starts but APIs don't work
**Solution:**
- Check MySQL is running
- Verify database credentials in `application.properties`
- Check console for error messages

### Issue: Frontend can't connect to backend
**Solution:**
- Make sure backend is running (check http://localhost:8080)
- Verify `.env` file has correct `REACT_APP_API_BASE_URL`
- Clear browser cache and refresh

---

## ✅ System Check

Before starting, verify everything is installed:

```cmd
java -version
mysql --version
node --version
npm --version
```

All four should work without errors!

---

## 📱 What You'll See

### Homepage (http://localhost:3000)
- Professional hero section with Navy Blue + Gold theme
- Feature highlights
- Course package preview
- Call-to-action buttons

### Courses Page
- 4 Beautiful course cards
- Pricing: ₹499, ₹999, ₹1499, ₹1999
- Commission rates displayed
- "Enroll Now" buttons

### Dashboard (After Login)
- Total Sales
- Total Commission
- Referral Count
- Available Balance
- Your unique referral link (with copy button)
- Recent commissions table

### Admin Panel (admin/admin123)
- Platform statistics
- User management
- Course management
- Withdrawal approval
- Revenue charts

---

## 🎨 Theme Preview

**Colors you'll see:**
- **Primary Navy Blue:** #1a237e (Navbar, buttons)
- **Gold:** #ffd700 (Accents, CTA buttons)
- **White Cards:** Clean, professional layouts
- **Smooth Animations:** Professional hover effects

---

## 💡 Tips

### For Faster Startup:
1. Once installed, you only need to run:
   - `mvnw.cmd spring-boot:run` (backend)
   - `npm start` (frontend)

2. No need to reinstall packages every time!

### For Development:
- Backend: Changes require restart
- Frontend: Auto-refreshes on file save
- Database: Changes persist between restarts

### For Testing:
- Use Razorpay test mode
- Test card: `4111 1111 1111 1111`
- Create multiple test users to test referrals

---

## 🚀 Next Steps After Running

1. **Explore the site** - Click around, test features
2. **Get Razorpay keys** - Sign up at https://dashboard.razorpay.com
3. **Customize content** - Update About page, add real courses
4. **Add course videos** - Upload actual course content
5. **Deploy to production** - See DEPLOYMENT_GUIDE.md

---

## 📞 Need Help?

### Check These Files:
- **QUICK_START.md** - Quick reference
- **README.md** - Complete overview
- **DEPLOYMENT_GUIDE.md** - Production setup
- **COMPLETE_CHECKLIST.md** - Feature list

### Still Stuck?
1. Check all prerequisites are installed
2. Verify MySQL is running (Windows Services)
3. Check for error messages in Command Prompt
4. Make sure ports 3000 and 8080 are free

---

## 🎉 You're Ready!

Once both servers are running, your professional digital university platform will be live on your computer!

**Homepage:** http://localhost:3000
**Admin:** admin / admin123

**Enjoy exploring your new platform!** 🚀

---

<div align="center">

## 📋 Quick Command Reference

**Start Backend:**
```cmd
cd C:\Users\sadab.anwar\Downloads\javalld1practice-remote
mvnw.cmd spring-boot:run
```

**Start Frontend (new window):**
```cmd
cd C:\Users\sadab.anwar\Downloads\javalld1practice-remote\nexurea-frontend
npm start
```

**Access Website:**
```
http://localhost:3000
```

**That's it!** 🎊

</div>
