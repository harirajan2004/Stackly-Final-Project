package com.example.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.exception.FileStorageException;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger =
            LoggerFactory.getLogger(FileServiceImpl.class);

    private final Path uploadDirectory =
            Paths.get("uploads").toAbsolutePath().normalize();

    // FILE UPLOAD
    @Override
    public String uploadFile(MultipartFile files) {

        if (files == null || files.isEmpty()) {

            throw new FileStorageException(
                    "File must not be empty");
        }

        String fileName =
                StringUtils.cleanPath(files.getOriginalFilename());

        if (fileName.isBlank()) {

            throw new FileStorageException(
                    "Invalid file name");
        }

        if (fileName.contains("..")) {

            throw new FileStorageException(
                    "Invalid file path");
        }

        try {

            // Create uploads folder if it does not exist
            Files.createDirectories(uploadDirectory);

            Path targetLocation =
                    uploadDirectory
                            .resolve(fileName)
                            .normalize();

            // Prevent path traversal
            if (!targetLocation.startsWith(uploadDirectory)) {

                throw new FileStorageException(
                        "Invalid file path");
            }

            Files.copy(
                    files.getInputStream(),
                    targetLocation,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            logger.info(
                    "File uploaded successfully: {}",
                    fileName);

            return fileName;

        } catch (IOException exception) {

            logger.error(
                    "Error while uploading file: {}",
                    fileName,
                    exception);

            throw new FileStorageException(
                    "Could not upload file");
        }
    }

    // FILE DOWNLOAD
    @Override
    public Resource downloadFile(String fileName) {

        if (fileName == null || fileName.isBlank()) {

            throw new FileStorageException(
                    "File name must not be empty");
        }

        String cleanFileName =
                StringUtils.cleanPath(fileName);

        if (cleanFileName.contains("..")) {

            throw new FileStorageException(
                    "Invalid file path");
        }

        try {

            Path filePath =
                    uploadDirectory
                            .resolve(cleanFileName)
                            .normalize();

            // Prevent path traversal
            if (!filePath.startsWith(uploadDirectory)) {

                throw new FileStorageException(
                        "Invalid file path");
            }

            Resource resource =
                    new UrlResource(filePath.toUri());

            if (!resource.exists()
                    || !resource.isReadable()) {

                throw new FileStorageException(
                        "File not found: " + cleanFileName);
            }

            logger.info(
                    "File downloaded successfully: {}",
                    cleanFileName);

            return resource;

        } catch (IOException exception) {

            logger.error(
                    "Error while downloading file: {}",
                    cleanFileName,
                    exception);

            throw new FileStorageException(
                    "Could not download file");
        }
    }
}