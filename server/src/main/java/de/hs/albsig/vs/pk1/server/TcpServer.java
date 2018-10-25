package de.hs.albsig.vs.pk1.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hs.albsig.vs.pk1.common.model.Channel;
import de.hs.albsig.vs.pk1.common.model.Constants;
import de.hs.albsig.vs.pk1.common.model.Message;
import de.hs.albsig.vs.pk1.dispatcher.Dispatcher;

public class TcpServer implements Server {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Channel channel;
    private final Dispatcher dispatcher;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream socketoutstr;
    private OutputStreamWriter osr;
    private BufferedWriter bw;
    private InputStream socketinstr;
    private InputStreamReader isr;
    private BufferedReader br;

    /**
     * @param channel
     * @param dispatcher
     */
    public TcpServer(final Channel channel, final Dispatcher dispatcher) {
        this.channel = channel;
        this.dispatcher = dispatcher;
    }

    @Override
    public void registerService() throws ServerException {
        try {
            LOGGER.trace("registerService()");
            dispatcher.rergister(channel, UUID.randomUUID());
            serverSocket = new ServerSocket(channel.getPort());
            while (true) {
                try {
                    acceptConnection();
                    reciveRequest();
                } catch (final ServerException e) {
                    LOGGER.error("Server stopped", e);
                }

            }
        } catch (final IOException e) {
            throw new ServerException("Can't create socket", e);
        }
    }

    @Override
    public void acceptConnection() throws ServerException {
        try {
            clientSocket = serverSocket.accept();
            LOGGER.trace("accepted Connection");
        } catch (final IOException e) {
            throw new ServerException("Can't accept connection", e);
        }
    }

    @Override
    public void deregisterService() throws ServerException {
        closeAll();
    }

    @Override
    public void reciveRequest() throws ServerException {
        try {
            LOGGER.trace("reciveRequest()");
            socketoutstr = clientSocket.getOutputStream();
            osr = new OutputStreamWriter(socketoutstr);
            bw = new BufferedWriter(osr);

            socketinstr = clientSocket.getInputStream();
            isr = new InputStreamReader(socketinstr);
            br = new BufferedReader(isr);

            if (Constants.RUN_SERVICE_XYZ.equals(br.readLine())) {
                bw.write(runServiceXYZ());
                bw.newLine();
                bw.flush();
                closeAllButSocket();
            }

        } catch (final IOException e) {
            throw new ServerException("Can't recive request", e);
        }
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

    private void closeAllButSocket() throws ServerException {
        try {
            clientSocket.close();
            socketoutstr.close();
            osr.close();
            bw.close();

            socketinstr.close();
            isr.close();
            br.close();
        } catch (final IOException e) {
            throw new ServerException("Can't close", e);
        }
    }

    private void closeAll() throws ServerException {
        try {
            serverSocket.close();
            closeAllButSocket();
        } catch (final IOException e) {
            throw new ServerException("Can't close", e);
        }
    }
}
