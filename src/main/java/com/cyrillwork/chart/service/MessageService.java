package com.cyrillwork.chart.service;

import com.cyrillwork.chart.models.FileData;
import com.cyrillwork.chart.models.Message;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.properties.MainProperties;
import com.cyrillwork.chart.repos.FileDataRepository;
import com.cyrillwork.chart.repos.MessageRepository;
import com.cyrillwork.chart.service.excel.FillManager;
import com.cyrillwork.chart.service.excel.Layouter;
import com.cyrillwork.chart.service.excel.Writer;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private MainProperties mainProperties;

    public Page<Message> findAllMessages(Pageable pageable){
        return messageRepository.findAll(pageable);
    }

    public Page<Message> findAllByUser(User user, Pageable pageable){
        return messageRepository.findAllByUser(user, pageable);
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

    @Transactional
    public void makeExcelDocument(HttpServletResponse response) throws ClassNotFoundException
    {
        // 1. Create new workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 2. Create new worksheet
        HSSFSheet worksheet = workbook.createSheet("POI Worksheet");

        // 3. Define starting indices for rows and columns
        int startRowIndex = 0;
        int startColIndex = 0;

        // 4. Build layout
        // Build title, date, and column headers
        Layouter.buildReport(worksheet, startRowIndex, startColIndex);

        // 5. Fill report
        FillManager.fillReport(worksheet, startRowIndex, startColIndex, messageRepository.findAll());

        // 6. Set the response properties
        String fileName = "Messages.xls";
        response.setHeader("Content-Disposition", "inline; filename=" + fileName);
        // Make sure to set the correct content type
        response.setContentType("application/vnd.ms-excel");

        //7. Write to the output stream
        Writer.write(response, worksheet);
    }
}
