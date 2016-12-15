package vgalloy.riot.server.dao.api.dao;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.game.GameDto;
import vgalloy.riot.api.api.dto.game.RecentGamesDto;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 01/07/16.
 */
public abstract class AbstractRecentGameDaoITest {

    private final RecentGamesDao dao;

    protected AbstractRecentGameDaoITest(RecentGamesDao dao) {
        this.dao = Objects.requireNonNull(dao);
    }

    @Test
    public void testInsertOk() {
        // GIVEN
        RecentGamesDto recentGamesDto = new RecentGamesDto();
        recentGamesDto.setSummonerId(19L);
        Set<GameDto> gameDtoSet = new HashSet<>();
        gameDtoSet.add(new GameDto());
        recentGamesDto.setGames(gameDtoSet);

        // WHEN
        dao.save(new CommonDpoWrapper<>(new DpoId(Region.JP, 19L), recentGamesDto));
        Optional<Entity<RecentGamesDto, DpoId>> result = dao.get(new DpoId(Region.JP, 19L));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(recentGamesDto, result.get().getItem().get());
    }
}