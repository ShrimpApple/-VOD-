package com.company.service;

import com.company.domain.Rating;
import com.company.domain.User;
import com.company.domain.Video;
import com.company.repository.RatingRepository;
import com.company.repository.UserRepository;
import com.company.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    public Rating addOrUpdateRating(Long userId, Long videoId, Boolean liked) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("동영상을 찾을 수 없습니다."));

        Rating rating = ratingRepository.findByUserAndVideo(user, video)
                .orElse(new Rating());
        rating.setUser(user);
        rating.setVideo(video);
        rating.setLiked(liked);
        return ratingRepository.save(rating);
    }

    public Long getLikes(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("동영상을 찾을 수 없습니다."));
        return ratingRepository.countByVideoAndLiked(video, true);
    }

    public Long getDislikes(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("동영상을 찾을 수 없습니다."));
        return ratingRepository.countByVideoAndLiked(video, false);
    }
}
