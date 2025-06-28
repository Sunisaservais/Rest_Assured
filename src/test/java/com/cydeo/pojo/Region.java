package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Region {

    @JsonProperty("region_id") //hey Jackson, find region_id from JSON response and set value to this variable
    private int regionId;

    @JsonProperty("region_name")
    private String regionName;

    private List<Link> links;
}
