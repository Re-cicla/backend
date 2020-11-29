package es.recicla;

import es.recicla.model.Container;
import es.recicla.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class ReciclaApplication {

    @Autowired
    private ContainerService containerService;

    @GetMapping("/container")
    public ResponseEntity<Container> container(){
        List<Container> containers = containerService.list();
        return new ResponseEntity(containers, HttpStatus.OK);
    }

    @GetMapping("/manolo")
    public String manolo(){
        return "8===========D Manolo";
    }

    public static void main(String[] args) {
        SpringApplication.run(ReciclaApplication.class, args);
    }

}
