package vgalloy.riot.server.service.internal.service;

import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.api.dao.ChampionDao;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.loader.api.service.LoaderClient;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.service.ChampionService;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/12/16.
 */
public final class ChampionServiceImpl extends AbstractService<ChampionDto> implements ChampionService {

    private final LoaderClient loaderClient;

    /**
     * Constructor.
     *
     * @param championDao  the champion dao
     * @param loaderClient the summoner loader client
     */
    public ChampionServiceImpl(ChampionDao championDao, LoaderClient loaderClient) {
        super(championDao);
        this.loaderClient = Objects.requireNonNull(loaderClient);
    }

    @Override
    public Optional<Model<ChampionDto>> get(DpoId dpoId) {
        loaderClient.loadChampionById(dpoId.getRegion(), dpoId.getId());
        return super.get(dpoId);
    }
}
