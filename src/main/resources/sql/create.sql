DROP TABLE IF EXISTS "Theater" CASCADE;
CREATE TABLE "Theater" (
  "ID" serial PRIMARY KEY,
  "Name" text NOT NULL,
  "Address" text NOT NULL,
  "Info" text
);

DROP TABLE IF EXISTS "Actor" CASCADE;
CREATE TABLE "Actor" (
  "ID" serial PRIMARY KEY,
  "Name" text NOT NULL,
  "Info" text,
  "Theater ID" integer NOT NULL,
  CONSTRAINT "FK_Actor.Theater ID"
    FOREIGN KEY ("Theater ID")
      REFERENCES "Theater"("ID") ON DELETE CASCADE
);

DROP TABLE IF EXISTS "Director" CASCADE;
CREATE TABLE "Director" (
  "ID" serial PRIMARY KEY,
  "Name" text NOT NULL,
  "Info" text,
  "Theater ID" integer NOT NULL,
  CONSTRAINT "FK_Director.Theater ID"
    FOREIGN KEY ("Theater ID")
      REFERENCES "Theater"("ID") ON DELETE CASCADE
);

DROP TABLE IF EXISTS "Play" CASCADE;
CREATE TABLE "Play" (
  "ID" serial PRIMARY KEY,
  "Name" text NOT NULL,
  "Duration" interval NOT NULL,
  "Info" text,
  "Theater ID" integer NOT NULL,
  "Director ID" integer NOT NULL,
  CONSTRAINT "FK_Play.Theater ID"
    FOREIGN KEY ("Theater ID")
      REFERENCES "Theater"("ID") ON DELETE CASCADE,
  CONSTRAINT "FK_Play.Director ID"
    FOREIGN KEY ("Director ID")
      REFERENCES "Director"("ID") ON DELETE CASCADE
);

DROP TABLE IF EXISTS "Role" CASCADE;
CREATE TABLE "Role" (
  "ID" serial PRIMARY KEY,
  "Actor ID" integer NOT NULL,
  "Play ID" integer NOT NULL,
  "Info" text,
  CONSTRAINT "FK_Role.Actor ID"
    FOREIGN KEY ("Actor ID")
      REFERENCES "Actor"("ID") ON DELETE CASCADE,
  CONSTRAINT "FK_Role.Play ID"
    FOREIGN KEY ("Play ID")
      REFERENCES "Play"("ID") ON DELETE CASCADE
);

DROP TABLE IF EXISTS "Place" CASCADE;
CREATE TABLE "Place" (
  "ID" serial PRIMARY KEY,
  "Number" text NOT NULL,
  "Place type" integer NOT NULL,
  "Theater ID" integer NOT NULL,
  CONSTRAINT "FK_Place.Theater ID"
    FOREIGN KEY ("Theater ID")
      REFERENCES "Theater"("ID") ON DELETE CASCADE
);

DROP TABLE IF EXISTS "Performance" CASCADE;
CREATE TABLE "Performance" (
  "ID" serial PRIMARY KEY,
  "Play ID" integer NOT NULL,
  "Date and time" timestamp NOT NULL,
  CONSTRAINT "FK_Performance.Play ID"
    FOREIGN KEY ("Play ID")
      REFERENCES "Play"("ID") ON DELETE CASCADE
);

DROP TABLE IF EXISTS "Ticket" CASCADE;
CREATE TABLE "Ticket" (
  "ID" serial PRIMARY KEY,
  "Performance ID" integer NOT NULL,
  "Place ID" integer NOT NULL,
  "Customer name" text NOT NULL,
  "Customer phone number" text NOT NULL,
  CONSTRAINT "FK_Ticket.Performance ID"
    FOREIGN KEY ("Performance ID")
      REFERENCES "Performance"("ID") ON DELETE CASCADE,
  CONSTRAINT "FK_Ticket.Place ID"
    FOREIGN KEY ("Place ID")
      REFERENCES "Place"("ID") ON DELETE CASCADE
);

DROP TABLE IF EXISTS "Ticket price" CASCADE;
CREATE TABLE "Ticket price" (
  "ID" serial PRIMARY KEY,
  "Performance ID" integer NOT NULL,
  "Place type" integer NOT NULL,
  "Price" integer NOT NULL,
  CONSTRAINT "FK_Ticket price.Performance ID"
    FOREIGN KEY ("Performance ID")
      REFERENCES "Performance"("ID") ON DELETE CASCADE
);