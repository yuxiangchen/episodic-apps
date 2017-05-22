package com.example.episodicevents.Endpoints;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by trainer8 on 5/22/17.
 */
@RestController
@RequestMapping("/")
public class EndpointsController {
    EndpointsRepository endpointsRepository;

    public EndpointsController(EndpointsRepository endpointsRepository) {
        this.endpointsRepository = endpointsRepository;
    }

    @PostMapping
    public void createEndpoints(@RequestBody Endpoints endpoints){
        this.endpointsRepository.save(endpoints);
    }

    @GetMapping("/recent")
    public List<Endpoints> getEndpoints(){
        return this.endpointsRepository.findAll();
    }
}
