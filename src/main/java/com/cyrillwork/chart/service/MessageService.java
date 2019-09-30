package com.cyrillwork.chart.service;

import com.cyrillwork.chart.models.Message;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Iterable<Message> findAllMessages(){
        return messageRepository.findAll();
    }

    public Iterable<Message> findAllByUser(User user){
        return messageRepository.findAllByUser(user);
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public void deleteMessage(Message message) { messageRepository.delete(message);}

    public String FindFileByCode(String code)
    {
        String filename = null;
        Iterable<Message> messages = messageRepository.findAll();

        for (Message message: messages) {
            String str1 = message.getFileName();
            if(str1 != null && str1.indexOf(code) != -1)
            {
                filename = message.getFileName().substring(message.getFileName().lastIndexOf(File.separator) + 1);
                break;
            }
        }
        return filename;
    }
}
