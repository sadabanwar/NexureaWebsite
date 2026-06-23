# 🗄️ Database Setup Guide - Nexurea Entrepreneur

## ⚠️ MySQL Must Be Installed First!

**MySQL is not currently detected on your system.**

### Download and Install MySQL:

**Option 1: MySQL Installer (Recommended)**
- Download: https://dev.mysql.com/downloads/installer/
- Choose: "Windows (x86, 32-bit), MSI Installer" (works on 64-bit too)
- Version: MySQL Installer 8.0.x
- Size: ~300 MB download

**Option 2: Standalone MySQL Server**
- Download: https://dev.mysql.com/downloads/mysql/
- Choose: Windows ZIP Archive
- Manual installation

---

## 📦 MySQL Installation Steps

### 1. Run MySQL Installer

1. **Double-click** the downloaded installer
2. **Choose Setup Type:** Developer Default (or Custom)
3. **Click** Next through requirements check
4. **Click** Execute to install

### 2. Configure MySQL Server

During installation, you'll configure:

**Server Configuration:**
- Port: `3306` (default - keep this)
- Root Password: **Choose a strong password and REMEMBER IT!**
- Windows Service: ✅ Enable (so MySQL starts automatically)
- Service Name: `MySQL80` (default)

**Example Root Password:** `MySecure123!`
(Use your own secure password!)

### 3. Complete Installation

- Click Next → Execute → Finish
- MySQL Server is now running!

---

## 🔧 Method 1: Run SQL Script via Command Line (Recommended)

### Step 1: Open Command Prompt as Administrator

- Press `Windows + X`
- Select "Command Prompt (Admin)" or "Windows PowerShell (Admin)"

### Step 2: Navigate to MySQL bin directory

```cmd
cd "C:\Program Files\MySQL\MySQL Server 8.0\bin"
```

Or find your MySQL installation:
```cmd
cd "C:\Program Files (x86)\MySQL\MySQL Server 8.0\bin"
```

### Step 3: Login to MySQL

```cmd
mysql -u root -p
```

Enter your root password when prompted.

### Step 4: Run the Setup Script

**Option A: Source the file from MySQL prompt**
```sql
source C:/Users/sadab.anwar/Downloads/javalld1practice-remote/DATABASE_SETUP.sql
```

**Option B: Exit and run from command line**
```cmd
exit
mysql -u root -p < "C:\Users\sadab.anwar\Downloads\javalld1practice-remote\DATABASE_SETUP.sql"
```

Enter your root password when prompted.

### ✅ Done! Database is ready!

---

## 🔧 Method 2: Using MySQL Workbench (GUI - Easier!)

### Step 1: Install MySQL Workbench

- Included with MySQL Installer
- Or download separately: https://dev.mysql.com/downloads/workbench/

### Step 2: Connect to MySQL

1. **Open** MySQL Workbench
2. **Click** on "Local instance MySQL80" (or your connection)
3. **Enter** root password
4. **Click** OK

### Step 3: Run the Script

1. **Click** "File" → "Open SQL Script"
2. **Navigate to:** `C:\Users\sadab.anwar\Downloads\javalld1practice-remote`
3. **Select:** `DATABASE_SETUP.sql`
4. **Click** Open
5. **Click** the ⚡ Lightning Bolt icon (Execute)
6. **Wait** for "Action Output" to show success messages

### ✅ Done! Check the output panel for success!

---

## 🔧 Method 3: Using Any Database Client

If you have another database client installed:

**Popular options:**
- DBeaver (Free)
- HeidiSQL (Free)
- Navicat
- DataGrip

**Steps:**
1. Connect to MySQL (localhost:3306, user: root)
2. Open and execute `DATABASE_SETUP.sql`

---

## 🔧 Method 4: Manual Database Creation (No Script)

If the script doesn't work, create manually:

### Step 1: Create Database

```sql
CREATE DATABASE nexurea_university;
USE nexurea_university;
```

### Step 2: Let Spring Boot Create Tables

- The application will auto-create tables when you first run it
- Spring Boot is configured with `spring.jpa.hibernate.ddl-auto=update`
- Tables will be created automatically from entity classes

### Step 3: After First Run, Add Admin User

```sql
USE nexurea_university;

-- Insert roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

-- Insert admin user (password: admin123)
INSERT INTO users (name, username, email, password, phone, referral_code, total_earnings, available_balance, withdrawn_amount, total_referrals, created_at, active)
VALUES (
    'Admin User',
    'admin',
    'admin@nexurea.com',
    '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG',
    '9999999999',
    'ADMIN001',
    0.00,
    0.00,
    0.00,
    0,
    NOW(),
    1
);

-- Link admin user to admin role
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r 
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';
```

---

## ✅ Verify Database Setup

### Check Database Exists

```sql
SHOW DATABASES;
```

You should see `nexurea_university` in the list.

### Check Tables (After running Spring Boot once)

```sql
USE nexurea_university;
SHOW TABLES;
```

You should see:
- users
- roles
- user_roles
- course_packages
- course_videos
- purchases
- commissions
- withdrawals

### Check Admin User

```sql
SELECT id, name, username, email, referral_code FROM users WHERE username = 'admin';
```

Should return one row with admin user.

### Check Sample Course Packages

```sql
SELECT id, name, price, commission_rate FROM course_packages;
```

Should return 4 course packages (₹499, ₹999, ₹1499, ₹1999).

---

## 🔑 Update Backend Configuration

After database is created, make sure your backend is configured correctly:

**Edit:** `src/main/resources/application.properties`

**Update these lines:**
```properties
# Line 3 - YOUR MySQL root password
spring.datasource.password=YOUR_MYSQL_ROOT_PASSWORD

# Database URL (should already be correct)
spring.datasource.url=jdbc:mysql://localhost:3306/nexurea_university?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true

# Username (usually root)
spring.datasource.username=root
```

**Example:**
```properties
spring.datasource.password=MySecure123!
```

---

## 🐛 Troubleshooting

### Error: "Access denied for user 'root'@'localhost'"

**Solution:**
- Password is wrong in `application.properties`
- Update line 3 with correct MySQL root password

### Error: "Unknown database 'nexurea_university'"

**Solution:**
- Database not created
- Run: `CREATE DATABASE nexurea_university;` in MySQL

### Error: "Can't connect to MySQL server"

**Solution:**
- MySQL service not running
- Start it: Windows Services → MySQL80 → Start
- Or run: `net start MySQL80` in Command Prompt (Admin)

### Error: "Communications link failure"

**Solution:**
- MySQL not installed or not running
- Check MySQL service in Windows Services
- Try: `mysql -u root -p` to test connection

### Error: "Table 'users' doesn't exist"

**Solution:**
- Tables not created yet
- Normal on first run
- Start Spring Boot backend, it will create tables automatically

### MySQL Workbench can't find server

**Solution:**
- Create new connection manually
- Host: `localhost` or `127.0.0.1`
- Port: `3306`
- Username: `root`
- Password: Your MySQL root password

---

## 📋 Quick Command Reference

### Check if MySQL is Running
```cmd
tasklist | findstr mysql
```

### Start MySQL Service
```cmd
net start MySQL80
```

### Stop MySQL Service
```cmd
net stop MySQL80
```

### Connect to MySQL
```cmd
mysql -u root -p
```

### Create Database Only
```sql
CREATE DATABASE nexurea_university;
```

### Show All Databases
```sql
SHOW DATABASES;
```

### Use Database
```sql
USE nexurea_university;
```

### Show All Tables
```sql
SHOW TABLES;
```

---

## 🎯 Recommended Approach

**For Beginners:**
1. Install MySQL using MySQL Installer
2. Install MySQL Workbench (included)
3. Use Workbench GUI to run DATABASE_SETUP.sql
4. Visual, easy, less chance of errors

**For Advanced Users:**
1. Install MySQL
2. Run script from command line
3. Faster, more control

**Simplest (Let Spring Boot Do It):**
1. Install MySQL
2. Create empty database: `CREATE DATABASE nexurea_university;`
3. Start backend - tables auto-create
4. Manually add admin user using SQL

---

## ✅ After Database Setup

Once database is ready:

1. ✅ Database `nexurea_university` exists
2. ✅ MySQL root password updated in `application.properties`
3. ✅ Ready to start backend server!

**Next step:** Run `START_WEBSITE.bat` or start backend manually!

---

## 📞 Still Need Help?

### Check These:
1. MySQL service is running (Windows Services)
2. Port 3306 is free (not used by other apps)
3. Firewall allows MySQL
4. Using correct root password

### Test MySQL Connection:
```cmd
mysql -u root -p -e "SELECT 1;"
```

If this works, MySQL is ready!

---

## 🎉 Success Indicators

You'll know setup worked when:

✅ No errors during script execution
✅ Database `nexurea_university` exists
✅ Admin user credentials work
✅ Backend starts without database errors
✅ You can login to website with admin/admin123

---

<div align="center">

**Once database is ready, run START_WEBSITE.bat!**

**Your digital university platform will be LIVE!** 🚀

</div>
