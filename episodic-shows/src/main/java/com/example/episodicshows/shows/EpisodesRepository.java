package com.example.episodicshows.shows;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by trainer8 on 5/18/17.
 */
public interface EpisodesRepository extends CrudRepository<Episodes, Long>{
    List<Episodes> findByShowId(Long showId);
    Episodes findAllById(Long Id);
}
