package com.example.project_optimisation.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.project_optimisation.document.Offer;
import com.example.project_optimisation.dto.OfferSummary;
import com.example.project_optimisation.repository.OfferRepository;
import com.google.gson.Gson;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

     @Autowired
    private StringRedisTemplate redisTemplate;

    private static final Gson gson = new Gson();

    public List<Offer> getLimitedOffers(String from, String to, int limit) {
        Query query = new Query().limit(limit);
        query.addCriteria(Criteria.where("from").is(from).where("to").is(to));
        return mongoTemplate.find(query, Offer.class);
    }
    

    public List<OfferSummary> searchOffers(String from, String to, int limit) {
        String redisKey = String.format("offers:%s:%s", from, to);
        String cached = redisTemplate.opsForValue().get(redisKey);

        if (cached != null) {
            try {
                String json = decompress(cached);
                OfferSummary[] summaries = gson.fromJson(json, OfferSummary[].class);
                return Arrays.asList(summaries);
            } catch (Exception e) {
                // fallback : ignore cache si invalide
            }
        }

        List<Offer> offers = offerRepository
            .findByFromAndToOrderByPriceAsc(from, to, PageRequest.of(0, limit));

        List<OfferSummary> result = offers.stream().map(o -> {
            OfferSummary s = new OfferSummary();
            s.setId(o.getID());
            s.setProvider(o.getProvider());
            s.setPrice(o.getPrice());
            s.setCurrency(o.getCurrency());
            s.setLegs(o.getLegs());
            return s;
        }).collect(Collectors.toList());

        try {
            String compressed = compress(gson.toJson(result));
            redisTemplate.opsForValue().set(redisKey, compressed, 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            // log erreur compression
        }

        return result;
    }

    private String compress(String str) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(baos)) {
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private String decompress(String base64Str) throws IOException {
        byte[] data = Base64.getDecoder().decode(base64Str);
        try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(data));
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[256];
            int len;
            while ((len = gzip.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            return out.toString(StandardCharsets.UTF_8);
        }
    }

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
