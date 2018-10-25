package de.hs.albsig.vs.pk1.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Message implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 8491822397434617234L;
    private Date date;
    private UUID id;

    public Message() {
    }

    public Message(final Date date, final UUID id) {
        this.date = date;
        this.id = id;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(final Date date) {
        this.date = date;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final UUID id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Message [date=" + date + ", id=" + id + "]";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (date == null) {
            if (other.date != null) {
                return false;
            }
        } else if (!date.equals(other.date)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
