CREATE TABLE artist
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                VARCHAR(255),
    nationality         VARCHAR(255),
    number_of_followers INTEGER,
    CONSTRAINT pk_artist PRIMARY KEY (id)
);

ALTER TABLE artist
    ADD CONSTRAINT uc_artist_name UNIQUE (name);
CREATE TABLE genre
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_genre PRIMARY KEY (id)
);

ALTER TABLE genre
    ADD CONSTRAINT uc_genre_name UNIQUE (name);
CREATE TABLE song
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title        VARCHAR(255),
    release_date date,
    artist_id    BIGINT                                  NOT NULL,
    duration     INTEGER,
    CONSTRAINT pk_song PRIMARY KEY (id)
);

ALTER TABLE song
    ADD CONSTRAINT FK_SONG_ON_ARTIST FOREIGN KEY (artist_id) REFERENCES artist (id);
CREATE TABLE song_genres
(
    genres_id BIGINT NOT NULL,
    song_id   BIGINT NOT NULL,
    CONSTRAINT pk_song_genres PRIMARY KEY (genres_id, song_id)
);

ALTER TABLE song_genres
    ADD CONSTRAINT fk_songen_on_genre FOREIGN KEY (genres_id) REFERENCES genre (id);

ALTER TABLE song_genres
    ADD CONSTRAINT fk_songen_on_song FOREIGN KEY (song_id) REFERENCES song (id);