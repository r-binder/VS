package de.hs.albsig.vs.pk1.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hs.albsig.vs.pk1.common.model.Channel;
import de.hs.albsig.vs.pk1.common.model.Constants;
import de.hs.albsig.vs.pk1.common.model.Message;

public class TcpClient implements Client {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Channel dispatcherChannel;

    /**
     * @param dispatcherChannel
     */
    public TcpClient(final Channel dispatcherChannel) {
        this.dispatcherChannel = dispatcherChannel;
    }

    @Override
    public Channel getChannel() {
        Channel c = null;
        try (Socket meinEchoSocket = new Socket(dispatcherChannel.getIp(),
                dispatcherChannel.getPort())) {

            final OutputStream socketoutstr = meinEchoSocket.getOutputStream();
            final OutputStreamWriter osr = new OutputStreamWriter(socketoutstr);
            final BufferedWriter bw = new BufferedWriter(osr);

            final InputStream socketinstr = meinEchoSocket.getInputStream();
            final InputStreamReader isr = new InputStreamReader(socketinstr);
            final BufferedReader br = new BufferedReader(isr);

            bw.write(Constants.GET_CHANNEL);
            bw.newLine();
            bw.flush();
            c = new ObjectMapper().readValue(br.readLine(), Channel.class);

            try {
                socketoutstr.close();
                osr.close();
                bw.close();

                socketinstr.close();
                isr.close();
                br.close();
            } catch (final IOException e) {
                LOGGER.error("Can't close", e);
            }
        } catch (final UnknownHostException uhe) {
            System.out.println(uhe);
        } catch (final IOException ioe) {
            System.out.println(ioe);
        }

        return c;
    }

    @Override
    public void serviceXYZ() {
        try {
            LOGGER.info(
                    new ObjectMapper().readValue(sendRequest(), Message.class));
        } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    @Override
    public String sendRequest() {
        String str = null;
        final Channel serverChannel = getChannel();
        try (Socket meinEchoSocket = new Socket(serverChannel.getIp(),
                serverChannel.getPort())) {

            final OutputStream socketoutstr = meinEchoSocket.getOutputStream();
            final OutputStreamWriter osr = new OutputStreamWriter(socketoutstr);
            final BufferedWriter bw = new BufferedWriter(osr);

            final InputStream socketinstr = meinEchoSocket.getInputStream();
            final InputStreamReader isr = new InputStreamReader(socketinstr);
            final BufferedReader br = new BufferedReader(isr);

            bw.write(Constants.RUN_SERVICE_XYZ);
            bw.newLine();
            bw.flush();
            str = br.readLine();

            try {
                socketoutstr.close();
                osr.close();
                bw.close();

                socketinstr.close();
                isr.close();
                br.close();
            } catch (final IOException e) {
                LOGGER.error("Can't close", e);
            }
        } catch (final UnknownHostException uhe) {
            LOGGER.error(uhe.getMessage(), uhe);
        } catch (final IOException ioe) {
            LOGGER.error(ioe.getMessage(), ioe);
        }

        return str;
    }

}
