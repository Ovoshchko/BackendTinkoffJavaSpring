--liquibase formatted sql
--changeset id:StackoverflowAnswers author:Ovoshchko runOnChange:true

CREATE TABLE IF NOT EXISTS linkviewer.stackoverflowanswers(
    name text,
    answer_id bigint,
    question_id bigint,

    PRIMARY KEY(answer_id)
);
