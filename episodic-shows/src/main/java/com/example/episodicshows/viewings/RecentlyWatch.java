package com.example.episodicshows.viewings;

import com.example.episodicshows.shows.EpisodesShow;
import com.example.episodicshows.shows.Shows;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by trainer8 on 5/20/17.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecentlyWatch {
    @JsonProperty("show")
    private Shows shows;
    @JsonProperty("episode")
    private EpisodesShow episodesShow;
//    @JsonFormat( pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private LocalDateTime updatedAt;
    private int timecode;
}
