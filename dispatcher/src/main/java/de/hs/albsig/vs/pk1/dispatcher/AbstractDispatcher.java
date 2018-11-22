package de.hs.albsig.vs.pk1.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hs.albsig.vs.pk1.common.model.Channel;

public class AbstractDispatcher implements Dispatcher {

    private static final Logger LOGGER = LogManager.getLogger();

    protected final Map<UUID, Channel> serverLocationMap;

    public AbstractDispatcher() {
        serverLocationMap = new HashMap<>();
    }

    @Override
    public void rergister(final Channel channel, final UUID server) {
        serverLocationMap.put(server, channel);
    }

    @Override
    public void deregister(final UUID server) {
        LOGGER.trace("Dispatcher deregister {}", server);
        serverLocationMap.remove(server);
    }

    @Override
    public Channel getChannel(final UUID server) {
        LOGGER.trace("Dispatcher getChannel");
        return serverLocationMap.get(server);
    }

    @Override
    public UUID loacateServer() {
        LOGGER.trace("Dispatcher loacateServer");
        if (serverLocationMap.size() == 0) {
            return null;
        }

        final int randomNumber = new Random().nextInt(serverLocationMap.size());
        return new ArrayList<>(serverLocationMap.keySet()).get(randomNumber);
    }

    @Override
    public void establishConnection() {
        throw new UnsupportedOperationException();
    }

}
