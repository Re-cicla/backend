package es.recicla.repository;

import es.recicla.model.Container;
import es.recicla.model.ContainerType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContainerRepository {
    public List<Container> findAll(){
        ArrayList<Container> list= new ArrayList<Container>();

        list.add(new Container("XX1", ContainerType.ORGANICO));
        list.add(new Container("XX2", ContainerType.RESIDUO));
        list.add(new Container("XX3", ContainerType.RESIDUO));
        list.add(new Container("XX4", ContainerType.ORGANICO));

        return list;
    }
}
