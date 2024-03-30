--liquibase formatted sql
--changeset id:GitCommits author:Ovoshchko runOnChange:true

CREATE TABLE IF NOT EXISTS linkviewer.gitcommits(
    name text,
    made_date timestamp,
    url text,
    comment_number bigint,

    PRIMARY KEY(url)
);
