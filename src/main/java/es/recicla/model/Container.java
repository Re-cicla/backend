package es.recicla.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

public class Container {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private ContainerType type;

    public Container(String name, ContainerType type) {
        this.id = (new Random()).nextInt();
        this.name = name;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ContainerType getType() {
        return type;
    }
}
