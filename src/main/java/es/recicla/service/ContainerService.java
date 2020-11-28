package es.recicla.service;

import es.recicla.model.Container;
import es.recicla.repository.ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContainerService {

    @Autowired
    private ContainerRepository repository;

    public List<Container> list() {
        Iterable<Container> users = repository.findAll();
        List<Container> list = new ArrayList<Container>();
        users.forEach(list::add);
        return list;
    }

}