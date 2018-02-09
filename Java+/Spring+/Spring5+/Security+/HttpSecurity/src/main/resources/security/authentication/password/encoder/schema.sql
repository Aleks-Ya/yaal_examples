-- CREATE TABLE users (
--   username VARCHAR PRIMARY KEY,
--   password VARCHAR,
--   enabled  BOOLEAN
-- );
--
-- CREATE TABLE authorities (
--   username  VARCHAR,
--   authority VARCHAR
-- );

CREATE TABLE users (
  username VARCHAR_IGNORECASE(50) NOT NULL PRIMARY KEY,
  password VARCHAR_IGNORECASE(80) NOT NULL,
  enabled  BOOLEAN                NOT NULL
);

CREATE TABLE authorities (
  username  VARCHAR_IGNORECASE(50) NOT NULL,
  authority VARCHAR_IGNORECASE(50) NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);
CREATE UNIQUE INDEX ix_auth_username
  ON authorities (username, authority);