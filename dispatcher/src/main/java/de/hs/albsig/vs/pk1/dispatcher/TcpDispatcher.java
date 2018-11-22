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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hs.albsig.vs.pk1.common.model.Constants;

public class TcpDispatcher extends AbstractDispatcher {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int PORT = 1232;

    public TcpDispatcher() {
        super();
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
            } catch (final UnknownHostException e) {
                LOGGER.error(e.getMessage(), e);
            } catch (final IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }).start();

    }
}
