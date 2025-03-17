package com.company.controller;

import com.company.domain.Comment;
import com.company.dto.CommentDto;
import com.company.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 댓글 추가: 요청 본문을 JSON 객체로 받아, DTO에서 댓글 내용을 추출
    @PostMapping("/{videoId}")
    public ResponseEntity<Comment> addComment(@PathVariable Long videoId, @RequestBody CommentDto commentDto) {
        Comment comment = commentService.addComment(videoId, commentDto.getContent());
        return ResponseEntity.ok(comment);
    }

    // 동영상에 대한 댓글 조회
    @GetMapping("/{videoId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long videoId) {
        List<Comment> comments = commentService.getCommentsByVideo(videoId);
        return ResponseEntity.ok(comments);
    }
}
