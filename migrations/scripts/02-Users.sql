--liquibase formatted sql
--changeset id:Users author:Ovoshchko

CREATE TABLE linkviewer.users(
    tg_id BIGINT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    PRIMARY KEY (tg_id)
);
