package vgalloy.riot.server.webservice.internal.mapper.gameid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.stereotype.Component;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.service.api.model.game.GameId;
import vgalloy.riot.server.webservice.internal.mapper.Mapper;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
@Component
public class GameIdMapper implements Mapper<GameId, String> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public String map(GameId gameId) {
        Objects.requireNonNull(gameId);
        return gameId.getRegion() + "_" + gameId.getId() + "_" + gameId.getMatchDate().format(DATE_TIME_FORMATTER);
    }

    @Override
    public GameId unmap(String gameId) {
        Objects.requireNonNull(gameId);

        String[] split = gameId.split("_");
        if (split.length != 3) {
            throw new IllegalArgumentException("the String : " + gameId + " can not be convert into MatchDetailId");
        }
        Region region = Region.valueOf(split[0]);
        Long matchId = Long.valueOf(split[1]);
        LocalDate localDate = LocalDate.from(DATE_TIME_FORMATTER.parse(split[2]));
        return new GameId(region, matchId, localDate);
    }
}
