package vgalloy.riot.server.webservice.internal.conf;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import vgalloy.riot.server.service.api.model.game.GameId;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;
import vgalloy.riot.server.webservice.internal.mapper.gameid.GameIdConverter;
import vgalloy.riot.server.webservice.internal.mapper.summonerid.SummonerIdConverter;

/**
 * Created by Vincent Galloy on 14/12/16.
 *
 * @author Vincent Galloy
 */
@Configuration
@ComponentScan("vgalloy.riot.server.webservice.internal.mapper")
public class JacksonConfiguration {

    private final SummonerIdConverter summonerIdConverter;
    private final GameIdConverter gameIdConverter;

    /**
     * Constructor.
     *
     * @param summonerIdConverter the summoner id converter
     * @param gameIdConverter     the game id converter
     */
    public JacksonConfiguration(SummonerIdConverter summonerIdConverter, GameIdConverter gameIdConverter) {
        this.summonerIdConverter = Objects.requireNonNull(summonerIdConverter);
        this.gameIdConverter = Objects.requireNonNull(gameIdConverter);
    }

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
