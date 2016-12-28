package vgalloy.riot.server.webservice.internal.conf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import vgalloy.riot.server.service.api.model.game.GameId;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;
import vgalloy.riot.server.webservice.internal.mapper.gameid.GameIdConverter;
import vgalloy.riot.server.webservice.internal.mapper.summonerid.SummonerIdConverter;

/**
 * @author Vincent Galloy - 14/12/16
 *         Created by Vincent Galloy on 14/12/16.
 */
@Configuration
@ComponentScan("vgalloy.riot.server.webservice.internal.mapper")
public class JacksonConfiguration {

    @Autowired
    private SummonerIdConverter summonerIdConverter;
    @Autowired
    private GameIdConverter gameIdConverter;

    /**
     * Create Jackson mapper bean.
     *
     * @return the bean
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SimpleModule module = new SimpleModule();
        module.addSerializer(SummonerId.class, summonerIdConverter);
        module.addSerializer(GameId.class, gameIdConverter);
        mapper.registerModule(module);

        return mapper;
    }
}
