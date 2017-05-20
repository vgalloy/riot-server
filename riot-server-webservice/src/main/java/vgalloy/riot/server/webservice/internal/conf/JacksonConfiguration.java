package vgalloy.riot.server.webservice.internal.conf;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vgalloy.riot.server.service.api.model.game.GameId;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;

/**
 * Created by Vincent Galloy on 14/12/16.
 *
 * @author Vincent Galloy
 */
@Configuration
public class JacksonConfiguration {

    private final StdSerializer<SummonerId> summonerIdConverter;
    private final StdSerializer<GameId> gameIdConverter;

    /**
     * Constructor.
     *
     * @param summonerIdConverter the summoner id converter
     * @param gameIdConverter     the game id converter
     */
    public JacksonConfiguration(StdSerializer<SummonerId> summonerIdConverter, StdSerializer<GameId> gameIdConverter) {
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
