package com.sea.template.api;

import com.sea.template.SeaIntegrationTest;
import com.sea.template.Repository.JDBCOrderbookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SeaIntegrationTest
@SpringBootTest
@ActiveProfiles({"standalone"})
public class TemplateApiIT {

    @Autowired
    private JDBCOrderbookRepository repo;

    @Autowired
    private TemplateApi unit;

    private String guest = "Tom";


    @Test
    public void should_get_no_guest() {
        Response actual = unit.getGuest(guest);
        assertThat(actual.getStatus()).isEqualTo(400);
    }

    @Test
    public void should_get_guest() {
        unit.postContact(guest);
        Response actual = unit.getGuest(guest);
        assertThat(actual.getStatus()).isEqualTo(200);
    }

}
