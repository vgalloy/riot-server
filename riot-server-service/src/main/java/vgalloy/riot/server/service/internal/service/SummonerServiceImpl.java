package vgalloy.riot.server.service.internal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.service.api.model.LastGame;
import vgalloy.riot.server.service.api.service.SummonerService;
import vgalloy.riot.server.service.internal.service.mapper.LastGameMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
@Component
public class SummonerServiceImpl extends AbstractService<SummonerDto> implements SummonerService {

    @Autowired
    private SummonerDao summonerDao;
    @Autowired
    private MatchDetailDao matchDetailDao;

    @Override
    public List<LastGame> getLastGames(Region region, long summonerId) {
        return matchDetailDao.getLastMatchDetail(region, summonerId).stream()
                .map(e -> LastGameMapper.map(e, summonerId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SummonerDto> getSummonerByName(Region region, String summonerName) {
        return summonerDao.getSummonerByName(region, summonerName);
    }
}
