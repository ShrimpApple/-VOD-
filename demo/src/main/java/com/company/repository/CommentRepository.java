package com.company.repository;

import com.company.domain.Comment;
import com.company.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByVideo(Video video);
}
