package de.hs.albsig.vs.pk1.client;

import de.hs.albsig.vs.pk1.common.model.Channel;
import de.hs.albsig.vs.pk1.common.model.Constants;

public class MainClientMqtt {

    public static void main(final String[] args) {
        final Client c = new MyMqttClient(
                new Channel(Constants.MQTT_BROKER, Constants.MQTT_BROKER_PORT));
        c.serviceXYZ();
        System.exit(0);
    }

}
