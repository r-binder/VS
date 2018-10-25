package de.hs.albsig.vs.pk1.dispatcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hs.albsig.vs.pk1.common.model.Channel;
import de.hs.albsig.vs.pk1.common.model.Constants;

public class TcpDispatcher implements Dispatcher {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final int PORT = 1232;
    private final Map<UUID, Channel> serverLocationMap;

    public TcpDispatcher() {
        serverLocationMap = new HashMap<>();
        startService();
    }

    private void startService() {
        new Thread(() -> {
            LOGGER.trace("start Dispatcher");
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                while (true) {

                    final Socket clientSocket = serverSocket.accept();
                    LOGGER.trace("Dispatcher accepted");
                    final OutputStream socketoutstr = clientSocket
                            .getOutputStream();
                    final OutputStreamWriter osr = new OutputStreamWriter(
                            socketoutstr);
                    final BufferedWriter bw = new BufferedWriter(osr);

                    final InputStream socketinstr = clientSocket
                            .getInputStream();
                    final InputStreamReader isr = new InputStreamReader(
                            socketinstr);
                    final BufferedReader br = new BufferedReader(isr);

                    if (Constants.GET_CHANNEL.equals(br.readLine())) {
                        bw.write(new ObjectMapper().writeValueAsString(
                                getChannel(loacateServer())));
                        bw.newLine();
                        bw.flush();
                    }

                    try {
                        clientSocket.close();
                        socketoutstr.close();
                        osr.close();
                        bw.close();

                        socketinstr.close();
                        isr.close();
                        br.close();
                    } catch (final IOException e) {
                        LOGGER.error("Can't close", e);
                    }
                }
            } catch (final UnknownHostException uhe) {
                System.out.println(uhe);
            } catch (final IOException ioe) {
                System.out.println(ioe);
            }
        }).start();

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
