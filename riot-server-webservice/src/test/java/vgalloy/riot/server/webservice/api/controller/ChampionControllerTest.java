package vgalloy.riot.server.webservice.api.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import vgalloy.riot.server.webservice.api.WebserviceConfig;
import vgalloy.riot.server.webservice.api.dto.AutoCompleteChampionNameDto;

/**
 * Created by Vincent Galloy on 30/04/17.
 *
 * @author Vincent Galloy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebserviceConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public final class ChampionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void championAutoCompleteMissingRegion() {
        // GIVEN
        AutoCompleteChampionNameDto autoCompleteChampionNameDto = new AutoCompleteChampionNameDto();
        autoCompleteChampionNameDto.setName("Lee");

        // WHEN
        ResponseEntity<?> c = this.restTemplate.postForEntity("/champions/autoCompleteChampionName", autoCompleteChampionNameDto, Object.class);

        // THEN
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), c.getStatusCode().value());
    }
}