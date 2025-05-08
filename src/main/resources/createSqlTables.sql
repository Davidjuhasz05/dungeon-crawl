-- Drop tables if they already exist to avoid errors
DROP TABLE IF EXISTS map;
DROP TABLE IF EXISTS actor;
DROP TABLE IF EXISTS item;

-- Create item table
CREATE TABLE item
(
    id       SERIAL PRIMARY KEY,
    item VARCHAR(50)  NOT NULL
);

-- Create actor table
CREATE TABLE actor
(
    id        SERIAL PRIMARY KEY,
    health    INTEGER     NOT NULL,
    actorType VARCHAR(50) NOT NULL,
    weapon    INTEGER REFERENCES item (id),
    inventory INTEGER[]
);

-- Create map table
CREATE TABLE map
(
    id       SERIAL PRIMARY KEY,
    mapName  VARCHAR(50) NOT NULL,
    x        INTEGER     NOT NULL,
    y        INTEGER     NOT NULL,
    cellType VARCHAR(50) NOT NULL,
    actor    INTEGER REFERENCES actor (id),
    item     INTEGER REFERENCES item (id)
);
