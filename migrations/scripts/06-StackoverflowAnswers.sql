--liquibase formatted sql
--changeset id:StackoverflowAnswers author:Ovoshchko

CREATE TABLE linkviewer.stackoverflowanswers(
    name text,
    answer_id bigint,
    question_id bigint
);
