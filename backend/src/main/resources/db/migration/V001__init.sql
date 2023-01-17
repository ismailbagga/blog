CREATE table basic_app_user
(
    id          BIGSERIAL PRIMARY KEY,
    username    varchar(255) NOT NULL UNIQUE,
    password    varchar(255),
    enabled     boolean     DEFAULT false,
    unlocked_at timestamp with time zone,
    role        varchar(50) DEFAULT 'SIMPLE_USER',
    version     int

);

CREATE TABLE markdown_content
(
    id      BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL
);
CREATE TABLE article
(
    id                  BIGSERIAL PRIMARY KEY,
    title               varchar(255) NOT NULL UNIQUE,
    slug                varchar(255) NOT NULL UNIQUE,
    description         varchar(255),
    reading_time        int          NOT NULL CHECK ( reading_time >= 1 ),
    markdown_content_id BIGINT REFERENCES markdown_content (id),
    url                 varchar(500),
    created_at          timestamp WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATE,
    next_article_id     BIGINT REFERENCES article (id) ON DELETE NO ACTION,
    prev_article_id     BIGINT REFERENCES article (id) ON DELETE NO ACTION

);
CREATE TABLE tag
(
    id    BIGSERIAL PRIMARY KEY,
    title varchar(255) UNIQUE,
    slug  varchar(255) UNIQUE
);
create table tag_of_article
(
    article_id bigint not null,
    tag_id     bigint not null,
    primary key (article_id, tag_id)
);


-- Indexes

CREATE INDEX basic_user_username_idx ON basic_app_user (username);
CREATE INDEX article_slug_idx ON article (slug);
CREATE INDEX tag_slug_idx ON tag (slug);
