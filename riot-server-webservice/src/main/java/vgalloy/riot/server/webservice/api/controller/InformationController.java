package vgalloy.riot.server.webservice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vgalloy.riot.server.service.api.model.LoaderInformation;
import vgalloy.riot.server.service.internal.executor.Runner;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/07/16.
 */
@RestController
@RequestMapping(value = "/loader", method = RequestMethod.GET)
public class InformationController {

    @Autowired
    private Runner runner;

    /**
     * Get the globalInformation about loader.
     *
     * @return a map with the id of each loader
     */
    @RequestMapping()
    public Map<Integer, String> getGlobalInformation() {
        AtomicInteger index = new AtomicInteger();
        return runner.getLoaderList().stream().collect(Collectors.toMap(e -> index.getAndIncrement(), e -> getClass().getCanonicalName()));
    }

    /**
     * Get more information about one loader.
     *
     * @param loaderId the loader id given by {@link #getGlobalInformation()}
     * @return the detail of one loader
     */
    @RequestMapping(value = "/{loaderId}")
    public LoaderInformation getInformation(@PathVariable int loaderId) {
        if (0 <= loaderId && loaderId < runner.getLoaderList().size()) {
            return runner.getLoaderList().get(loaderId).getLoaderInformation();
        }
        return null;
    }
}
