package ru.webDevelop.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.webDevelop.entity.UserClass;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/tasklist")
public class TestThymeleafController {
    @ModelAttribute
    public void addListUserToModel(Model model) {
        List<UserClass> userClassList = Arrays.asList(new UserClass(1L, "Pety", "Ostanovite", "Chykcha"),
                new UserClass(2L, "Basya", "Shkola", "Pyku"),
                new UserClass(3L, "Edgar", "Vampir", "Nau"));
        model.addAttribute("userclass", userClassList);
    }

    @GetMapping
    public String showTable() {

        return "tasklist";
    }

//    @GetMapping("/list")
//    public ModelAndView getModelList() {
//        ModelAndView modelAndView = new ModelAndView( "tablelist" );
//        List<UserClass> userClassList = Arrays.asList(new UserClass(1L, "Pety", "Ostanovite", "Chykcha"),
//                new UserClass(2L, "Basya", "Shkola", "Pyku"),
//                new UserClass(3L, "Edgar", "Vampir", "Nau"));
//        modelAndView.addObject("userclass", userClassList);
//        return modelAndView;
//    }

//    @RequestMapping("/list")
//    public String getListUser(Model model) {
//        List<UserClass> userClassList = Arrays.asList(new UserClass(1L, "Pety", "Ostanovite", "Chykcha"),
//                new UserClass(2L, "Basya", "Shkola", "Pyku"),
//                new UserClass(3L, "Edgar", "Vampir", "Nau"));
//        model.addAttribute("userclass", userClassList);
//        return "tablelist";
//    }


}
