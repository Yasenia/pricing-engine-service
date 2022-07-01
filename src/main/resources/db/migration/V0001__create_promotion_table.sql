CREATE SEQUENCE promotion_id_seq;

CREATE TABLE promotion
(
    id              text PRIMARY KEY DEFAULT lpad(nextval('promotion_id_seq')::text, 8, '0'),
    code            text        NOT NULL UNIQUE,
    title           text        NOT NULL,
    description     text,
    constraint_json jsonb       NOT NULL,
    rule_json       jsonb       NOT NULL,
    creator_id      text        NOT NULL,
    create_time     timestamptz NOT NULL,
    last_editor_id  text        NOT NULL,
    last_edit_time  timestamptz NOT NULL
);

ALTER SEQUENCE promotion_id_seq OWNED BY promotion.id;
