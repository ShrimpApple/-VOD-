import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Signup from './pages/Signup';
import VideoDetail from './pages/VideoDetail';
import Subscription from './pages/Subscription';
import Profile from './pages/Profile';
import Payment from './pages/Payment';
import AdminDashboard from './pages/AdminDashboard';
import Search from './components/Search'; // 추가

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/search" element={<Search />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/videos/:id" element={<VideoDetail />} />
        <Route path="/subscriptions" element={<Subscription />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/payment" element={<Payment />} />
        <Route path="/admin" element={<AdminDashboard />} />
      </Routes>
    </Router>
  );
};

export default App;
