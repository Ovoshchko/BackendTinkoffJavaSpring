/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.linkviewer.tables.pojos;


import java.beans.ConstructorProperties;
import java.io.Serializable;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.Nullable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Userlink implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private Long linkId;

    public Userlink() {}

    public Userlink(Userlink value) {
        this.userId = value.userId;
        this.linkId = value.linkId;
    }

    @ConstructorProperties({ "userId", "linkId" })
    public Userlink(
        @Nullable Long userId,
        @Nullable Long linkId
    ) {
        this.userId = userId;
        this.linkId = linkId;
    }

    /**
     * Getter for <code>LINKVIEWER.USERLINK.USER_ID</code>.
     */
    @Nullable
    public Long getUserId() {
        return this.userId;
    }

    /**
     * Setter for <code>LINKVIEWER.USERLINK.USER_ID</code>.
     */
    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    /**
     * Getter for <code>LINKVIEWER.USERLINK.LINK_ID</code>.
     */
    @Nullable
    public Long getLinkId() {
        return this.linkId;
    }

    /**
     * Setter for <code>LINKVIEWER.USERLINK.LINK_ID</code>.
     */
    public void setLinkId(@Nullable Long linkId) {
        this.linkId = linkId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Userlink other = (Userlink) obj;
        if (this.userId == null) {
            if (other.userId != null)
                return false;
        }
        else if (!this.userId.equals(other.userId))
            return false;
        if (this.linkId == null) {
            if (other.linkId != null)
                return false;
        }
        else if (!this.linkId.equals(other.linkId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.userId == null) ? 0 : this.userId.hashCode());
        result = prime * result + ((this.linkId == null) ? 0 : this.linkId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Userlink (");

        sb.append(userId);
        sb.append(", ").append(linkId);

        sb.append(")");
        return sb.toString();
    }
}