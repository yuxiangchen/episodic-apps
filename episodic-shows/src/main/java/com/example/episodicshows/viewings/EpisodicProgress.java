package com.example.episodicshows.viewings;

import lombok.*;

import java.util.Date;

/**
 * Created by trainer8 on 5/23/17.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EpisodicProgress {
    public Long userId;
    public Long episodeId;
    public Date createdAt;
    public int offset;
}
