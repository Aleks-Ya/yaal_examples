INSERT INTO authors (id, name) VALUES (1, 'F. Scott Fitzgerald');
INSERT INTO authors (id, name) VALUES (2, 'Harper Lee');
INSERT INTO authors (id, name) VALUES (3, 'William Shakespeare');

INSERT INTO books (id, name, abstractPart) VALUES (1, 'The Great Gatsby',
                                                   'THE GREAT GATSBY, F. Scott Fitzgerald’s third book, stands as the supreme achievement of his career. This exemplary novel of the Jazz Age has been acclaimed by generations of readers. The story of the fabulously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan, of lavish parties on Long Island at a time when The New York Times noted “gin was the national drink and sex the national obsession,” it is an exquisitely crafted tale of America in the 1920s.');
INSERT INTO books (id, name, abstractPart) VALUES (2, 'Romeo and Juliet',
                                                   'In Romeo and Juliet, Shakespeare creates a world of violence and generational conflict in which two young people fall in love and die because of that love. The story is rather extraordinary in that the normal problems faced by young lovers are here so very large. It is not simply that the families of Romeo and Juliet disapprove of the lover affection for each other; rather, the Montagues and the Capulets are on opposite sides in a blood feud and are trying to kill each other on the streets of Verona. ');
INSERT INTO books (id, name, abstractPart) VALUES (3, 'Hamlet', 'One of the greatest plays of all time, the compelling tragedy of the tormented young prince of Denmark continues to capture the imaginations of modern audiences worldwide.');

INSERT INTO book_to_author (bookId, authorId) VALUES (1, 1);
INSERT INTO book_to_author (bookId, authorId) VALUES (1, 2);
INSERT INTO book_to_author (bookId, authorId) VALUES (2, 3);
INSERT INTO book_to_author (bookId, authorId) VALUES (3, 3);
