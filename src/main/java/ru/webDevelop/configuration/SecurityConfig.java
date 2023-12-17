package ru.webDevelop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.webDevelop.entity.Responsible;
import ru.webDevelop.repository.ResponsibleRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(  );
    }

    @Bean
    public UserDetailsService userDetailsService(ResponsibleRepository repository) {
        return username -> {
            Responsible user =  repository.findByUsername(username);
            return (UserDetails) user;
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/", "/hello", "/tasklist").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin( (form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .build();

    }
}
