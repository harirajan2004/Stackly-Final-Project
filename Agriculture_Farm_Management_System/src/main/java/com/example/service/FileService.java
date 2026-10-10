package com.example.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    // FILE UPLOAD
    String uploadFile(MultipartFile files);

    // FILE DOWNLOAD
    Resource downloadFile(String fileName);
}