package vgalloy.riot.server.webservice.api.controller;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.server.service.api.context.ContextManager;
import vgalloy.riot.server.service.api.model.LoaderInformation;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/07/16.
 */
@RestController
@RequestMapping(value = "/loader", method = RequestMethod.GET)
public class InformationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InformationController.class);

    @Autowired
    private ContextManager contextManager;

    /**
     * Get the globalInformation about loader.
     *
     * @return a mapToEntity with the id of each loader
     */
    @RequestMapping()
    public Map<Integer, String> getGlobalInformation() {
        LOGGER.info("[ GET ] : getGlobalInformation");
        AtomicInteger index = new AtomicInteger();
        return contextManager.getLoaderList().stream()
                .collect(Collectors.toMap(e -> index.getAndIncrement(), e -> e.getClass().getSimpleName()));
    }

    /**
     * Get more information about one loader.
     *
     * @param loaderId the loader id given by {@link #getGlobalInformation()}
     * @return the detail of one loader
     */
    @RequestMapping(value = "/{loaderId}")
    public LoaderInformation getInformation(@PathVariable int loaderId) {
        LOGGER.info("[ GET ] : getInformation, loaderId : {}", loaderId);
        if (0 <= loaderId && loaderId < contextManager.getLoaderList().size()) {
            return contextManager.getLoaderList().get(loaderId).getLoaderInformation();
        }
        return null;
    }
}
