/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.linkviewer.tables;


import edu.java.scrapper.domain.jooq.linkviewer.Keys;
import edu.java.scrapper.domain.jooq.linkviewer.Linkviewer;
import edu.java.scrapper.domain.jooq.linkviewer.tables.records.UserlinkRecord;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


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
public class Userlink extends TableImpl<UserlinkRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>LINKVIEWER.USERLINK</code>
     */
    public static final Userlink USERLINK = new Userlink();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<UserlinkRecord> getRecordType() {
        return UserlinkRecord.class;
    }

    /**
     * The column <code>LINKVIEWER.USERLINK.USER_ID</code>.
     */
    public final TableField<UserlinkRecord, Long> USER_ID = createField(DSL.name("USER_ID"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>LINKVIEWER.USERLINK.LINK_ID</code>.
     */
    public final TableField<UserlinkRecord, Long> LINK_ID = createField(DSL.name("LINK_ID"), SQLDataType.BIGINT, this, "");

    private Userlink(Name alias, Table<UserlinkRecord> aliased) {
        this(alias, aliased, null);
    }

    private Userlink(Name alias, Table<UserlinkRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>LINKVIEWER.USERLINK</code> table reference
     */
    public Userlink(String alias) {
        this(DSL.name(alias), USERLINK);
    }

    /**
     * Create an aliased <code>LINKVIEWER.USERLINK</code> table reference
     */
    public Userlink(Name alias) {
        this(alias, USERLINK);
    }

    /**
     * Create a <code>LINKVIEWER.USERLINK</code> table reference
     */
    public Userlink() {
        this(DSL.name("USERLINK"), null);
    }

    public <O extends Record> Userlink(Table<O> child, ForeignKey<O, UserlinkRecord> key) {
        super(child, key, USERLINK);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : Linkviewer.LINKVIEWER;
    }

    @Override
    @NotNull
    public List<ForeignKey<UserlinkRecord, ?>> getReferences() {
        return Arrays.asList(Keys.CONSTRAINT_1, Keys.CONSTRAINT_1E);
    }

    private transient Users _users;
    private transient Links _links;

    /**
     * Get the implicit join path to the <code>LINKVIEWER.USERS</code> table.
     */
    public Users users() {
        if (_users == null)
            _users = new Users(this, Keys.CONSTRAINT_1);

        return _users;
    }

    /**
     * Get the implicit join path to the <code>LINKVIEWER.LINKS</code> table.
     */
    public Links links() {
        if (_links == null)
            _links = new Links(this, Keys.CONSTRAINT_1E);

        return _links;
    }

    @Override
    @NotNull
    public Userlink as(String alias) {
        return new Userlink(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public Userlink as(Name alias) {
        return new Userlink(alias, this);
    }

    @Override
    @NotNull
    public Userlink as(Table<?> alias) {
        return new Userlink(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Userlink rename(String name) {
        return new Userlink(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Userlink rename(Name name) {
        return new Userlink(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Userlink rename(Table<?> name) {
        return new Userlink(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super Long, ? super Long, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super Long, ? super Long, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}