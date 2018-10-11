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

public class MeinEchoClient {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(final String[] argv) {

        try {
            final String host = "localhost";
            final Socket meinEchoSocket = new Socket(host, 1234);

            final OutputStream socketoutstr = meinEchoSocket.getOutputStream();
            final OutputStreamWriter osr = new OutputStreamWriter(socketoutstr);
            final BufferedWriter bw = new BufferedWriter(osr);

            final InputStream socketinstr = meinEchoSocket.getInputStream();
            final InputStreamReader isr = new InputStreamReader(socketinstr);
            final BufferedReader br = new BufferedReader(isr);

            final String anfrage = "Hallo";
            String antwort;

            bw.write(anfrage);
            bw.newLine();
            bw.flush();
            antwort = br.readLine();

            LOGGER.info("Host = " + host);
            LOGGER.info("Echo = " + antwort);

            bw.close();
            br.close();
            meinEchoSocket.close();
        } catch (final UnknownHostException uhe) {
            System.out.println(uhe);
        } catch (final IOException ioe) {
            System.out.println(ioe);
        }

    } // ende: main

} // Ende: public class MeinEchoClient
