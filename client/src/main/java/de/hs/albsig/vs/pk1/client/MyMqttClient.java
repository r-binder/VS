package de.hs.albsig.vs.pk1.client;

import java.io.IOException;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hs.albsig.vs.pk1.common.model.Channel;
import de.hs.albsig.vs.pk1.common.model.Constants;
import de.hs.albsig.vs.pk1.common.model.Message;

public class MyMqttClient implements Client {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Channel dispatcherChannel;
    private Channel serverChannel;
    private String requestRet;

    /**
     * @param dispatcherChannel
     */
    public MyMqttClient(final Channel dispatcherChannel) {
        this.dispatcherChannel = dispatcherChannel;
    }

    @Override
    public Channel getChannel() {
        LOGGER.trace("getChannel()");
        IMqttClient mqttClient = null;
        try {
            mqttClient = new MqttClient(
                    String.format("%s:%s", dispatcherChannel.getIp(),
                            dispatcherChannel.getPort()),
                    UUID.randomUUID().toString());
            final MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            mqttClient.connect(options);
            mqttClient.publish(Constants.MQTT_TOPIC_TO_DISPATCHER,
                    Constants.GET_CHANNEL.getBytes(), 1, false);
            mqttClient.subscribe(Constants.MQTT_TOPIC_FROM_DISPATCHER,
                    (topic, message) -> {
                        LOGGER.trace("getChannel() - callback");
                        try {
                            serverChannel = new ObjectMapper().readValue(
                                    new String(message.getPayload()),
                                    Channel.class);
                        } catch (final IOException e) {
                            LOGGER.error("Can't parse message", e);
                        }
                    });

        } catch (final MqttException e1) {
            LOGGER.error(e1.getMessage(), e1);
        }

        while (serverChannel == null) {
            try {
                Thread.sleep(10);
            } catch (final InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        try {
            mqttClient.disconnect();
        } catch (final MqttException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return serverChannel;
    }

    @Override
    public void serviceXYZ() {
        try {
            LOGGER.info(
                    new ObjectMapper().readValue(sendRequest(), Message.class));
        } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    @Override
    public String sendRequest() {
        LOGGER.trace("sendRequest()");
        final Channel channel = getChannel();
        IMqttClient mqttClient = null;
        try {
            mqttClient = new MqttClient(
                    String.format("%s:%s", channel.getIp(), channel.getPort()),
                    UUID.randomUUID().toString());
            final MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            mqttClient.connect(options);
            mqttClient.publish(Constants.MQTT_TOPIC_TO_SERVER,
                    Constants.RUN_SERVICE_XYZ.getBytes(), 1, false);
            mqttClient.subscribe(Constants.MQTT_TOPIC_FROM_SERVER,
                    (topic, message) -> {
                        LOGGER.trace("sendRequest() - callback");
                        requestRet = new String(message.getPayload());
                    });

        } catch (final MqttException e1) {
            LOGGER.error(e1.getMessage(), e1);
        }

        while (requestRet == null) {
            try {
                Thread.sleep(10);
            } catch (final InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        try {
            mqttClient.disconnect();
        } catch (final MqttException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return requestRet;
    }

}
