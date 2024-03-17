--liquibase formatted sql
--changeset id:UserLink author:Ovoshchko

CREATE TABLE linkviewer.userlink(
    user_id bigint,
    link_id bigint,

    FOREIGN KEY (user_id) REFERENCES linkviewer.users(tg_id),
    FOREIGN KEY (link_id) REFERENCES linkviewer.links(id)
);
