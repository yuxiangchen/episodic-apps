package com.example.episodicshows.shows;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by trainer8 on 5/18/17.
 */
@RestController
@RequestMapping("/shows")
public class ShowsController {
    private final ShowsRepository repository;

    public ShowsController(ShowsRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Shows createShows(@RequestBody Shows shows){
        return this.repository.save(shows);
    }

    @GetMapping
    public ArrayList<Shows> getShows() {
        return (ArrayList<Shows>) this.repository.findAll();
    }


}
