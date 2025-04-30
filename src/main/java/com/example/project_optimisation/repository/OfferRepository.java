package com.example.project_optimisation.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.project_optimisation.document.Offer;

public interface OfferRepository extends MongoRepository<Offer, String> {}

