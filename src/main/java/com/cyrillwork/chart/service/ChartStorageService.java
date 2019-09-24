package com.cyrillwork.chart.service;

import com.cyrillwork.chart.properties.MainProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class ChartStorageService implements StorageService{

    @Autowired
    private MainProperties mainProperties;

    @Autowired
    private MessageService messageService;

    @Override
    public void init() {
    }

    @Override
    public void store(MultipartFile file) {
    }

    @Override
    public Stream<Path> loadAll() throws IOException {
        String uploadPath = mainProperties.getUploadPath();
        File uploadDir = new File(uploadPath);

        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        Path path = Paths.get(uploadPath);
        Stream<Path> pathStream = Files.walk(path);

        return pathStream;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String code) {
        String filename = messageService.FindFileByCode(code);
        FileSystemResource f = new FileSystemResource(mainProperties.getUploadPath());
        return f.createRelative(filename);
    }

    @Override
    public void deleteAll() {
    }
}
