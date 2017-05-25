package com.example.episodicevents.Endpoints;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * Created by trainer8 on 5/22/17.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Play.class, name = "play"),
        @JsonSubTypes.Type(value = Pause.class, name = "pause"),
        @JsonSubTypes.Type(value = FastForward.class, name = "fastForward"),
        @JsonSubTypes.Type(value = Rewind.class, name = "rewind"),
        @JsonSubTypes.Type(value = Progress.class, name = "progress"),
        @JsonSubTypes.Type(value = Scrub.class, name = "scrub")
})
public class Endpoints {
    @Id
    private String id;
    private Long userId;
    private Long showId;
    private Long episodeId;
    private LocalDateTime createdAt;

    public Endpoints(Long userId, Long showId, Long episodeId, LocalDateTime createdAt) {
        this.userId = userId;
        this.showId = showId;
        this.episodeId = episodeId;
        this.createdAt = createdAt;
    }

    public String getType() {
        return null;
    }
}
