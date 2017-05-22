package com.example.episodicshows.users;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by trainer8 on 5/17/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UsersRepository repository;


    @Before
    @Transactional
    @Rollback
    public void setup() throws Exception {

        repository.deleteAll();


        Users users1 = new Users();
        users1.setEmail("joe@example.com");


        repository.save(users1);
    }

    @Test
    @Transactional
    @Rollback
    public void testGet() throws Exception {

        MockHttpServletRequestBuilder request = get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email", equalTo("joe@example.com")));
    }

    @Test
    @Transactional
    @Rollback
    public void testPost() throws Exception {

        Long count = repository.count();

        Map<String, Object> payload = new HashMap<String, Object>(){
            {
                put("email", "joe1@example.com");
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.email", equalTo("joe1@example.com")));

        assertThat(repository.count(), equalTo(count+1));
    }

}