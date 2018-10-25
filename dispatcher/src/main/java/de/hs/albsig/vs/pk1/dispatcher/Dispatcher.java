package de.hs.albsig.vs.pk1.dispatcher;

import java.util.UUID;

import de.hs.albsig.vs.pk1.common.model.Channel;

public interface Dispatcher {
    void rergister(Channel channel, UUID server);

    void deregister(UUID server);

    Channel getChannel(UUID server);

    UUID loacateServer();

    void establishConnection();
}
