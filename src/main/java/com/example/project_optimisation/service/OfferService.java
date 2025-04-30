package com.example.project_optimisation.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
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
        // Convertir l'ID de String à ObjectId
        ObjectId objectId = new ObjectId(id);

        // Créer la requête MongoDB pour rechercher par ID
        Query query = new Query();
        //query.addCriteria(Criteria.where("id").is(objectId));
        query.addCriteria(Criteria.where("id").is(id));

        // Effectuer la recherche via MongoTemplate
        return mongoTemplate.findOne(query, Offer.class);
    }
}
