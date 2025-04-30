package com.example.project_optimisation.model;

import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class City {
    @Id @GeneratedValue
    private Long id;
    private String code;
    private String name;
    private String country;

    /*@Relationship(type = "NEAR")
    private List<NearRelation> near;*/

    public City(String code, String name, String country){
        this.code = code;
        this.name = name;
        this.country = country;
    }

    public Long getId(){
        return id;
    }
    public String getCode(){
        return code;
    }

    public String getName(){return name;}

    public String getCountry(){
        return country;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCountry(String country){this.country = country;}
    /*public List<NearRelation> getNear(){
        return near;
    }*/
}
