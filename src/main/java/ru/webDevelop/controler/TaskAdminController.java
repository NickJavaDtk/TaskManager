package ru.webDevelop.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.webDevelop.configuration.BDConfig;
import ru.webDevelop.entity.Executor;
import ru.webDevelop.service.ExecutorService;
import ru.webDevelop.telegram.configuration.TelegramConfig;
import ru.webDevelop.telegram.entity.MessageDocument;
import ru.webDevelop.telegram.service.impl.FileServiceImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TaskAdminController {

    @Autowired
    private TelegramConfig telegramConfig;

    @Autowired
    private BDConfig bdConfig;

    @Autowired
    private ExecutorService service;

    @ModelAttribute
    private void addSettingToModel(Model model) {
       // List<MessageDocument> messageList = fileService.findAllMessageDocument( );
        List<String> settingBD = Arrays.asList(new String[]{bdConfig.getUri( ), bdConfig.getUsername( ), bdConfig.getPassword( )});
        List<String> settingTelegram = Arrays.asList(new String[]{telegramConfig.getName(), telegramConfig.getToken()});
        List<Executor> executorList = service.findAllExecutor();

        model.addAttribute("connectBD", settingBD);
        model.addAttribute("connectBot", settingTelegram);

    }

    @GetMapping
    private String show() {
        return "admin";
    }


//    private String[] getSetting() {
//        try (InputStream inputStream =
//                     getClass( ).getClassLoader( ).getResourceAsStream("/resources/application.yaml");
//             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
//
//        }
//
//    }




}
