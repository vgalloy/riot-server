package vgalloy.riot.server.webservice.api.conf;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import vgalloy.riot.server.service.api.service.MatchDetailService;
import vgalloy.riot.server.service.api.service.QueryService;
import vgalloy.riot.server.service.api.service.RankedStatsService;
import vgalloy.riot.server.service.api.service.SummonerService;
import vgalloy.riot.server.webservice.internal.conf.SwaggerConfig;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public class WebserviceConfigTest {

    @Import({SwaggerConfig.class})
    @ComponentScan(value = {
            "vgalloy.riot.server.webservice.internal.filter",
            "vgalloy.riot.server.webservice.internal.controller",
            "vgalloy.riot.server.webservice.api.controller"})
    @SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class, MongoAutoConfiguration.class})
    public static class Config {

        @Bean
        public QueryService queryService() {
            return null;
        }

        @Bean
        public MatchDetailService matchDetailService() {
            return null;
        }

        @Bean
        public RankedStatsService rankedStatsService() {
            return null;
        }

        @Bean
        public SummonerService summonerService() {
            return null;
        }

        public static void main(String... args) {
            SpringApplication.run(WebserviceConfig.class, args);
        }
    }

    @Test
    public void testStartApplication() {
        SpringApplication.run(Config.class);
    }
}