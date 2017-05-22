package com.example.episodicshows.viewings;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by trainer8 on 5/20/17.
 */
public interface ViewingsRepository extends CrudRepository<Viewings, Long>{
    List<Viewings> findAllByUserIdOrderByUpdatedAtDesc(Long userId);
    Viewings findAllByUserIdAndShowId(Long userId, Long showId);
}
