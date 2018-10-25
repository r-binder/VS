package de.hs.albsig.vs.pk1.common.model;

public class Channel {
    private String ip;
    private int port;

    public Channel() {
    }

    /**
     * @param ip
     * @param port
     */
    public Channel(final String ip, final int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     *            the ip to set
     */
    public void setIp(final String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port
     *            the port to set
     */
    public void setPort(final int port) {
        this.port = port;
    }

}
