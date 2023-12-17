package ru.webDevelop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;
import ru.webDevelop.entity.Responsible;

public interface ResponsibleRepository extends CrudRepository<Responsible, Long> {
    Responsible findByUsername(String username);
}
