import React, { useState, useEffect } from 'react';
import api from '../services/api';
import '../styles/style.css';

const RatingSection = ({ videoId, userId }) => {
  const [likes, setLikes] = useState(0);
  const [dislikes, setDislikes] = useState(0);

  const fetchRatings = async () => {
    try {
      const response = await api.get(`/ratings/video/${videoId}`);
      setLikes(response.data.likes);
      setDislikes(response.data.dislikes);
    } catch (error) {
      console.error("평가 정보를 불러오지 못했습니다.", error);
    }
  };

  useEffect(() => {
    fetchRatings();
  }, [videoId]);

  const handleRating = async (liked) => {
    try {
      // userId는 현재 로그인한 사용자의 ID (여기서는 예시로 전달)
      await api.post(`/ratings/${userId}/${videoId}?liked=${liked}`);
      fetchRatings();
    } catch (error) {
      console.error("평가 등록에 실패했습니다.", error);
    }
  };

  return (
    <div className="rating-section" style={{ marginTop: '1rem', textAlign: 'center' }}>
      <button onClick={() => handleRating(true)}>👍 {likes}</button>
      <button onClick={() => handleRating(false)} style={{ marginLeft: '1rem' }}>👎 {dislikes}</button>
    </div>
  );
};

export default RatingSection;
