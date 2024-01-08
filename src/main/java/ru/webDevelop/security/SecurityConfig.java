package ru.webDevelop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.webDevelop.entity.Executor;
import ru.webDevelop.repository.ExecutorRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(  );
    }

    @Bean
    public UserDetailsService userDetailsService(ExecutorRepository repository) {
        return username -> {
            Executor user =  repository.findByUsername(username);
            if (user != null) {
                return  user;
            }
            throw  new UsernameNotFoundException("Пользователя с именем " + user + " не существует");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/",
                                "/hello", "/tasklist", "/appeal",
                                "/styles/**", "/static/**",
                                "/images/**", "/register", "/admin").permitAll()
                        //.requestMatchers("/admin").hasAnyRole("USER")
                        .anyRequest().authenticated()
                )

                .formLogin( (form) -> form
                        .loginPage("/login")
                        .permitAll()

                )
                .build();

    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        List<UserDetails> userList = new ArrayList<>();
//        userList.add(new User("adminLpu", encoder.encode("adminTask" )
//                , Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//        return new InMemoryUserDetailsManager(userList);
//    }
}
