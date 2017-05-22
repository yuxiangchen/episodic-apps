package com.example.episodicshows.shows;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by trainer8 on 5/18/17.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Episodes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    private Long showId;
    private int seasonNumber;
    private int episodeNumber;

    public Episodes(Long showId, int seasonNumber, int episodeNumber) {
        this.showId = showId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
    }

    /*
    @ManyToOne
    @JoinColumn(name = "show_id")
    private Shows shows;
*/
}
