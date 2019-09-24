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

    public String FindFileByCode(String code)
    {
        String filename = null;
        Iterable<Message> messages = messageRepository.findAll();

        for (Message message: messages) {
            if(message.getFileName().indexOf(code) != -1)
            {
                filename = message.getFileName().substring(message.getFileName().lastIndexOf('/') + 1);
                break;
            }
        }
        return filename;
    }
}
