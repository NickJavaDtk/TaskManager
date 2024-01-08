package ru.webDevelop.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.webDevelop.entity.Executor;

@Data
public class RegistrationForm {
    @NotBlank(message = "Логин не может быть пустым")
    private  String username;
    @NotNull
    @Size(min=6, message = "Минимальная длина пароля 6 символов")
    private  String password;
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String name;

    private String position;

    public Executor getExecutor(PasswordEncoder encoder) {

        return new Executor(username, encoder.encode(password), name, position);
    }
}
