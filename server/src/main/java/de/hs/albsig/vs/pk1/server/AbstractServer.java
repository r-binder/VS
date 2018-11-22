package de.hs.albsig.vs.pk1.server;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hs.albsig.vs.pk1.common.model.Channel;
import de.hs.albsig.vs.pk1.common.model.Message;
import de.hs.albsig.vs.pk1.dispatcher.Dispatcher;

public abstract class AbstractServer implements Server {

    private static final Logger LOGGER = LogManager.getLogger();
    protected final Channel channel;
    protected final Dispatcher dispatcher;

    protected final UUID channelId;

    /**
     * @param channel
     * @param dispatcher
     */
    public AbstractServer(final Channel channel, final Dispatcher dispatcher) {
        this.channel = channel;
        this.dispatcher = dispatcher;
        channelId = UUID.randomUUID();
    }

    @Override
    public void registerService() throws ServerException {
        LOGGER.trace("registerService()");
        dispatcher.rergister(channel, channelId);
    }

    @Override
    public void deregisterService() throws ServerException {
        LOGGER.trace("deregister");
        dispatcher.deregister(channelId);
    }

    @Override
    public String runServiceXYZ() throws ServerException {
        try {
            LOGGER.trace("runServiceXYZ()");
            return new ObjectMapper().writeValueAsString(
                    new Message(new Date(), UUID.randomUUID()));
        } catch (final IOException e) {
            throw new ServerException("Can't write", e);
        }
    }
}
