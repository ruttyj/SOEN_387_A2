#List all books
SELECT * FROM books;

#Get book info (id)
SELECT *
FROM books
WHERE id = 2;

#Get book info (isbn)
SELECT * 
FROM books
WHERE isbn = '9173464813408';

#Add new book (id will be returned)
INSERT INTO books (id, title, description, isbn, author_fname, author_lname, publisher_company, publisher_address, cover_iamge) VALUES (id, 'NEW BOOK', 'A NEW BOOK ABOUT BOOKS', '1234567891012', 'FOO', 'BAR', 'CONCORDIA PUBLISHING', 'MONTREAL');

#update book info (id, book details passed as arg)
UPDATE books
SET description = 'AN EVEN NEWER BOOK ABOUT BOOKS'
WHERE id = 16;

#set/reset book cover image

#delete book (id)
DELETE FROM books
WHERE id = 16;

#delete all books
DELETE FROM books;