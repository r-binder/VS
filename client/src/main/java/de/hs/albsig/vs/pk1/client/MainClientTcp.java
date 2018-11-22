package de.hs.albsig.vs.pk1.client;

import de.hs.albsig.vs.pk1.common.model.Channel;

public class MainClientTcp {

    public static void main(final String[] args) {
        final Client c = new TcpClient(new Channel("localhost", 1232));
        c.serviceXYZ();
    }

}
