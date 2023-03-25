CREATE TABLE hotels (
  id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  category VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE availabilities (
  id INT NOT NULL,
  date DATE NOT NULL,
  id_hotel INT NOT NULL,
  rooms INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_hotel) REFERENCES hotels(id)
);

CREATE TABLE bookings (
  id INT NOT NULL,
  id_hotel INT NOT NULL,
  date_from DATE NOT NULL,
  date_to DATE NOT NULL,
  email VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_hotel) REFERENCES hotels(id)
);