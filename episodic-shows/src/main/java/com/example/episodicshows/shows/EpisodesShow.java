package com.example.episodicshows.shows;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by trainer8 on 5/18/17.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EpisodesShow {
    private Long id;
    private int seasonNumber;
    private int episodeNumber;
    private String title;

}
