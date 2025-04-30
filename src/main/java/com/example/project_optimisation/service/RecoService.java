package com.example.project_optimisation.service;

import org.springframework.stereotype.Service;

import com.example.project_optimisation.repository.CityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecoService {

    private final CityRepository cityRepository;

    public RecoService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<String> getRecommendedCities(String cityCode, int k) {
        return cityRepository.findRecommendedCities(cityCode, k)
                             .stream()
                             .map(CityRepository.RecommendationProjection::getCity)
                             .collect(Collectors.toList());
    }
}
