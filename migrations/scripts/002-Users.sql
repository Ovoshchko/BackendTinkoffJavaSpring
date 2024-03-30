--liquibase formatted sql
--changeset id:Users author:Ovoshchko runOnChange:true

CREATE TABLE IF NOT EXISTS linkviewer.users(
    tg_id BIGINT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    PRIMARY KEY (tg_id)
);
