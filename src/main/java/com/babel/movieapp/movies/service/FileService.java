package com.babel.movieapp.movies.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileService {

    private Path uploadedFilesPath;

    public FileService() throws IOException {
        this.uploadedFilesPath = createTempDir();
    }

    private Path createTempDir() throws IOException {
        return Files.createTempDirectory("spring-uploaded-files");
    }

    public ResponseEntity<Resource> responseEntity(String file) {
        try {
            Resource resource;
            MediaType contentType = MediaType.IMAGE_JPEG;
            if (file.isEmpty())
                resource = new InputStreamResource(getClass().getResourceAsStream("/static/img/movieposter.jpg"));
            else {
                Path path = resolveFilePath(file);
                resource = new FileSystemResource(path);
                contentType =
                        Optional.ofNullable(Files.probeContentType(path)).map(MediaType::valueOf).orElse(MediaType.APPLICATION_OCTET_STREAM);
            }
            return ResponseEntity.ok().contentType(contentType).body(resource);
        } catch (IOException e) {
            throw wrapEx(e);
        }
    }

    private RuntimeException wrapEx(Exception e) {
        return new RuntimeException(e);
    }

    public void saveFile(MultipartFile file) {
        try {
            Path path = resolveFilePath(file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw wrapEx(e);
        }
    }

    private Path resolveFilePath(String fileName) {
        return uploadedFilesPath.resolve(uploadedFilesPath.resolve(Paths.get(fileName)));
    }
}
