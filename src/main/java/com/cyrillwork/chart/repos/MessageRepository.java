package com.cyrillwork.chart.repos;

import com.cyrillwork.chart.models.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
