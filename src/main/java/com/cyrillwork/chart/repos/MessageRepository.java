package com.cyrillwork.chart.repos;

import com.cyrillwork.chart.models.FileData;
import com.cyrillwork.chart.models.Message;
import com.cyrillwork.chart.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Page<Message> findAll(Pageable pageable);

    @Transactional
    Page<Message> findAllByUser(User user, Pageable pageable);

    @Transactional
    Iterable<Message> findAllByFile(FileData file);

//    @Transactional
//    @Query(value = "SELECT * FROM user_messages WHERE file_id = ?0", nativeQuery = true)
//    Iterable<Message> findAllByFileByFileNative(Long id);
}
