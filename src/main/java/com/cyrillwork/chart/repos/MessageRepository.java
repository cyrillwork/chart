package com.cyrillwork.chart.repos;

import com.cyrillwork.chart.models.Message;
import com.cyrillwork.chart.models.User;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Iterable<Message> findAllByUser(User user);
}
