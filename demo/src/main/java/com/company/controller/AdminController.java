package com.company.controller;

import com.company.domain.Video;
import com.company.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping("/videos")
    public ResponseEntity<List<Video>> getAllVideos() {
        List<Video> videos = videoRepository.findAll();
        return ResponseEntity.ok(videos);
    }

    @PostMapping("/videos")
    public ResponseEntity<Video> addVideo(@RequestBody Video video) {
        Video savedVideo = videoRepository.save(video);
        return ResponseEntity.ok(savedVideo);
    }

    @PutMapping("/videos/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable Long id, @RequestBody Video videoDetails) {
        return videoRepository.findById(id).map(video -> {
            video.setTitle(videoDetails.getTitle());
            video.setDescription(videoDetails.getDescription());
            video.setUrl(videoDetails.getUrl());
            Video updatedVideo = videoRepository.save(video);
            return ResponseEntity.ok(updatedVideo);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable Long id) {
        return videoRepository.findById(id).map(video -> {
            videoRepository.delete(video);
            return ResponseEntity.ok("Video deleted successfully");
        }).orElse(ResponseEntity.notFound().build());
    }
}
