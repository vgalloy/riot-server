package vgalloy.riot.server.dao.api.dao;

import java.util.Objects;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 30/06/16.
 */
public abstract class AbstractSummonerDaoITest {

    private final SummonerDao dao;

    protected AbstractSummonerDaoITest(SummonerDao dao) {
        this.dao = Objects.requireNonNull(dao);
    }

    @Test
    public void testRandomFalse() {
        // WHEN
        Optional<Entity<SummonerDto, DpoId>> result = dao.getRandom(Region.BR);

        // THEN
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void testRandomTrue() {
        // GIVEN
        SummonerDto summoner = new SummonerDto();
        summoner.setId(2L);
        dao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, 2L), summoner));

        // WHEN
        Optional<Entity<SummonerDto, DpoId>> result = dao.getRandom(Region.EUW);

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(summoner, result.get().getItem().get());
    }

    @Test
    public void testSummonerByName() {
        // GIVEN
        SummonerDto summoner = new SummonerDto();
        summoner.setName("NAME");
        summoner.setId(2L);
        dao.save(new CommonDpoWrapper<>(new DpoId(Region.EUW, 2L), summoner));

        // WHEN
        Optional<SummonerDto> resultEmpty = dao.getSummonerByName(Region.EUW, "azeR");
        Optional<SummonerDto> resultEmpty2 = dao.getSummonerByName(Region.BR, summoner.getName());
        Optional<SummonerDto> result = dao.getSummonerByName(Region.EUW, summoner.getName());

        // THEN
        Assert.assertFalse(resultEmpty.isPresent());
        Assert.assertFalse(resultEmpty2.isPresent());
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(summoner, result.get());
    }
}