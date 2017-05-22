package com.example.episodicshows.shows;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trainer8 on 5/18/17.
 */
@RestController

public class EpisodesController {

    private final EpisodesRepository repository;
    public EpisodesController(EpisodesRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/shows/{id}/episodes")
    public EpisodesShow createUsers(@RequestBody Episodes episodes, @PathVariable Long id){

        this.repository.findByShowId(id);

        episodes.setShowId(id);
        Episodes episodes1 = this.repository.save(episodes);

        EpisodesShow episodesShow = new EpisodesShow();
        episodesShow.setId(episodes1.getId());
        episodesShow.setEpisodeNumber(episodes.getEpisodeNumber());
        episodesShow.setSeasonNumber(episodes.getSeasonNumber());
        episodesShow.setTitle("S" + episodes.getSeasonNumber() + " E" + episodes.getEpisodeNumber());

        return episodesShow;
    }

    @GetMapping("/shows/{id}/episodes")
    public List<EpisodesShow> showUsers(@PathVariable Long id){

        List<Episodes> episodesList = this.repository.findByShowId(id);

        List<EpisodesShow> episodesShowList = new ArrayList<>();

        for(int i=0;i<episodesList.size();i++){
            EpisodesShow episodesShow = new EpisodesShow(
                    episodesList.get(i).getId(),
                    episodesList.get(i).getSeasonNumber(),
                    episodesList.get(i).getEpisodeNumber(),
                    "S" + episodesList.get(i).getSeasonNumber() + " E" + episodesList.get(i).getEpisodeNumber());
            episodesShowList.add(episodesShow);
        }
        return episodesShowList;
    }
}
