package com.example.project_optimisation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_optimisation.service.RecoService;

import java.util.List;

@RestController
public class RecoController {

    private final RecoService recoService;

    public RecoController(RecoService recoService) {
        this.recoService = recoService;
    }

    @GetMapping("/reco")
    public List<String> getReco(@RequestParam String city, @RequestParam int k) {
        return recoService.getRecommendedCities(city, k);
    }
}
