package ru.webDevelop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.webDevelop.entity.Appeal;

import java.util.Optional;

public interface AppealRepository extends CrudRepository<Appeal, Long> {

}
