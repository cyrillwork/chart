package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.Message;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.properties.MainProperties;
import com.cyrillwork.chart.service.ChartStorageService;
import com.cyrillwork.chart.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private MainProperties mainProperties;

    @Autowired
    private ChartStorageService storageService;

    @GetMapping("/messages")
    public String show_messages(Model model){
        prepareModel(model);
        return "messages.html";
    }

    @GetMapping("/messages/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename){
        Resource file = storageService.loadAsResource(filename);
        ResponseEntity<Resource> body = ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        return body;
    }


    @PostMapping("/messages")
    public  String input_message(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult result,
            Model model,
            @RequestParam("file")MultipartFile file
            //@RequestParam Map<String, String> form
    ) throws IOException {

        message.setUser(user);

        if(file != null)
        {
            File dir = new File(mainProperties.getUploadPath());
            if(!dir.exists())
            {
                dir.mkdir();
            }

            String fileName =  mainProperties.getUploadPath() + UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            file.transferTo(new File(fileName));
            message.setFileName(fileName);

        }
        messageService.saveMessage(message);

        prepareModel(model);
        return "messages.html";
    }

    public void prepareModel(Model model){
        model.addAttribute("all_messages", messageService.findAllMessages());
        model.addAttribute("message", new Message());
        model.addAttribute("upload_path", mainProperties.getUploadPath());
    }

}
