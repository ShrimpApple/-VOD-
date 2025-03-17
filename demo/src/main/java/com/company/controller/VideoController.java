package com.company.controller;

import com.company.domain.Video;
import com.company.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    // 동영상 목록 조회
    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos() {
        List<Video> videos = videoRepository.findAll();
        return ResponseEntity.ok(videos);
    }

    // 동영상 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long id) {
        return videoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 동영상 등록 (관리자 전용 예시)
    @PostMapping
    public ResponseEntity<Video> addVideo(@RequestBody Video video) {
        Video savedVideo = videoRepository.save(video);
        return ResponseEntity.ok(savedVideo);
    }

    // 동영상 검색 엔드포인트 추가
    @GetMapping("/search")
    public ResponseEntity<List<Video>> searchVideos(@RequestParam("query") String query) {
        List<Video> videos = videoRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
        return ResponseEntity.ok(videos);
    }
}
