package ru.webDevelop.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.webDevelop.telegram.entity.MessageDocument;
import ru.webDevelop.telegram.service.impl.FileServiceImpl;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/appeal")
public class MessageAppealController {

    @Autowired
    private FileServiceImpl fileService;

    @ModelAttribute
    private void addMessageListToModel(Model model) {
        List<MessageDocument> messageList = fileService.findAllMessageDocument();

        model.addAttribute("messagelist", messageList);
    }

    @GetMapping
    private String show() {
        return "appeal";
    }
}
