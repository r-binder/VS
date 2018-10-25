package de.hs.albsig.vs.pk1.client;

import de.hs.albsig.vs.pk1.common.model.Channel;

public interface Client {
    Channel getChannel();

    void serviceXYZ();

    String sendRequest();
}
