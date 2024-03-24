--liquibase formatted sql
--changeset id:GitCommits author:Ovoshchko

CREATE TABLE linkviewer.gitcommits(
    name text,
    made_date timestamp,
    url text,
    comment_number bigint
);
