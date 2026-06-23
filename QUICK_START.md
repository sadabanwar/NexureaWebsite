# ⚡ Nexurea Entrepreneur - Quick Start Guide

## 🚀 Get Running in 5 Minutes!

### Step 1: Database (1 minute)
```bash
# Create database and add sample data
mysql -u root -p
```
```sql
CREATE DATABASE nexurea_university;
exit;
```
```bash
mysql -u root -p nexurea_university < DATABASE_SETUP.sql
```

### Step 2: Backend Configuration (30 seconds)
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
razorpay.key.id=rzp_test_YOUR_KEY
razorpay.key.secret=YOUR_SECRET
```

### Step 3: Start Backend (1 minute)
```bash
mvn spring-boot:run
```
✅ Backend running on http://localhost:8080

### Step 4: Frontend Configuration (30 seconds)
Create `nexurea-frontend/.env`:
```env
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_RAZORPAY_KEY=rzp_test_YOUR_KEY
```

### Step 5: Start Frontend (2 minutes)
```bash
cd nexurea-frontend
npm install
npm start
```
✅ Frontend running on http://localhost:3000

---

## 🎯 First Steps

### 1. Login as Admin
- Go to: http://localhost:3000/login
- Username: `admin`
- Password: `admin123`

### 2. Create a Test User
- Go to: http://localhost:3000/register
- Fill the form
- Note your referral code

### 3. Test Payment
- Browse courses: http://localhost:3000/courses
- Click "Enroll Now"
- Use test card: `4111 1111 1111 1111`
- CVV: `123`, Expiry: Any future date

### 4. Check Dashboard
- Go to: http://localhost:3000/dashboard
- See your stats and referral link

### 5. Test Referral
- Copy your referral link
- Register another user with your code
- Have them purchase a course
- Check your commission!

---

## 📋 Key URLs

| Service | URL | Credentials |
|---------|-----|-------------|
| Frontend | http://localhost:3000 | - |
| Backend API | http://localhost:8080/api | - |
| Swagger Docs | http://localhost:8080/swagger-ui.html | - |
| Admin Login | http://localhost:3000/login | admin / admin123 |
| Admin Panel | http://localhost:3000/admin | Requires admin login |

---

## 🔑 Default Admin Credentials

```
Username: admin
Password: admin123
Email: admin@nexurea.com
```

**⚠️ IMPORTANT: Change this password immediately!**

---

## 💳 Razorpay Test Cards

### Successful Payment
```
Card Number: 4111 1111 1111 1111
CVV: Any 3 digits
Expiry: Any future date
Name: Test User
```

### Failed Payment
```
Card Number: 4111 1111 1111 1234
CVV: 123
Expiry: 12/25
```

---

## 🛠️ Quick Commands

### Backend
```bash
# Build
mvn clean install

# Run
mvn spring-boot:run

# Run tests
mvn test

# Package JAR
mvn clean package
```

### Frontend
```bash
# Install dependencies
npm install

# Start dev server
npm start

# Build for production
npm run build

# Run tests
npm test
```

### Database
```bash
# Login to MySQL
mysql -u root -p

# Use database
use nexurea_university;

# Show tables
show tables;

# Check users
select * from users;

# Check packages
select * from course_packages;
```

---

## 📱 Test User Journey

### Complete Flow (5 minutes)
1. **Register** (http://localhost:3000/register)
   - Fill form with your details
   - Optional: Enter a referral code
   - Click "Sign Up"

2. **Browse Courses** (http://localhost:3000/courses)
   - View all 4 packages
   - Read features and pricing
   - Click "Enroll Now" on any package

3. **Make Payment**
   - Razorpay modal opens
   - Enter test card details
   - Complete payment

4. **Access Course** (http://localhost:3000/my-courses)
   - See purchased course
   - Click to watch videos

5. **View Dashboard** (http://localhost:3000/dashboard)
   - Copy referral link
   - Share with friends
   - Earn commissions!

6. **Request Withdrawal** (http://localhost:3000/withdrawals)
   - Enter amount (min ₹100)
   - Add bank/UPI details
   - Submit request

---

## 🛡️ Admin Tasks

### After Login as Admin

1. **View Dashboard**
   - http://localhost:3000/admin
   - See platform statistics

2. **Manage Users**
   - http://localhost:3000/admin/users
   - Activate/deactivate users

3. **Manage Courses**
   - http://localhost:3000/admin/courses
   - Create new packages
   - Add videos

4. **Approve Withdrawals**
   - http://localhost:3000/admin/withdrawals
   - View pending requests
   - Approve with transaction ID

---

## 🐛 Quick Troubleshooting

### Backend Won't Start
```bash
# Check MySQL is running
systemctl status mysql  # Linux
# or
mysql -u root -p  # Try connecting

# Check port 8080 is free
netstat -ano | findstr :8080  # Windows
lsof -i :8080  # Mac/Linux
```

### Frontend Won't Start
```bash
# Clear cache
rm -rf node_modules package-lock.json
npm install

# Check port 3000 is free
netstat -ano | findstr :3000  # Windows
lsof -i :3000  # Mac/Linux
```

### Database Connection Error
```properties
# Check application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/nexurea_university?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### Razorpay Not Loading
```html
<!-- Check public/index.html has this script -->
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
```

---

## 📊 Commission Examples

### Package 1 (₹499 - 50%)
- User purchases: ₹499
- You earn: ₹249.50
- Platform keeps: ₹249.50

### Package 2 (₹999 - 55%)
- User purchases: ₹999
- You earn: ₹549.45
- Platform keeps: ₹449.55

### Package 3 (₹1,499 - 57%)
- User purchases: ₹1,499
- You earn: ₹854.43
- Platform keeps: ₹644.57

### Package 4 (₹1,999 - 60%)
- User purchases: ₹1,999
- You earn: ₹1,199.40
- Platform keeps: ₹799.60

---

## 🎯 Next Steps

1. **Get Razorpay Keys**
   - Sign up at https://dashboard.razorpay.com/
   - Get test keys from Settings → API Keys
   - Update in backend and frontend

2. **Customize Content**
   - Add your logo
   - Update about us page
   - Add real course videos
   - Customize colors if needed

3. **Test Everything**
   - Registration flow
   - Payment process
   - Video access
   - Referral system
   - Admin functions

4. **Deploy**
   - Follow DEPLOYMENT_GUIDE.md
   - Use production Razorpay keys
   - Enable HTTPS
   - Configure domain

---

## 📚 Documentation

- **README.md** - Main overview
- **PROJECT_SUMMARY.md** - Complete details
- **DEPLOYMENT_GUIDE.md** - Production setup
- **COMPLETE_CHECKLIST.md** - Feature verification

---

## 🆘 Need Help?

### Check These First:
1. Browser console (F12) for frontend errors
2. Terminal/console for backend errors
3. MySQL logs for database issues
4. Documentation files listed above

### Common Issues:
- **Port already in use** → Change port or kill process
- **Database error** → Check MySQL credentials
- **Payment not working** → Verify Razorpay keys
- **Can't login** → Check backend is running

---

## ✅ Success Checklist

After setup, you should have:
- [ ] Backend running on :8080
- [ ] Frontend running on :3000
- [ ] Database created and initialized
- [ ] Can login as admin
- [ ] Can register new user
- [ ] Can browse courses
- [ ] Can complete test payment
- [ ] Can see dashboard
- [ ] Can copy referral link
- [ ] Admin panel accessible

---

## 🎉 You're Ready!

Your **Nexurea Entrepreneur Digital University** is now running!

**Start earning:** Share your referral link and earn up to 60% commission!

**For detailed information, check other documentation files.**

---

<div align="center">

**Happy Learning & Earning! 🚀**

[Back to README](README.md) | [Deployment Guide](DEPLOYMENT_GUIDE.md) | [Complete Checklist](COMPLETE_CHECKLIST.md)

</div>
