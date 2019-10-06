package com.cyrillwork.chart.models;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Data
@Table(name="file_data")
@Slf4j
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private byte[] file;

    public void setMultipartFile(MultipartFile multipartFile) {
        try {
            this.file = multipartFile.getBytes();
        } catch (IOException e) {
            log.error(String.valueOf(e.getStackTrace()));
        }
    }

}

