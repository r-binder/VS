package de.hs.albsig.vs.pk1.server;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hs.albsig.vs.pk1.server.model.Message;

public class AppTest {

    @Test
    public void objectMapper() {
        try {
            final String json = new ObjectMapper().writeValueAsString(
                    new Message(new Date(), UUID.randomUUID()));
            System.out.println(json);

            final Message obFromjson = new ObjectMapper().readValue(json,
                    Message.class);
            System.out.println(obFromjson);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
