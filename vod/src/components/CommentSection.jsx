import React, { useState, useEffect } from 'react';
import api from '../services/api';
import '../styles/style.css';

const CommentSection = ({ videoId }) => {
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');

  const fetchComments = async () => {
    try {
      const response = await api.get(`/comments/${videoId}`);
      setComments(response.data);
    } catch (error) {
      console.error("댓글 조회 실패", error);
    }
  };

  useEffect(() => {
    fetchComments();
  }, [videoId]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // content 필드에 newComment를 담아서 보냄
      await api.post(`/comments/${videoId}`, { content: newComment });
      setNewComment('');
      fetchComments();
    } catch (error) {
      console.error("댓글 추가 실패", error);
    }
  };

  return (
    <div className="container" style={{ marginTop: '2rem' }}>
      <h3>댓글</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="댓글을 입력하세요"
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
        />
        <button type="submit">댓글 추가</button>
      </form>
      <ul>
        {comments.map((comment) => (
          <li key={comment.id} style={{ marginTop: '1rem' }}>
            <p>{comment.content}</p>
            <small>{new Date(comment.createdAt).toLocaleString()}</small>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CommentSection;
