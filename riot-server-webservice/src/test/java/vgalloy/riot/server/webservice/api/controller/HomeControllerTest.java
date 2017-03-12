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

import vgalloy.riot.server.webservice.api.conf.WebserviceConfig;

/**
 * Created by Vincent Galloy on 20/02/17.
 *
 * @author Vincent Galloy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebserviceConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public final class HomeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHomePage() {
        // GIVEN
        ResponseEntity<String> c = this.restTemplate.getForEntity("/home", String.class);

        // WHEN
        Assert.assertEquals(HttpStatus.OK.value(), c.getStatusCode().value());
    }
}