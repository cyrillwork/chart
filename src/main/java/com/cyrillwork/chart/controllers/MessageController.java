package com.cyrillwork.chart.controllers;

import com.cyrillwork.chart.models.Message;
import com.cyrillwork.chart.models.User;
import com.cyrillwork.chart.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public String show_messages( Model model,
                                 @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){
        prepareModel(model, pageable);
        return "messages.html";
    }

    @GetMapping("/messages/excel")
    public void getExcelFile(HttpServletResponse response) {
        try {
            messageService.makeExcelDocument(response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //return "excel.html";
    }

    @GetMapping(value = "/messages/{filename:.+}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename){
        return messageService.uploadFile(filename);
    }

    @PostMapping("/messages")
    public  String input_message(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult result,
            Model model,
            @RequestParam("file")MultipartFile file,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) throws IOException {
        messageService.saveMessage(message, user, file);
        prepareModel(model, pageable);
        return "messages.html";
    }

    public void prepareModel(Model model, Pageable pageable){

        Page<Message> pages = messageService.findAllMessages(pageable);

        model.addAttribute("url", "/messages");
        model.addAttribute("all_messages", pages);
        model.addAttribute("message", new Message());

        int totalPages = pages.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        //model.addAttribute("upload_path", mainProperties.getUploadPath());
    }

}
