package com.example.episodicshows.viewings;

import com.example.episodicshows.shows.Episodes;
import com.example.episodicshows.shows.EpisodesRepository;
import com.example.episodicshows.shows.ShowsRepository;
import com.example.episodicshows.users.Users;
import com.example.episodicshows.users.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import javax.transaction.Transactional;
import java.time.ZoneId;

/**
 * Created by trainer8 on 5/23/17.
 */
@Configuration
public class EpisodicShowListener implements RabbitListenerConfigurer {

    @Autowired
    ObjectMapper objectMapper;

    ViewingsRepository viewingsRepository;
    EpisodesRepository episodesRepository;
    ShowsRepository showsRepository;
    UsersRepository usersRepository;

    public EpisodicShowListener(ViewingsRepository viewingsRepository, EpisodesRepository episodesRepository, ShowsRepository showsRepository, UsersRepository usersRepository) {
        this.viewingsRepository = viewingsRepository;
        this.episodesRepository = episodesRepository;
        this.showsRepository = showsRepository;
        this.usersRepository = usersRepository;
    }

    @RabbitListener(queues = "#{'${queue}'}") //(queues = "episodic-progress")
    @Transactional
    public void receiveMessage(final EpisodicProgress episodicProgress) {

        ZoneId defaultZoneId = ZoneId.systemDefault();


        Episodes episodes = this.episodesRepository.findOne(episodicProgress.getEpisodeId());
        Users users = this.usersRepository.findOne(episodicProgress.getUserId());

        Viewings viewingsExist = this.viewingsRepository.
                findAllByUserIdAndShowId(users.getId(), episodes.getShowId());


        if(viewingsExist!=null){
            viewingsExist.setUpdatedAt(episodicProgress.getCreatedAt().toInstant().atZone(defaultZoneId).toLocalDateTime());
            viewingsExist.setTimecode(episodicProgress.getOffset());
            this.viewingsRepository.save(viewingsExist);
        } else {
            Viewings viewings = new Viewings();
            viewings.setShowId(episodes.getShowId());
            viewings.setUserId(episodicProgress.getUserId());
            viewings.setEpisodeId(episodicProgress.getEpisodeId());
            viewings.setUpdatedAt(episodicProgress.getCreatedAt().toInstant().atZone(defaultZoneId).toLocalDateTime());
            viewings.setTimecode(episodicProgress.getOffset());
            this.viewingsRepository.save(viewings);
        }

    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

}
