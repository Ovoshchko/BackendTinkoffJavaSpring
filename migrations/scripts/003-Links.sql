--liquibase formatted sql
--changeset id:Links author:Ovoshchko runOnChange:true

CREATE TABLE IF NOT EXISTS linkviewer.links(
    id bigint GENERATED ALWAYS AS IDENTITY,
    url text NOT NULL,
    last_check TIMESTAMP NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (url)
);
