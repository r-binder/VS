package de.hs.albsig.vs.pk1.server;

import de.hs.albsig.vs.pk1.common.model.Channel;
import de.hs.albsig.vs.pk1.common.model.Constants;
import de.hs.albsig.vs.pk1.dispatcher.Dispatcher;
import de.hs.albsig.vs.pk1.dispatcher.MqttDispatcher;

public class MainServerDispatcherMqtt {

    public static void main(final String[] args) {
        final Dispatcher d = new MqttDispatcher();
        final Server s = new MqttServer(
                new Channel(Constants.MQTT_BROKER, Constants.MQTT_BROKER_PORT),
                d);
        try {
            s.registerService();
        } catch (final ServerException e) {
            e.printStackTrace();
        }
    }
}
