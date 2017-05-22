package com.example.episodicevents.Endpoints;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Created by trainer8 on 5/22/17.
 */
public class Rewind extends Endpoints{
    HashMap<String, Object> data;

    public Rewind(Long userId, Long showId, Long episodeId, LocalDateTime createdAt, HashMap<String, Object> data) {
        super(userId, showId, episodeId, createdAt);
        this.data = data;
    }

    public String getType() {
        return "progress";
    }
}
