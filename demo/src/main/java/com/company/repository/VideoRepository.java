package com.company.repository;

import com.company.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    // 제목이나 설명에 해당 키워드가 포함된 동영상 목록 반환 (대소문자 구분 없이)
    List<Video> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
}
