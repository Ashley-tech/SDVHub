package com.example.project_optimisation.document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "offers")
public class Offer {
    @Id
    private ObjectId _id;

    private String id;
    private String from;
    private String to;
    private Instant departDate;
    private Instant returnDate;
    private String provider;
    private BigDecimal price;
    private String currency;
    private List<Leg> legs;
    private Hotel hotel;
    private Activity activity;

    public Offer(String id){
        this.id = id;
    }

    // Tu peux ajouter un getter et un setter pour ObjectId
    public String getIdAsString() {
        return _id != null ? _id.toHexString() : null;
    }

    public void setIdFromString(String _id) {
        this._id = new ObjectId(_id);
    }

    public String getID(){
        return id;
    }
    public String getFr(){
        return from;
    }

    public String getTo(){return to;}

    public Instant getDateDepart(){
        return departDate;
    }

    public Instant getDateRetour(){
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
