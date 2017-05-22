package com.example.episodicshows.shows;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by trainer8 on 5/18/17.
 */
public interface ShowsRepository extends CrudRepository<Shows, Long>{
    Shows findById(Long id);
    Shows findByName(String name);
}
