package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.service.FileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(
        name = "File",
        description = "File upload and download APIs"
)
public class FileController {

    @Autowired
    private FileService fileService;

    // FILE UPLOAD
    @PostMapping(
            value = "/api/file/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(
            summary = "Upload a file",
            description = "Uploads a file and stores it in the uploads folder"
    )
    @ApiResponse(
            responseCode = "200",
            description = "File uploaded successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid or empty file"
    )
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile files) {

        String fileName =
                fileService.uploadFile(files);

        return new ResponseEntity<>(
                "File uploaded successfully: " + fileName,
                HttpStatus.OK
        );
    }

   
 // FILE DOWNLOAD
 @GetMapping("/api/file/download/{fileName}")
 @Operation(
         summary = "Download a file",
         description = "Downloads a file from the uploads folder"
 )
 @ApiResponse(
         responseCode = "200",
         description = "File downloaded successfully"
 )
 @ApiResponse(
         responseCode = "404",
         description = "File not found"
 )
 public ResponseEntity<Resource> downloadFile(
         @PathVariable String fileName) {

     Resource resource =
             fileService.downloadFile(fileName);

     return ResponseEntity.ok()
             .header(
                     HttpHeaders.CONTENT_DISPOSITION,
                     "inline; filename=\"" +
                             resource.getFilename() + "\""
             )
             .contentType(MediaType.IMAGE_JPEG)
             .body(resource);
 }


}