package com.example.project_optimisation.dto;

import java.math.BigDecimal;
import java.util.List;

import com.example.project_optimisation.document.Offer;
import com.example.project_optimisation.document.Offer.Leg;

public class OfferSummary {
    private String id;
    private String provider;
    private BigDecimal price;
    private String currency;
    private List<Offer.Leg> legs;
    public void setProvider(String provider) {
        this.provider = provider;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setId(String id){
        this.id = id;
    }
    public BigDecimal getPrice(){
        return price;
    }
    public String getId(){
        return id;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }
}
