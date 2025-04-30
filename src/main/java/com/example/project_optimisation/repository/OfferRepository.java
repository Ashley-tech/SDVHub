package com.example.project_optimisation.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.project_optimisation.document.Offer;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface OfferRepository extends MongoRepository<Offer, String> {
    List<Offer> findByFromAndToOrderByPriceAsc(String from, String to, Pageable pageable);
}

