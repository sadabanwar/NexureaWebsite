# Nexurea Entrepreneur Digital University - Frontend

Premium React frontend for the Nexurea affiliate marketing platform with course management and payment integration.

## Features

- **Modern UI/UX**: Navy Blue + Gold premium theme
- **Responsive Design**: Mobile-first approach
- **Razorpay Integration**: Secure payment processing
- **Video Player**: Course video streaming with react-player
- **Admin Dashboard**: Complete admin panel with analytics
- **Affiliate System**: Referral tracking and commission management
- **TypeScript**: Type-safe development

## Tech Stack

- React 18.2
- TypeScript
- React Router DOM 6
- Axios
- React Player
- Recharts
- Razorpay

## Installation

```bash
npm install
```

## Configuration

Create a `.env` file in the root directory:

```env
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_RAZORPAY_KEY=your_razorpay_key_here
```

## Development

```bash
npm start
```

Runs the app in development mode at [http://localhost:3000](http://localhost:3000)

## Build

```bash
npm run build
```

Builds the app for production to the `build` folder.

## Project Structure

```
src/
├── components/         # Reusable components
├── pages/             # Page components
│   ├── Admin/        # Admin pages
│   └── ...           # User pages
├── services/         # API services
├── context/          # React context
├── types/            # TypeScript types
├── styles/           # Global styles
└── App.tsx           # Main app component
```

## Available Routes

### Public Routes
- `/` - Home
- `/about` - About Us
- `/courses` - Course Catalog
- `/login` - Login
- `/register` - Register

### Protected Routes
- `/dashboard` - Affiliate Dashboard
- `/my-courses` - Purchased Courses
- `/video/:packageId` - Video Player
- `/withdrawals` - Withdrawal Management

### Admin Routes
- `/admin` - Admin Dashboard
- `/admin/users` - User Management
- `/admin/courses` - Course Management
- `/admin/withdrawals` - Withdrawal Approval

## Key Features

### For Users
- Browse and purchase courses
- Watch course videos
- Track affiliate earnings
- Request withdrawals
- Share referral links

### For Admins
- Manage users
- Create/edit courses and videos
- Approve/reject withdrawals
- View analytics and reports

## Theme Colors

- Primary Navy: `#1a237e`
- Secondary Navy: `#283593`
- Gold: `#ffd700`
- Light Gold: `#ffe44d`
- Background: `#f5f5f5`

## License

Private - All rights reserved
