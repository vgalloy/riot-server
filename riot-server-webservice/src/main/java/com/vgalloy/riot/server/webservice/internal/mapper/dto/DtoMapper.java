package com.vgalloy.riot.server.webservice.internal.mapper.dto;

import com.vgalloy.riot.server.webservice.api.dto.Dto;
import com.vgalloy.riot.server.webservice.internal.mapper.Mapper;

/**
 * Created by Vincent Galloy on 01/05/17.
 *
 * @author Vincent Galloy
 */
public interface DtoMapper<BUSINESS, DTO extends Dto> extends Mapper<BUSINESS, DTO> {

}
