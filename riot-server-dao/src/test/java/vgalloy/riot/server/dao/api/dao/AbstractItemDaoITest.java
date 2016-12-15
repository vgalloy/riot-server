package vgalloy.riot.server.dao.api.dao;

import java.util.Objects;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 01/07/16.
 */
public abstract class AbstractItemDaoITest {

    private final ItemDao dao;

    protected AbstractItemDaoITest(ItemDao dao) {
        this.dao = Objects.requireNonNull(dao);
    }

    @Test
    public void testInsertOk() {
        // GIVEN
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Trinity force");

        // WHEN
        dao.save(new CommonDpoWrapper<>(new DpoId(Region.JP, 19L), itemDto));
        Optional<Entity<ItemDto, DpoId>> result = dao.get(new DpoId(Region.JP, 19L));

        // THEN
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(result.get().getItem().isPresent());
        Assert.assertEquals(itemDto, result.get().getItem().get());
    }
}