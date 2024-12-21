-- Create table `events`
CREATE TABLE events (
  id_event SERIAL PRIMARY KEY,
  nom_event VARCHAR(26) NOT NULL,
  date_event DATE NOT NULL DEFAULT CURRENT_DATE,
  description TEXT NOT NULL,
  id_user INT NOT NULL
);

-- Create table `reservations`
CREATE TABLE reservations (
  id_reservation SERIAL PRIMARY KEY,
  id_user INT NOT NULL,
  id_event INT NOT NULL,
  id_salle INT NOT NULL,
  id INT NOT NULL
);

-- Create table `salles`
CREATE TABLE salles (
  id_salles SERIAL PRIMARY KEY,
  nom_salle VARCHAR(26) NOT NULL,
  capacity INT NOT NULL
);

-- Create table `terrains`
CREATE TABLE terrains (
  id_terrain SERIAL PRIMARY KEY,
  nom_terrain VARCHAR(26) NOT NULL,
  type VARCHAR(26) NOT NULL
);

-- Create table `utilisateurs`
CREATE TABLE utilisateurs (
  id_user SERIAL PRIMARY KEY,
  nom VARCHAR(26) NOT NULL,
  prenom VARCHAR(26) NOT NULL,
  email VARCHAR(26) NOT NULL,
  type VARCHAR(26) NOT NULL
);
