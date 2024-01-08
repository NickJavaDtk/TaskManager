package ru.webDevelop.security;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.webDevelop.repository.AppealRepository;
import ru.webDevelop.repository.ExecutorRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private ExecutorRepository repository;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(ExecutorRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String getRegisterExecutor(@Valid @ModelAttribute("form") RegistrationForm form, BindingResult binding) {
        if (binding.hasErrors()) {
            return "registration";
        }
        repository.save(form.getExecutor(passwordEncoder));
        String s = null;
        return "redirect:/login";
    }


}
