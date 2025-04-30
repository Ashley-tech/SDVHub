package com.example.project_optimisation.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_optimisation.document.Offer;
import com.example.project_optimisation.dto.OfferSummary;
import com.example.project_optimisation.model.Erreur;
import com.example.project_optimisation.service.OfferService;

@RestController
public class APIController {
    @GetMapping("/error/{status}")
    public Erreur error(@PathVariable int status){
        return new Erreur(status,"");
    }

    @GetMapping("/")
    public String home() {
        return "API REST Spring Boot est opérationnelle 🚀";
    }

    @Autowired
    private OfferService offerService;

    @GetMapping("/offers/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable String id) {
        Offer offer = offerService.getOfferById(id);
        return offer != null ? ResponseEntity.ok(offer) : ResponseEntity.notFound().build();
    }

    @GetMapping("/offers")
    public List<OfferSummary> searchOffers(
        @RequestParam String from,
        @RequestParam String to,
        @RequestParam(required = false) int limit
    ) {
        System.out.println("Limites : "+limit);
        return offerService.searchOffers(from, to, limit);
    }
        
    /*public List<OfferSummary> getSampleOffers(@RequestParam String from,
    @RequestParam String to,
    @RequestParam(required = false, defaultValue="10") int limit) {
        return offerService.getLimitedOffers(from,to,limit);
    }*/

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("session:" + uuid, userId, Duration.ofSeconds(900));

        Map<String, Object> response = new HashMap<>();
        response.put("token", uuid);
        response.put("expires_in", 900);
        return ResponseEntity.ok(response);
    }
}

class User{
    public String id;
    public User(String id){this.id = id;}
    public String getId(){
        return id;
    }
}
