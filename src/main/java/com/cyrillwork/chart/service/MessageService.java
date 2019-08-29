package com.cyrillwork.chart.service;

import com.cyrillwork.chart.models.Message;
import com.cyrillwork.chart.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Iterable<Message> findAllMessages(){
        return messageRepository.findAll();
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

}
