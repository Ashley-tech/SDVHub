package com.example.project_optimisation.service;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.project_optimisation.document.Offer;
import com.example.project_optimisation.repository.OfferRepository;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Cacheable(value = "offers", key = "#id", unless = "#result == null", cacheManager = "cacheManager")
    public Offer getOfferById(String id) {

        // Utiliser MongoTemplate pour récupérer le document
        return offerRepository.findById(new ObjectId(id)).orElse(null);

    }
}