package com.example.project_optimisation.repository;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.project_optimisation.model.City;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

@Repository
public interface CityRepository extends Neo4jRepository<City, Long> {

   // @Query("MATCH (c:City {code:$city})-[:NEAR]->(n:City) " +
     //      "RETURN n.code AS city ORDER BY n.weight DESC LIMIT $k")
     @Query("MATCH (c:City {code:$city})-[r:NEAR]->(n:City) WITH n, r.weight AS weight ORDER BY weight DESC LIMIT $k RETURN n.code AS city")
    List<RecommendationProjection> findRecommendedCities(@Param("city") String city, @Param("k") int k);

    interface RecommendationProjection {
        String getCity(); // alias de n.code
    }
}
/*
@Repository
public interface CityRepository extends Neo4jRepository<City, Long> {
    // méthodes personnalisées si besoin
}*/

