package de.hs.albsig.vs.pk1.server;

public class ServerException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 3251936735941220937L;

    /**
     * 
     */
    public ServerException() {
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ServerException(final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public ServerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public ServerException(final String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ServerException(final Throwable cause) {
        super(cause);
    }

}
