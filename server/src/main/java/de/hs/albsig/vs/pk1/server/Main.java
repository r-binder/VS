package de.hs.albsig.vs.pk1.server;

import de.hs.albsig.vs.pk1.common.model.Channel;
import de.hs.albsig.vs.pk1.dispatcher.Dispatcher;
import de.hs.albsig.vs.pk1.dispatcher.TcpDispatcher;

public class Main {

    public static void main(final String[] args) {
        final Dispatcher d = new TcpDispatcher();
        final Channel serverChannel = new Channel("localhost", 1235);
        final Server s = new TcpServer(serverChannel, d);
        try {
            s.registerService();
        } catch (final ServerException e1) {
            e1.printStackTrace();
        }
    }

}
