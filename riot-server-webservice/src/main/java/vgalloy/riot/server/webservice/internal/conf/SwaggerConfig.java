package vgalloy.riot.server.webservice.internal.conf;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import vgalloy.riot.server.service.api.model.game.GameId;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 13/06/16.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Swagger Api configuration.
     *
     * @return The Swagger api configuration.
     */
    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(SummonerId.class, String.class)
                .directModelSubstitute(GameId.class, String.class)
//                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(
                        new ResponseMessageBuilder().code(200).message("OK").responseModel(new ModelRef("Success")).build(),
                        new ResponseMessageBuilder().code(202).message("Resource not loaded yet. Retry Later").responseModel(new ModelRef("Accepted")).build(),
                        new ResponseMessageBuilder().code(401).message("Unauthorized").responseModel(new ModelRef("Error")).build(),
                        new ResponseMessageBuilder().code(404).message("Resource not found").responseModel(new ModelRef("Error")).build(),
                        new ResponseMessageBuilder().code(500).message("Internal Server Error").responseModel(new ModelRef("Error")).build()));
    }
}
