package ru.webDevelop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.webDevelop.entity.Responsible;
import ru.webDevelop.repository.ResponsibleRepository;

@Service
public class ResponsibleService {
    @Autowired
    private final ResponsibleRepository repository;

    public ResponsibleService(ResponsibleRepository repository) {
        this.repository = repository;
    }

    public void save(Responsible responsible) {
        repository.save(responsible);
    }

    public void delete(Responsible responsible) {
        repository.delete(responsible);
    }

    public Responsible findByUsername(String username) {
        Responsible responsible = repository.findByUsername(username);
        if (responsible != null) {
           return responsible;
        } else {
            throw new UsernameNotFoundException("Пользователь " + responsible + " не найден");
        }
    }


}
