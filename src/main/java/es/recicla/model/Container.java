package es.recicla.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Container {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private ContainerType type;

    public Container(String name, ContainerType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ContainerType getType() {
        return type;
    }
}
