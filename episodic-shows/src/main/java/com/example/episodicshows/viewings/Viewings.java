package com.example.episodicshows.viewings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Created by trainer8 on 5/20/17.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Viewings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long showId;
    private Long userId;
    private Long episodeId;
    private LocalDateTime updatedAt;
    private int timecode;

    public Viewings(Long showId, Long userId, Long episodeId, LocalDateTime updatedAt, int timecode) {
        this.showId = showId;
        this.userId = userId;
        this.episodeId = episodeId;
        this.updatedAt = updatedAt;
        this.timecode = timecode;
    }



}
