package vgalloy.riot.server.webservice.internal.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vincent Galloy on 01/05/17.
 * Mapper should be tread-safe
 *
 * @author Vincent Galloy
 */
public interface Mapper<BUSINESS, DTO> {

    /**
     * Map the business object into the dto object.
     *
     * @param business the business object
     * @return the dto
     */
    DTO map(BUSINESS business);

    /**
     * Map the dto object into the business object.
     *
     * @param dto the dto object
     * @return the business
     */
    BUSINESS unmap(DTO dto);

    /**
     * Map a list of business object into a list of dto object.
     *
     * @param businessList the business list
     * @return the list of dto object
     */
    default List<DTO> mapList(List<BUSINESS> businessList) {
        return businessList.stream()
            .map(this::map)
            .collect(Collectors.toList());
    }

    /**
     * Map a list of dto object into a list of business object.
     *
     * @param businessList the dto list
     * @return the list of business object
     */
    default List<BUSINESS> unmapList(List<DTO> businessList) {
        return businessList.stream()
            .map(this::unmap)
            .collect(Collectors.toList());
    }
}
