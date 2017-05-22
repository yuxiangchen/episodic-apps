package com.example.episodicshows.viewings;

import com.example.episodicshows.shows.Episodes;
import com.example.episodicshows.shows.EpisodesRepository;
import com.example.episodicshows.shows.Shows;
import com.example.episodicshows.shows.ShowsRepository;
import com.example.episodicshows.users.Users;
import com.example.episodicshows.users.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by trainer8 on 5/20/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ViewingsControllerTest{

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ViewingsRepository repositoryViewings;

    @Autowired
    private UsersRepository repositoryUsers;

    @Autowired
    private EpisodesRepository repositoryEpisodes;

    @Autowired
    private ShowsRepository repositoryShow;

    @Test
    @Transactional
    @Rollback
    public void testPatch() throws Exception {
        Users users = new Users();
        users.setEmail("yuxiang.chen@dish.com");
        this.repositoryUsers.save(users);
        Long userId = users.getId();

        Shows shows = new Shows();
        shows.setName("Friends");
        this.repositoryShow.save(shows);
        Long showsId = shows.getId();


        Episodes episodes = new Episodes();
        episodes.setShowId(showsId);
        episodes.setEpisodeNumber(1);
        episodes.setSeasonNumber(1);
        this.repositoryEpisodes.save(episodes);

        Long count = repositoryViewings.count();

        Map<String, Object> payload = new HashMap<String, Object>(){
            {
                put("episodeId", episodes.getId());
                put("updatedAt", LocalDateTime.parse("2017-05-04T11:45:34.9182"));
                put("timecode",79);
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = mapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = patch("/users/{id}/viewings",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(repositoryViewings.count(), equalTo(count+1));
    }


    @Test
    @Transactional
    @Rollback
    public void testGet() throws Exception {
        Users users = new Users();
        users.setEmail("yuxiang.chen@dish.com");
        this.repositoryUsers.save(users);
        Long userId = users.getId();

        Shows shows = new Shows();
        shows.setName("Friends");
        this.repositoryShow.save(shows);
        Long showsId = shows.getId();


        Episodes episodes = new Episodes();
        episodes.setShowId(showsId);
        episodes.setSeasonNumber(2);
        episodes.setEpisodeNumber(3);
        this.repositoryEpisodes.save(episodes);


        Viewings viewings = new Viewings();
        viewings.setUserId(userId);
        viewings.setEpisodeId(episodes.getId());
        viewings.setShowId(episodes.getShowId());
        viewings.setUpdatedAt(LocalDateTime.parse("2017-05-04T11:45:34.9182"));
        viewings.setTimecode(65);
        this.repositoryViewings.save(viewings);

        MockHttpServletRequestBuilder request = get("/users/{id}/recently-watched",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].show.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$[0].show.name", equalTo("Friends")))
                .andExpect(jsonPath("$[0].episode.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$[0].episode.seasonNumber", equalTo(2)))
                .andExpect(jsonPath("$[0].episode.episodeNumber", equalTo(3)))
                .andExpect(jsonPath("$[0].episode.title", equalTo("S2 E3")))
                .andExpect(jsonPath("$[0].updatedAt", equalTo("2017-05-04T11:45:34.9182")))
                .andExpect(jsonPath("$[0].timecode", equalTo(65)));

    }


    @Test
    @Transactional
    @Rollback
    public void testGetMultiValue() throws Exception {
        Users users = new Users();
        users.setEmail("yuxiang.chen@dish.com");
        this.repositoryUsers.save(users);
        Long userId = users.getId();

        Shows shows = new Shows();
        shows.setName("Friends");
        this.repositoryShow.save(shows);
        Long showsId = shows.getId();

        Episodes episodes = new Episodes();
        episodes.setShowId(showsId);
        episodes.setSeasonNumber(4);
        episodes.setEpisodeNumber(5);
        this.repositoryEpisodes.save(episodes);

        Viewings viewings = new Viewings();
        viewings.setUserId(userId);
        viewings.setEpisodeId(episodes.getId());
        viewings.setShowId(episodes.getShowId());
        viewings.setUpdatedAt(LocalDateTime.parse("2017-05-11T11:45:34.1000"));
        viewings.setTimecode(0);
        this.repositoryViewings.save(viewings);

        Viewings viewings2 = new Viewings();
        viewings2.setUserId(userId);
        viewings2.setEpisodeId(episodes.getId());
        viewings2.setShowId(episodes.getShowId());
        viewings2.setUpdatedAt(LocalDateTime.parse("2017-12-11T12:25:24.1111"));
        viewings2.setTimecode(1);
        this.repositoryViewings.save(viewings2);

        Viewings viewings3 = new Viewings();
        viewings3.setUserId(userId);
        viewings3.setEpisodeId(episodes.getId());
        viewings3.setShowId(episodes.getShowId());
        viewings3.setUpdatedAt(LocalDateTime.parse("2017-12-22T12:25:24.2222"));
        viewings3.setTimecode(2);
        this.repositoryViewings.save(viewings3);

        Viewings viewings4 = new Viewings();
        viewings4.setUserId(userId);
        viewings4.setEpisodeId(episodes.getId());
        viewings4.setShowId(episodes.getShowId());
        viewings4.setUpdatedAt(LocalDateTime.parse("2017-12-30T12:25:24.3333"));
        viewings4.setTimecode(3);
        this.repositoryViewings.save(viewings4);

        MockHttpServletRequestBuilder request = get("/users/{id}/recently-watched",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].show.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$[0].show.name", equalTo("Friends")))
                .andExpect(jsonPath("$[0].episode.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$[0].episode.seasonNumber", equalTo(4)))
                .andExpect(jsonPath("$[0].episode.episodeNumber", equalTo(5)))
                .andExpect(jsonPath("$[0].episode.title", equalTo("S4 E5")))
                .andExpect(jsonPath("$[0].updatedAt", equalTo("2017-12-30T12:25:24.3333")))
                .andExpect(jsonPath("$[0].timecode", equalTo(3)));

    }
}