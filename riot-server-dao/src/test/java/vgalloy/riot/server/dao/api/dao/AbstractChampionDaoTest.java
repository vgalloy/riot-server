package vgalloy.riot.server.dao.api.dao;

import java.util.Objects;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 01/07/16.
 */
public abstract class AbstractChampionDaoTest {

    private final ChampionDao dao;

    protected AbstractChampionDaoTest(ChampionDao championDao) {
        dao = Objects.requireNonNull(championDao);
    }

    @Test
    public void testInsertOk() {
        // GIVEN
        ChampionDto dto = new ChampionDto();
        dto.setName("Le Blanc");

        // WHEN
        dao.save(new CommonDpoWrapper<>(new DpoId(Region.JP, 19L), dto));
        Optional<Entity<ChampionDto, DpoId>> result = dao.get(new DpoId(Region.JP, 19L));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(dto, result.get().getItem().get());
    }
}