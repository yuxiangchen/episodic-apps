package com.example.episodicshows.users;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by trainer8 on 5/17/17.
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersRepository repository;

    public UsersController(UsersRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Users createUser(@RequestBody Users users) {
        return this.repository.save(users);
    }

    @GetMapping
    public ArrayList<Users> getUsers() {
        return (ArrayList<Users>) this.repository.findAll();
    }
}
