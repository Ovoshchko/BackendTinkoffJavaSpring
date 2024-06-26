/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.linkviewer.tables.records;


import edu.java.scrapper.domain.jooq.linkviewer.tables.Gitcommits;

import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
public class GitcommitsRecord extends UpdatableRecordImpl<GitcommitsRecord> implements Record4<String, LocalDateTime, String, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINKVIEWER.GITCOMMITS.NAME</code>.
     */
    public void setName(@Nullable String value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINKVIEWER.GITCOMMITS.NAME</code>.
     */
    @Size(max = 1000000000)
    @Nullable
    public String getName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>LINKVIEWER.GITCOMMITS.MADE_DATE</code>.
     */
    public void setMadeDate(@Nullable LocalDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINKVIEWER.GITCOMMITS.MADE_DATE</code>.
     */
    @Nullable
    public LocalDateTime getMadeDate() {
        return (LocalDateTime) get(1);
    }

    /**
     * Setter for <code>LINKVIEWER.GITCOMMITS.URL</code>.
     */
    public void setUrl(@NotNull String value) {
        set(2, value);
    }

    /**
     * Getter for <code>LINKVIEWER.GITCOMMITS.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getUrl() {
        return (String) get(2);
    }

    /**
     * Setter for <code>LINKVIEWER.GITCOMMITS.COMMENT_NUMBER</code>.
     */
    public void setCommentNumber(@Nullable Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>LINKVIEWER.GITCOMMITS.COMMENT_NUMBER</code>.
     */
    @Nullable
    public Long getCommentNumber() {
        return (Long) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row4<String, LocalDateTime, String, Long> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row4<String, LocalDateTime, String, Long> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<String> field1() {
        return Gitcommits.GITCOMMITS.NAME;
    }

    @Override
    @NotNull
    public Field<LocalDateTime> field2() {
        return Gitcommits.GITCOMMITS.MADE_DATE;
    }

    @Override
    @NotNull
    public Field<String> field3() {
        return Gitcommits.GITCOMMITS.URL;
    }

    @Override
    @NotNull
    public Field<Long> field4() {
        return Gitcommits.GITCOMMITS.COMMENT_NUMBER;
    }

    @Override
    @Nullable
    public String component1() {
        return getName();
    }

    @Override
    @Nullable
    public LocalDateTime component2() {
        return getMadeDate();
    }

    @Override
    @NotNull
    public String component3() {
        return getUrl();
    }

    @Override
    @Nullable
    public Long component4() {
        return getCommentNumber();
    }

    @Override
    @Nullable
    public String value1() {
        return getName();
    }

    @Override
    @Nullable
    public LocalDateTime value2() {
        return getMadeDate();
    }

    @Override
    @NotNull
    public String value3() {
        return getUrl();
    }

    @Override
    @Nullable
    public Long value4() {
        return getCommentNumber();
    }

    @Override
    @NotNull
    public GitcommitsRecord value1(@Nullable String value) {
        setName(value);
        return this;
    }

    @Override
    @NotNull
    public GitcommitsRecord value2(@Nullable LocalDateTime value) {
        setMadeDate(value);
        return this;
    }

    @Override
    @NotNull
    public GitcommitsRecord value3(@NotNull String value) {
        setUrl(value);
        return this;
    }

    @Override
    @NotNull
    public GitcommitsRecord value4(@Nullable Long value) {
        setCommentNumber(value);
        return this;
    }

    @Override
    @NotNull
    public GitcommitsRecord values(@Nullable String value1, @Nullable LocalDateTime value2, @NotNull String value3, @Nullable Long value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GitcommitsRecord
     */
    public GitcommitsRecord() {
        super(Gitcommits.GITCOMMITS);
    }

    /**
     * Create a detached, initialised GitcommitsRecord
     */
    @ConstructorProperties({ "name", "madeDate", "url", "commentNumber" })
    public GitcommitsRecord(@Nullable String name, @Nullable LocalDateTime madeDate, @NotNull String url, @Nullable Long commentNumber) {
        super(Gitcommits.GITCOMMITS);

        setName(name);
        setMadeDate(madeDate);
        setUrl(url);
        setCommentNumber(commentNumber);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised GitcommitsRecord
     */
    public GitcommitsRecord(edu.java.scrapper.domain.jooq.linkviewer.tables.pojos.Gitcommits value) {
        super(Gitcommits.GITCOMMITS);

        if (value != null) {
            setName(value.getName());
            setMadeDate(value.getMadeDate());
            setUrl(value.getUrl());
            setCommentNumber(value.getCommentNumber());
            resetChangedOnNotNull();
        }
    }
}
