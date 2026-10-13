--liquibase formatted sql

--changeset doctor_name:1
CREATE SEQUENCE films_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE films (
                       id          BIGINT PRIMARY KEY NOT NULL,
                       film_id     BIGINT UNIQUE,
                       film_name   TEXT,
                       year        INTEGER,
                       rating      NUMERIC(4,2),
                       description TEXT
);

--rollback DROP TABLE films;
--rollback DROP SEQUENCE films_id_seq;