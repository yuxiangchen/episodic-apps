package com.example.episodicshows.episodes;

import com.example.episodicshows.shows.Shows;
import com.example.episodicshows.shows.ShowsRepository;
import com.example.episodicshows.shows.Episodes;
import com.example.episodicshows.shows.EpisodesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by trainer8 on 5/18/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EpisodesControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private EpisodesRepository repositoryEpisodes;

    @Autowired
    private ShowsRepository repositoryShow;

    @Test
    @Transactional
    @Rollback
    public void testPost() throws Exception {

        Shows shows = new Shows(1L,"This is a test");
        this.repositoryShow.save(shows);
        Shows shows1 = this.repositoryShow.findByName("This is a test");
        Long showId = shows1.getId();

        Long count = repositoryEpisodes.count();

        Map<String, Object> payload = new HashMap<String, Object>(){
            {
                put("seasonNumber", 1);
                put("episodeNumber", 2);
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = post("/shows/{id}/episodes",showId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.seasonNumber", equalTo(1)))
                .andExpect(jsonPath("$.episodeNumber", equalTo(2)))
                .andExpect(jsonPath("$.title", equalTo("S1 E2")));

        Map<String, Object> payload1 = new HashMap<String, Object>(){
            {
                put("seasonNumber", 1);
                put("episodeNumber", 3);
            }
        };

        ObjectMapper mapper1 = new ObjectMapper();
        String json1 = mapper1.writeValueAsString(payload1);

        MockHttpServletRequestBuilder request1 = post("/shows/{id}/episodes",showId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json1);

        this.mvc.perform(request1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.seasonNumber", equalTo(1)))
                .andExpect(jsonPath("$.episodeNumber", equalTo(3)))
                .andExpect(jsonPath("$.title", equalTo("S1 E3")));

        assertThat(repositoryEpisodes.count(), equalTo(count+2));
    }


    @Test
    @Transactional
    @Rollback
    public void testGet() throws Exception {

        Shows shows = new Shows(1L,"This is a test");
        this.repositoryShow.save(shows);
        Shows shows1 = this.repositoryShow.findByName("This is a test");
        Long showId = shows1.getId();

        Episodes episodes1 = new Episodes(showId,2,3);

        this.repositoryEpisodes.save(episodes1);

        MockHttpServletRequestBuilder requestBuilder = get("/shows/{id}/episodes",showId)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id", instanceOf(Number.class)))
                .andExpect(jsonPath("$[0].seasonNumber", equalTo(2)))
                .andExpect(jsonPath("$[0].episodeNumber", equalTo(3)))
                .andExpect(jsonPath("$[0].title", equalTo("S2 E3")));
    }
}