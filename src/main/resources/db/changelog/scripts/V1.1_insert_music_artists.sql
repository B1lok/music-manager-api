CREATE TABLE if not exists artist
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                VARCHAR(255),
    nationality         VARCHAR(255),
    number_of_followers INTEGER,
    CONSTRAINT pk_artist PRIMARY KEY (id)
);

INSERT INTO artist (name, nationality, number_of_followers)
VALUES
    ('Beyonce', 'American', 123456789),
    ('Ed Sheeran', 'British', 98765432),
    ('Taylor Swift', 'American', 87654321),
    ('BTS', 'South Korean', 76543210),
    ('Drake', 'Canadian', 65432109),
    ('Rihanna', 'Barbadian', 54321098),
    ('Justin Bieber', 'Canadian', 43210987),
    ('Ariana Grande', 'American', 32109876),
    ('The Weeknd', 'Canadian', 21098765),
    ('Adele', 'British', 10987654);