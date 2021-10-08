DROP TABLE IF EXISTS users;

CREATE TABLE users (
  user_name VARCHAR(50) NOT NULL PRIMARY KEY,
  password VARCHAR(1000) NOT NULL
);

DROP TABLE IF EXISTS words;

CREATE TABLE words (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_name VARCHAR(50) NOT NULL,
  word VARCHAR(1000) NOT NULL,
  score INT DEFAULT 0
);

ALTER TABLE words ADD FOREIGN KEY (user_name) REFERENCES users(user_name);

DROP INDEX IF EXISTS IDX_USER_WORD;

CREATE UNIQUE INDEX IDX_USER_WORD ON words(user_name, word);

