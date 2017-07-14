package com.vgalloy.riot.server.webservice.api.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.vgalloy.riot.server.webservice.api.WebserviceConfig;
import com.vgalloy.riot.server.webservice.api.dto.impl.AutoCompleteChampionNameDto;
import com.vgalloy.riot.server.webservice.internal.dto.ErrorDto;

/**
 * Created by Vincent Galloy on 30/04/17.
 *
 * @author Vincent Galloy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebserviceConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public final class ChampionControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void championAutoCompleteMissingRegion() {
        // GIVEN
        AutoCompleteChampionNameDto autoCompleteChampionNameDto = new AutoCompleteChampionNameDto();
        autoCompleteChampionNameDto.setName("Lee");

        // WHEN
        ResponseEntity<ErrorDto> c = this.restTemplate.postForEntity("/champions/autoCompleteChampionName", autoCompleteChampionNameDto, ErrorDto.class);

        // THEN
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), c.getStatusCode().value());
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), c.getBody().getCode());
        Assert.assertEquals("Region can not be null", c.getBody().getMessage());
    }

    @Test
    public void championAutoCompleteWithInvalidJson() {
        // GIVEN
        String invalidJson = "{ytqzer";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(invalidJson, headers);

        // WHEN
        ResponseEntity<ErrorDto> c = this.restTemplate.postForEntity("/champions/autoCompleteChampionName", entity, ErrorDto.class);

        // THEN
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), c.getStatusCode().value());
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), c.getBody().getCode());
        Assert.assertEquals("Invalid json", c.getBody().getMessage());
    }
}