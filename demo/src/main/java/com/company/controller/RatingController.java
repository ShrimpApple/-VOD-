package com.company.controller;

import com.company.domain.Rating;
import com.company.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // 사용자(userId)가 동영상(videoId)에 대해 평가(liked=true/false)를 남김
    @PostMapping("/{userId}/{videoId}")
    public ResponseEntity<Rating> addOrUpdateRating(@PathVariable Long userId,
                                                    @PathVariable Long videoId,
                                                    @RequestParam Boolean liked) {
        Rating rating = ratingService.addOrUpdateRating(userId, videoId, liked);
        return ResponseEntity.ok(rating);
    }

    // 동영상의 좋아요/싫어요 수 집계
    @GetMapping("/video/{videoId}")
    public ResponseEntity<?> getRatingCounts(@PathVariable Long videoId) {
        Long likes = ratingService.getLikes(videoId);
        Long dislikes = ratingService.getDislikes(videoId);
        return ResponseEntity.ok(new RatingResponse(likes, dislikes));
    }

    // 내부 클래스로 응답 포맷 정의
    static class RatingResponse {
        private Long likes;
        private Long dislikes;

        public RatingResponse(Long likes, Long dislikes) {
            this.likes = likes;
            this.dislikes = dislikes;
        }

        public Long getLikes() {
            return likes;
        }

        public Long getDislikes() {
            return dislikes;
        }
    }
}

