/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.linkviewer.tables.records;


import edu.java.scrapper.domain.jooq.linkviewer.tables.Users;

import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


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
public class UsersRecord extends UpdatableRecordImpl<UsersRecord> implements Record2<Long, OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINKVIEWER.USERS.TG_ID</code>.
     */
    public void setTgId(@NotNull Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINKVIEWER.USERS.TG_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getTgId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>LINKVIEWER.USERS.CREATED_AT</code>.
     */
    public void setCreatedAt(@NotNull OffsetDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINKVIEWER.USERS.CREATED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getCreatedAt() {
        return (OffsetDateTime) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Long, OffsetDateTime> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row2<Long, OffsetDateTime> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Users.USERS.TG_ID;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field2() {
        return Users.USERS.CREATED_AT;
    }

    @Override
    @NotNull
    public Long component1() {
        return getTgId();
    }

    @Override
    @NotNull
    public OffsetDateTime component2() {
        return getCreatedAt();
    }

    @Override
    @NotNull
    public Long value1() {
        return getTgId();
    }

    @Override
    @NotNull
    public OffsetDateTime value2() {
        return getCreatedAt();
    }

    @Override
    @NotNull
    public UsersRecord value1(@NotNull Long value) {
        setTgId(value);
        return this;
    }

    @Override
    @NotNull
    public UsersRecord value2(@NotNull OffsetDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public UsersRecord values(@NotNull Long value1, @NotNull OffsetDateTime value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UsersRecord
     */
    public UsersRecord() {
        super(Users.USERS);
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    @ConstructorProperties({ "tgId", "createdAt" })
    public UsersRecord(@NotNull Long tgId, @NotNull OffsetDateTime createdAt) {
        super(Users.USERS);

        setTgId(tgId);
        setCreatedAt(createdAt);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    public UsersRecord(edu.java.scrapper.domain.jooq.linkviewer.tables.pojos.Users value) {
        super(Users.USERS);

        if (value != null) {
            setTgId(value.getTgId());
            setCreatedAt(value.getCreatedAt());
            resetChangedOnNotNull();
        }
    }
}
