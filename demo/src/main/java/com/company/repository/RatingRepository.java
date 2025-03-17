package com.company.repository;

import com.company.domain.Rating;
import com.company.domain.User;
import com.company.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserAndVideo(User user, Video video);
    Long countByVideoAndLiked(Video video, Boolean liked);
}
