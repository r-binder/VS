package de.hs.albsig.vs.pk1.dispatcher;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hs.albsig.vs.pk1.common.model.Constants;

public class MqttDispatcher extends AbstractDispatcher {

    private static final Logger LOGGER = LogManager.getLogger();

    public MqttDispatcher() {
        super();
        startService();
    }

    private void startService() {
        LOGGER.trace("start Dispatcher");
        try {
            final IMqttClient mqttClient = new MqttClient(
                    String.format("%s:%s", Constants.MQTT_BROKER,
                            Constants.MQTT_BROKER_PORT),
                    UUID.randomUUID().toString());
            final MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            mqttClient.connect(options);

            mqttClient.subscribe(Constants.MQTT_TOPIC_TO_DISPATCHER,
                    (topic, message) -> {
                        LOGGER.trace("startService() - callback");
                        if (Constants.GET_CHANNEL
                                .equals(new String(message.getPayload()))) {
                            LOGGER.trace("Dispatcher publish channel");
                            mqttClient.publish(
                                    Constants.MQTT_TOPIC_FROM_DISPATCHER,
                                    new ObjectMapper()
                                            .writeValueAsString(
                                                    getChannel(loacateServer()))
                                            .getBytes(),
                                    1, false);
                        }
                    });

        } catch (final MqttException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }
}
