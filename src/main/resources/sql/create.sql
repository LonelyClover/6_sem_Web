DROP TABLE IF EXISTS theater CASCADE;
CREATE TABLE theater (
  id serial PRIMARY KEY,
  name text NOT NULL,
  address text NOT NULL,
  info text
);

DROP TABLE IF EXISTS actor CASCADE;
CREATE TABLE actor (
  id serial PRIMARY KEY,
  name text NOT NULL,
  info text,
  theater_id integer NOT NULL
      REFERENCES theater (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS director CASCADE;
CREATE TABLE director (
  id serial PRIMARY KEY,
  name text NOT NULL,
  info text,
  theater_id integer NOT NULL
      REFERENCES theater (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS play CASCADE;
CREATE TABLE play (
  id serial PRIMARY KEY,
  name text NOT NULL,
  duration interval NOT NULL,
  info text,
  theater_id integer NOT NULL
      REFERENCES theater ON DELETE CASCADE,
  director_id integer NOT NULL
      REFERENCES director (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS role CASCADE;
CREATE TABLE role (
  id serial PRIMARY KEY,
  actor_id integer NOT NULL REFERENCES actor (id) ON DELETE CASCADE,
  play_id integer NOT NULL REFERENCES play (id) ON DELETE CASCADE,
  info text
);

DROP TABLE IF EXISTS place CASCADE;
CREATE TABLE place (
  id serial PRIMARY KEY,
  number text NOT NULL,
  place_type integer NOT NULL,
  theater_id integer NOT NULL
      REFERENCES theater (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS performance CASCADE;
CREATE TABLE performance (
  id serial PRIMARY KEY,
  play_id integer NOT NULL REFERENCES play (id) ON DELETE CASCADE,
  datetime timestamp NOT NULL
);

DROP TABLE IF EXISTS ticket CASCADE;
CREATE TABLE ticket (
  id serial PRIMARY KEY,
  performance_id integer NOT NULL REFERENCES performance (id) ON DELETE CASCADE,
  place_id integer NOT NULL REFERENCES place (id) ON DELETE CASCADE,
  customer_name text NOT NULL,
  customer_phone_number text NOT NULL
);

DROP TABLE IF EXISTS ticket_price CASCADE;
CREATE TABLE ticket_price (
  id serial PRIMARY KEY,
  performance_id integer NOT NULL REFERENCES performance (id) ON DELETE CASCADE,
  place_type integer NOT NULL,
  price integer NOT NULL
);