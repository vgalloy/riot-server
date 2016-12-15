package vgalloy.riot.server.dao.api.dao;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.stats.ChampionStatsDto;
import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/06/16.
 */
public abstract class AbstractRankedStatsDaoITest {

    private final RankedStatsDao dao;

    protected AbstractRankedStatsDaoITest(RankedStatsDao dao) {
        this.dao = Objects.requireNonNull(dao);
    }

    @Test(expected = NullPointerException.class)
    public void testNullRegion() {
        dao.get(new DpoId(null, 1L));
    }

    @Test
    public void testEmptyDatabase() {
        Optional<Entity<RankedStatsDto, DpoId>> rankedStatsEntity = dao.get(new DpoId(Region.BR, 1L));
        Assert.assertFalse(rankedStatsEntity.isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void testRandomWithNullRegion() {
        dao.getRandom(null);
    }

    @Test
    public void testEmptyRandom() {
        Optional<Entity<RankedStatsDto, DpoId>> rankedStatsEntity = dao.getRandom(Region.KR);
        Assert.assertFalse(rankedStatsEntity.isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void testInsertWithNullRankedStatsDto() {
        dao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, 1L), null));
    }

    @Test(expected = NullPointerException.class)
    public void testInsertWithNullId() {
        dao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, null), new RankedStatsDto()));
    }

    @Test
    public void testInsertWithCorrectRankedStatsDto() {
        // GIVEN
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        rankedStatsDto.setSummonerId(10L);

        // WHEN
        dao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, 10L), rankedStatsDto));
        Optional<Entity<RankedStatsDto, DpoId>> result = dao.get(new DpoId(Region.EUW, 10L));

        // THEN
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(rankedStatsDto, result.get().getItem().get());
    }

    @Test
    public void testRandomWithCorrectRankedStatsDto() {
        // GIVEN
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        rankedStatsDto.setSummonerId(11L);

        // WHEN
        dao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, 11L), rankedStatsDto));
        Optional<Entity<RankedStatsDto, DpoId>> result = dao.getRandom(Region.EUW);

        // THEN
        Assert.assertNotNull(result);
    }

    @Test
    public void testCorrectRankedStatsDto() {
        // GIVEN
        RankedStatsDto rankedStatsDto = new RankedStatsDto();
        rankedStatsDto.setChampions(new ArrayList<>());
        rankedStatsDto.getChampions().add(new ChampionStatsDto());
        rankedStatsDto.getChampions().get(0).setId(10);
        rankedStatsDto.getChampions().add(new ChampionStatsDto());
        rankedStatsDto.setSummonerId(12L);

        // WHEN
        dao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, 12L), rankedStatsDto));
        Optional<Entity<RankedStatsDto, DpoId>> result = dao.get(new DpoId(Region.EUW, 12L));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(rankedStatsDto, result.get().getItem().get());
    }
}