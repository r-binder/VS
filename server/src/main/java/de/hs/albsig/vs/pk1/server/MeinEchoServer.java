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
import java.net.UnknownHostException;

public class MeinEchoServer {

    public static void main(final String[] argv) {

        try {
            final ServerSocket serverSocket = new ServerSocket(1234);
            final Socket clientSocket = serverSocket.accept();

            final OutputStream socketoutstr = clientSocket.getOutputStream();
            final OutputStreamWriter osr = new OutputStreamWriter(socketoutstr);
            final BufferedWriter bw = new BufferedWriter(osr);

            final InputStream socketinstr = clientSocket.getInputStream();
            final InputStreamReader isr = new InputStreamReader(socketinstr);
            final BufferedReader br = new BufferedReader(isr);

            String anfrage;
            String antwort;

            anfrage = br.readLine();
            antwort = "Antwort auf " + anfrage;
            bw.write(antwort);
            bw.newLine();
            bw.flush();

            bw.close();
            br.close();
            clientSocket.close();
            serverSocket.close();
        } catch (final UnknownHostException uhe) {
            System.out.println(uhe);
        } catch (final IOException ioe) {
            System.out.println(ioe);
        }

    } // ende: main

} // Ende: public class MeinEchoServer
