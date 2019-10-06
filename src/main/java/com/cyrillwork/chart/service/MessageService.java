package com.cyrillwork.chart.service;

import com.cyrillwork.chart.models.FileData;
import com.cyrillwork.chart.models.Message;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.properties.MainProperties;
import com.cyrillwork.chart.repos.FileDataRepository;
import com.cyrillwork.chart.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private MainProperties mainProperties;

    public Iterable<Message> findAllMessages(){
        return messageRepository.findAll();
    }

    public Iterable<Message> findAllByUser(User user){
        return messageRepository.findAllByUser(user);
    }

    public void saveMessage(Message message, User user, MultipartFile file) throws IOException {
        message.setUser(user);

        if((file != null) && (!file.getOriginalFilename().isEmpty()))
        {
            String path = mainProperties.getUploadPath();
            File dir = new File(path);
            if(!dir.exists())
            {
                dir.mkdir();
            }
            FileData fileData = new FileData();
            fileData.setMultipartFile(file);
            message.setFile(fileData);
            saveFileData(fileData);

            String fileName =  String.format("%s%s.%s.%s",
                    mainProperties.getUploadPath(),
                    UUID.randomUUID().toString(),
                    fileData.getId(),
                    file.getOriginalFilename());

            file.transferTo(new File(fileName));
            message.setFileName(fileName);
        }

        messageRepository.save(message);
    }

    public void saveFileData(FileData fileData) {fileDataRepository.save(fileData); }

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

    public byte[] getFileDate(String file_id) {
        FileData file = null;
        Long id = Long.valueOf(file_id);

        Message m = null;
        FileData f = new FileData();
        f.setId(id);
        Iterable<Message> mess =  messageRepository.findAllByFile(f);
        if(mess.iterator().hasNext()) {
            m = mess.iterator().next();
            file = m.getFile();
        }

//        Iterable<Message> mess =  messageRepository.findAllByFileByFileNative(id);
//        if(mess.iterator().hasNext()) {
//            m = mess.iterator().next();
//            file = m.getFile();
//        }


//        Iterable<Message> all = messageRepository.findAll();
//        for(Message iii: all){
//            FileData fff = iii.getFile();
//            if((fff != null) && (fff.getId() == id))
//            {
//                file = fff;
//                break;
//            }
//        }

        return (file != null) ? file.getFile() : null;
    }

    public Resource fileAsResource(File file) {
        FileSystemResource f = new FileSystemResource(file);
        return f.createRelative(file.getName());
    }

    public ResponseEntity<Resource> uploadFile(String filename) {
        ResponseEntity<Resource> body = null;
        int index1 = filename.indexOf(".") + 1;
        int index2 = filename.indexOf(".", index1);

        String file_id = filename.substring(index1, index2);
        String name = filename.substring(index2 + 1);

        byte[] bytes = getFileDate(file_id);

        if(bytes == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            File file = File.createTempFile("file_id_" + file_id, null, null);
            FileOutputStream out = new FileOutputStream( file );
            out.write( bytes );
            out.close();

            String new_file = file.getPath().substring(0, file.getPath().lastIndexOf(File.separator) + 1) + name;
            //boolean resRename = file.renameTo(new File(new_file));
            Files.move( file.toPath(), Paths.get(new_file), StandardCopyOption.REPLACE_EXISTING);
            Resource resource = fileAsResource(new File(new_file));

            body = ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + name + "\"").body(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }
}
