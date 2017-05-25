package com.example.episodicevents.Endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by trainer8 on 5/22/17.
 */
@RestController
@RequestMapping("/")
public class EndpointsController {
    EndpointsRepository endpointsRepository;
    private final RabbitTemplate rabbitTemplate;

    public EndpointsController(EndpointsRepository endpointsRepository, RabbitTemplate rabbitTemplate) {
        this.endpointsRepository = endpointsRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public void createEndpoints(@RequestBody Endpoints endpoints) throws JsonProcessingException {

        if(endpoints.getType().equalsIgnoreCase("progress")){

            EpisodicProgress episodicProgress = new EpisodicProgress();
            Progress progress = (Progress)endpoints;


            episodicProgress.setUserId(progress.getUserId());
            episodicProgress.setEpisodeId(progress.getEpisodeId());


            LocalDateTime localDateTime = progress.getCreatedAt();
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//            String json = mapper.writeValueAsString(localDateTime);
//            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//            LocalDateTime localDateTime1 = LocalDateTime.parse(json,formatter);
//            episodicProgress.setCreatedAt(localDateTime1);
            episodicProgress.setCreatedAt(progress.getCreatedAt());
            episodicProgress.setOffset((int)progress.getData().get("offset"));

            rabbitTemplate.convertAndSend("my-exchange", "my-routing-key", episodicProgress);
        }
        this.endpointsRepository.save(endpoints);
    }

    @GetMapping("/recent")
    public List<Endpoints> getEndpoints(){
        return this.endpointsRepository.findAll();
    }
}
