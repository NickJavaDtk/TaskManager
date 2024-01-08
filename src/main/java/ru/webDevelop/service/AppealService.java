package ru.webDevelop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.webDevelop.entity.Appeal;
import ru.webDevelop.repository.AppealRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Service
public class AppealService {

    @Autowired
    private final AppealRepository appealRepository;


    public AppealService(AppealRepository appealRepository) {
        this.appealRepository = appealRepository;
    }

    public List<Appeal> findAllExecutor() {
        Iterable<Appeal> iterList = appealRepository.findAll();
        List<Appeal> appealList = new ArrayList<>();
        for (Iterator<Appeal> it = iterList.iterator( ); it.hasNext( ); ) {
            Appeal appealEntity = it.next( );
            appealList.add(appealEntity);
        }
        return appealList;
    }

    public Appeal findById(Long id) {
        Appeal appeal =  appealRepository.findById(id).orElse(null);
        return appeal;
    }

    public void saveAppeal(Appeal appeal) {
        appealRepository.save(appeal);
    }
}
