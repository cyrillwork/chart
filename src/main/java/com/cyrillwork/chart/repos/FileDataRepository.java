package com.cyrillwork.chart.repos;

import com.cyrillwork.chart.models.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDataRepository extends JpaRepository<FileData, Long> {
}
