package com.example.episodicevents.Endpoints;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Created by trainer8 on 5/22/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class Progress extends Endpoints{
    HashMap<String, Object> data;

    public Progress(Long userId, Long showId, Long episodeId, LocalDateTime createdAt, HashMap<String, Object> data) {
        super(userId, showId, episodeId, createdAt);
        this.data = data;
    }

    public String getType() {
        return "play";
    }
}
