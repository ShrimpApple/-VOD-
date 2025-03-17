package com.company.service;

import com.company.domain.Comment;
import com.company.domain.Video;
import com.company.repository.CommentRepository;
import com.company.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VideoRepository videoRepository;

    public Comment addComment(Long videoId, String content) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("동영상을 찾을 수 없습니다."));
        Comment comment = new Comment(content, video);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByVideo(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("동영상을 찾을 수 없습니다."));
        return commentRepository.findByVideo(video);
    }
}
