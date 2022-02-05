package com.fitmate.backend.controller;

import com.fitmate.backend.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class FileController {
    private final S3Uploader s3Uploader;

    @PostMapping("/images")
    public ResponseEntity<?> upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(s3Uploader.upload(multipartFile, "static"));
    }
}
