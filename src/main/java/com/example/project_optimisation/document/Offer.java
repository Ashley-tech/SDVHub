package com.example.project_optimisation.document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "offers")
public class Offer {
    //private ObjectId _id;

    @Id
    private String id;
    private String from;
    private String to;
    private Date departDate;
    private Date returnDate;
    private String provider;
    private BigDecimal price;
    private String currency;
    private List<Leg> legs;
    private Hotel hotel;
    private Activity activity;

    public Offer(){
        
    }

    public String getID(){
        return id;
    }
    public String getFrom(){
        return from;
    }

    public String getTo(){return to;}

    public Date getDateDepart(){
        return departDate;
    }

    public Date getDateRetour(){
        return returnDate;
    }

    @Data
    public static class Activity {
        public String title;
        public BigDecimal price;
    }
    
    @Data
    public static class Leg {
        public String flightNum;
        public String dep;
        public String arr;
        public int duration;
    }
    
    @Data
    public static class Hotel {
        public String name;
        public int nights;
        public BigDecimal price;
    }
}
