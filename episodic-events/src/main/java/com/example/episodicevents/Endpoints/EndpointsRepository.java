package com.example.episodicevents.Endpoints;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by trainer8 on 5/22/17.
 */
public interface EndpointsRepository extends MongoRepository<Endpoints, Long> {
    Endpoints findByUserId(Long userId);
}
