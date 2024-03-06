CREATE TABLE linkviewer.users(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    tg_id BIGINT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    PRIMARY KEY (id)
);
