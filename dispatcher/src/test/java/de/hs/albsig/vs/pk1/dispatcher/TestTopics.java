package de.hs.albsig.vs.pk1.dispatcher;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import de.hs.albsig.vs.pk1.common.model.Constants;

public class TestTopics {

    public static void main(final String args[]) {
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
                        System.out.println("Topic: " + topic + " Message: "
                                + new String(message.getPayload()));
                    });

            mqttClient.subscribe(Constants.MQTT_TOPIC_FROM_DISPATCHER,
                    (topic, message) -> {
                        System.out.println("Topic: " + topic + " Message: "
                                + new String(message.getPayload()));
                    });

            mqttClient.subscribe(Constants.MQTT_TOPIC_TO_SERVER,
                    (topic, message) -> {
                        System.out.println("Topic: " + topic + " Message: "
                                + new String(message.getPayload()));
                    });

            mqttClient.subscribe(Constants.MQTT_TOPIC_FROM_SERVER,
                    (topic, message) -> {
                        System.out.println("Topic: " + topic + " Message: "
                                + new String(message.getPayload()));
                    });

            // mqttClient.disconnect();
        } catch (final MqttException e) {
            e.printStackTrace();
        }
    }
}
