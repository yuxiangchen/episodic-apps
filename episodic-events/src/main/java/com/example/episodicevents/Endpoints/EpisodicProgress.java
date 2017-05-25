package com.example.episodicevents.Endpoints;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by trainer8 on 5/24/17.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EpisodicProgress {
    public Long userId;
    public Long episodeId;
    public LocalDateTime createdAt;
    public int offset;
}
