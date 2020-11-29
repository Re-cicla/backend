package es.recicla.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

public class ContainerRequest {

    @JsonProperty("location")
    private String location;

    @JsonProperty("containerType")
    private String containerType;


    public ContainerRequest(String location, String containerType) {
        this.location = location;
        this.containerType = containerType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }
}
