CREATE TABLE books (
  id           BIGINT AUTO_INCREMENT PRIMARY KEY,
  name         VARCHAR(100),
  abstractPart VARCHAR(1000)
);
CREATE TABLE authors (
  id   BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100)
);
CREATE TABLE book_to_author (
  bookId   BIGINT,
  authorId BIGINT,
  FOREIGN KEY (bookId) REFERENCES books (id),
  FOREIGN KEY (authorId) REFERENCES authors (id)
);