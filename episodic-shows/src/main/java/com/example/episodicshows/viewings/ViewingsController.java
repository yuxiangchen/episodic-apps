package com.example.episodicshows.viewings;

import com.example.episodicshows.shows.*;
import com.example.episodicshows.users.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trainer8 on 5/20/17.
 */
@RestController
public class ViewingsController {
    private final UsersRepository usersRepository;

    private final ViewingsRepository viewingsRepository;

    private final EpisodesRepository episodesRepository;

    private final ShowsRepository showsRepository;

    public ViewingsController(UsersRepository usersRepository, ViewingsRepository viewingsRepository, EpisodesRepository episodesRepository, ShowsRepository showsRepository) {
        this.usersRepository = usersRepository;
        this.viewingsRepository = viewingsRepository;
        this.episodesRepository = episodesRepository;
        this.showsRepository = showsRepository;
    }

    @PatchMapping("/users/{id}/viewings")
    public ResponseEntity updateViewing(@RequestBody Viewings viewings, @PathVariable Long id){

//        return null;
//        this.viewingsRepository.findByUserId(id);
        Episodes episodes = this.episodesRepository.findAllById(viewings.getEpisodeId());

        viewings.setUserId(id);
        viewings.setShowId(episodes.getShowId());

        Viewings viewingsExist = this.viewingsRepository.findAllByUserIdAndShowId(viewings.getUserId(),viewings.getShowId());

        if(viewingsExist!=null){
            this.viewingsRepository.delete(viewingsExist);
        }

        Viewings viewingsNew = this.viewingsRepository.save(viewings);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/users/{id}/recently-watched")
    public List<RecentlyWatch> GetWatch(@PathVariable Long id){

        List<Viewings> viewingsList =  this.viewingsRepository.findAllByUserId(id);

        List<RecentlyWatch> recentlyWatchList = new ArrayList<>();

        for (Viewings aViewingsList : viewingsList) {
            Shows shows = this.showsRepository.findById(aViewingsList.getShowId());
            Episodes episodes = this.episodesRepository.findAllById(aViewingsList.getEpisodeId());
            EpisodesShow episodesShow = new EpisodesShow();

            episodesShow.setId(episodes.getId());
            episodesShow.setEpisodeNumber(episodes.getEpisodeNumber());
            episodesShow.setSeasonNumber(episodes.getSeasonNumber());
            episodesShow.setTitle("S" + episodes.getSeasonNumber() + " E" + episodes.getEpisodeNumber());

            RecentlyWatch recentlyWatch = new RecentlyWatch();
            recentlyWatch.setShows(shows);
            recentlyWatch.setEpisodesShow(episodesShow);
            recentlyWatch.setUpdatedAt(aViewingsList.getUpdatedAt());
            recentlyWatch.setTimecode(aViewingsList.getTimecode());

            recentlyWatchList.add(recentlyWatch);
        }



//        for(int i=1;i<recentlyWatchList.size();i++){
//
//            if (recentlyWatchList.get(i).getShows() == recentlyWatchList.get(i - 1).getShows()) {
//                recentlyWatchList.remove(i-1);
//                i--;
//            }
//
//        }


        return recentlyWatchList;
    }
}
