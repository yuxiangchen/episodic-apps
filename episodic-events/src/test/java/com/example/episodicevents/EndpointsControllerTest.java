package com.example.episodicevents;

import com.example.episodicevents.Endpoints.EndpointsRepository;
import com.example.episodicevents.Endpoints.Play;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by trainer8 on 5/22/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EndpointsControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private EndpointsRepository endpointsRepository;


    @Test
    @Rollback
    public void testPostPlay() throws Exception {

        Long count = endpointsRepository.count();

        Map<String, Object> dataPayload = new HashMap<String, Object>(){
            {
                put("offset", 0);
            }
        };

        Map<String, Object> payload = new HashMap<String, Object>(){
            {
                put("type", "play");
                put("userId", 52);
                put("showId", 987);
                put("episodeId", 456);
                put("createdAt", "2017-11-08T15:59:13.0091745");
                put("data", dataPayload);
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = mapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = post("")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(endpointsRepository.count(), equalTo(count+1));
    }


    @Test
    @Rollback
    public void testPostPause() throws Exception {

        Long count = endpointsRepository.count();

        Map<String, Object> dataPayload = new HashMap<String, Object>(){
            {
                put("offset", 1023);
            }
        };

        Map<String, Object> payload = new HashMap<String, Object>(){
            {
                put("type", "pause");
                put("userId", 52);
                put("showId", 987);
                put("episodeId", 456);
                put("createdAt", "2017-11-08T15:59:13.0091745");
                put("data", dataPayload);
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = mapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = post("")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(endpointsRepository.count(), equalTo(count+1));
    }


    @Test
    @Rollback
    public void testPostFastForward() throws Exception {

        Long count = endpointsRepository.count();

        Map<String, Object> dataPayload = new HashMap<String, Object>(){
            {
                put("startOffset", 4);
                put("endOffset", 408);
                put("speed", 2.5);
            }
        };

        Map<String, Object> payload = new HashMap<String, Object>(){
            {
                put("type", "fastForward");
                put("userId", 52);
                put("showId", 987);
                put("episodeId", 456);
                put("createdAt", "2017-11-08T15:59:13.0091745");
                put("data", dataPayload);
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = mapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = post("")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(endpointsRepository.count(), equalTo(count+1));
    }


    @Test
    @Rollback
    public void testPostRewind() throws Exception {

        Long count = endpointsRepository.count();

        Map<String, Object> dataPayload = new HashMap<String, Object>(){
            {
                put("startOffset", 4);
                put("endOffset", 408);
                put("speed", 2.5);
            }
        };

        Map<String, Object> payload = new HashMap<String, Object>(){
            {
                put("type", "rewind");
                put("userId", 52);
                put("showId", 987);
                put("episodeId", 456);
                put("createdAt", "2017-11-08T15:59:13.0091745");
                put("data", dataPayload);
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = mapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = post("")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(endpointsRepository.count(), equalTo(count+1));
    }


    @Test
    @Rollback
    public void testPostProgress() throws Exception {
        Long count = endpointsRepository.count();

        Map<String, Object> dataPayload = new HashMap<String, Object>(){
            {
                put("startOffset", 4);
            }
        };

        Map<String, Object> payload = new HashMap<String, Object>(){
            {
                put("type", "progress");
                put("userId", 52);
                put("showId", 987);
                put("episodeId", 456);
                put("createdAt", "2017-11-08T15:59:13.0091745");
                put("data", dataPayload);
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = mapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = post("")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(endpointsRepository.count(), equalTo(count+1));
    }

//
//    @Test
//    @Rollback
//    public void testPostProgressWithAmqp() throws Exception {
//        @MockBean
//        private
//        Long count = endpointsRepository.count();
//
//        Map<String, Object> dataPayload = new HashMap<String, Object>(){
//            {
//                put("startOffset", 4);
//            }
//        };
//
//        Map<String, Object> payload = new HashMap<String, Object>(){
//            {
//                put("type", "progress");
//                put("userId", 52);
//                put("showId", 987);
//                put("episodeId", 456);
//                put("createdAt", "2017-11-08T15:59:13.0091745");
//                put("data", dataPayload);
//            }
//        };
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        String json = mapper.writeValueAsString(payload);
//
//        MockHttpServletRequestBuilder request = post("")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        this.mvc.perform(request)
//                .andExpect(status().isOk());
//
//        assertThat(endpointsRepository.count(), equalTo(count+1));
//    }



    @Test
    @Rollback
    public void testPostScrub() throws Exception {

        Long count = endpointsRepository.count();

        Map<String, Object> dataPayload = new HashMap<String, Object>(){
            {
                put("startOffset", 4);
                put("endOffset",408);
            }
        };

        Map<String, Object> payload = new HashMap<String, Object>(){
            {
                put("type", "scrub");
                put("userId", 52);
                put("showId", 987);
                put("episodeId", 456);
                put("createdAt", "2017-11-08T15:59:13.0091745");
                put("data", dataPayload);
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = mapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = post("")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        assertThat(endpointsRepository.count(), equalTo(count+1));
    }


    @Before
    public void setup() throws Exception {
        endpointsRepository.deleteAll();
        HashMap<String, Object> data = new HashMap<String, Object>(){
            {
                put("offset", 32);
            }
        };

        Play play = new Play(52L,987L,456L, LocalDateTime.now(), data);

        this.endpointsRepository.save(play);
    }

    @Test
    public void testGet() throws Exception {


        MockHttpServletRequestBuilder request = get("/recent")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[0].type",equalTo("play")))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].userId",equalTo(52)))
                .andExpect(jsonPath("$[0].showId",equalTo(987)))
                .andExpect(jsonPath("$[0].episodeId",equalTo(456)))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].data.offset",equalTo(32)));

    }
}