package de.hs.albsig.vs.pk1.server;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import de.hs.albsig.vs.pk1.common.model.Channel;
import de.hs.albsig.vs.pk1.common.model.Constants;
import de.hs.albsig.vs.pk1.dispatcher.Dispatcher;

public class MqttServer extends AbstractServer {

    private static final Logger LOGGER = LogManager.getLogger();

    private IMqttClient mqttClient;

    /**
     * @param channel
     * @param dispatcher
     */
    public MqttServer(final Channel channel, final Dispatcher dispatcher) {
        super(channel, dispatcher);
    }

    @Override
    public void registerService() throws ServerException {
        super.registerService();
        try {
            mqttClient = new MqttClient(
                    String.format("%s:%s", channel.getIp(), channel.getPort()),
                    UUID.randomUUID().toString());
            acceptConnection();
            reciveRequest();
        } catch (final MqttException e) {
            throw new ServerException(e);
        }
    }

    @Override
    public void deregisterService() throws ServerException {
        super.deregisterService();
        try {
            mqttClient.disconnect();
        } catch (final MqttException e) {
            throw new ServerException(e);
        }
    }

    @Override
    public void acceptConnection() throws ServerException {
        final MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        try {
            mqttClient.connect(options);
        } catch (final MqttException e) {
            throw new ServerException(e);
        }
    }

    @Override
    public void reciveRequest() throws ServerException {
        try {
            mqttClient.subscribe(Constants.MQTT_TOPIC_TO_SERVER,
                    (topic, message) -> {
                        LOGGER.trace("reciveRequest() - callback");
                        if (Constants.RUN_SERVICE_XYZ
                                .equals(new String(message.getPayload()))) {
                            LOGGER.trace("reciveRequest()");
                            mqttClient.publish(Constants.MQTT_TOPIC_FROM_SERVER,
                                    runServiceXYZ().getBytes(), 1, false);
                        }
                    });
        } catch (final MqttException e) {
            throw new ServerException(e);
        }
    }
}
