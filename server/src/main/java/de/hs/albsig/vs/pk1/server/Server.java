package de.hs.albsig.vs.pk1.server;

public interface Server {
    void registerService() throws ServerException;

    void acceptConnection() throws ServerException;

    void deregisterService() throws ServerException;

    void reciveRequest() throws ServerException;

    String runServiceXYZ() throws ServerException;
}
