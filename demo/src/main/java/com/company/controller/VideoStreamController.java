package com.company.controller;

import com.company.domain.Video;
import com.company.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/stream")
public class VideoStreamController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping("/{videoId}")
    public ResponseEntity<StreamingResponseBody> streamVideo(@PathVariable Long videoId, HttpServletRequest request) {
        Optional<Video> optionalVideo = videoRepository.findById(videoId);
        if (!optionalVideo.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Video video = optionalVideo.get();
        File videoFile = new File(video.getUrl());
        if (!videoFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        long fileLength = videoFile.length();
        String rangeHeader = request.getHeader("Range");

        try {
            if (rangeHeader == null) {
                // 전체 파일 전송
                InputStream inputStream = new FileInputStream(videoFile);
                StreamingResponseBody responseBody = outputStream -> {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    inputStream.close();
                };

                return ResponseEntity.ok()
                        .contentType(getMediaTypeForFile(videoFile))
                        .contentLength(fileLength)
                        .header("Accept-Ranges", "bytes")
                        .header("Cache-Control", "no-cache, no-store, must-revalidate")
                        .body(responseBody);
            } else {
                // Range 헤더가 있는 경우 부분 전송
                String[] ranges = rangeHeader.replace("bytes=", "").split("-");
                long start = Long.parseLong(ranges[0]);
                long end = (ranges.length > 1 && !ranges[1].isEmpty()) ? Long.parseLong(ranges[1]) : fileLength - 1;
                if (end >= fileLength) {
                    end = fileLength - 1;
                }
                long contentLength = end - start + 1;

                StreamingResponseBody responseBody = outputStream -> {
                    try (RandomAccessFile raf = new RandomAccessFile(videoFile, "r")) {
                        raf.seek(start);
                        byte[] buffer = new byte[4096];
                        long bytesRemaining = contentLength;
                        int bytesRead;
                        while (bytesRemaining > 0 && (bytesRead = raf.read(buffer, 0, (int)Math.min(buffer.length, bytesRemaining))) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            bytesRemaining -= bytesRead;
                        }
                    }
                };

                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Range", "bytes " + start + "-" + end + "/" + fileLength);
                headers.add("Accept-Ranges", "bytes");
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");

                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .headers(headers)
                        .contentType(getMediaTypeForFile(videoFile))
                        .contentLength(contentLength)
                        .body(responseBody);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 파일의 확장자에 따라 MediaType 반환 (여기서는 .mp4만 처리)
    private MediaType getMediaTypeForFile(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".mp4")) {
            return MediaType.valueOf("video/mp4");
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}
