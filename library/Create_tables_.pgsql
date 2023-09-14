-- Create the Book table
CREATE TABLE Book (
    isbn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255)
);
-- Create an ENUM type for BookStatus
CREATE TYPE BookStatus AS ENUM ('AVAILABLE', 'BORROWED', 'LOST');

-- Create the BookCopy table with a foreign key reference to Book
CREATE TABLE BookCopy (
    id SERIAL PRIMARY KEY,
    isbn VARCHAR(20) REFERENCES Book(isbn),
    status BookStatus DEFAULT 'AVAILABLE'
);

-- Create the Borrower table
CREATE TABLE Borrower (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    cne VARCHAR(20)
);

-- Create the Borrow table with foreign key references to BookCopy and Borrower
CREATE TABLE Borrow (
    id SERIAL PRIMARY KEY,
    id_copy INT REFERENCES BookCopy(id),
    id_borrower INT REFERENCES Borrower(id),
    date_borrow DATE,
    date_return_agreement DATE,
    date_actual_return DATE
);



-- Trigger to set the status of BookCopy to 'AVAILABLE' when inserting a new copy
CREATE OR REPLACE FUNCTION set_book_copy_status()
RETURNS TRIGGER AS $$
BEGIN
    NEW.status = 'AVAILABLE';
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;



-- Trigger to set the status of BookCopy to 'BORROWED' when inserting a new borrow
CREATE OR REPLACE FUNCTION set_borrowed_book_copy_status()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE BookCopy
    SET status = 'BORROWED'
    WHERE id = NEW.id_copy;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_borrowed_book_copy_status_trigger
AFTER INSERT ON Borrow
FOR EACH ROW
EXECUTE FUNCTION set_borrowed_book_copy_status();

-- Create a SQL function to update the status of BookCopy records
CREATE OR REPLACE FUNCTION update_book_copy_status()
RETURNS VOID AS $$
BEGIN
    -- Set the status to 'AVAILABLE' for records where date_actual_return is not null
    UPDATE BookCopy
    SET status = 'AVAILABLE'
    WHERE date_actual_return IS NOT NULL;

    -- Set the status to 'LOST' for records where date_return_agreement > current date and date_actual_return is null
    UPDATE BookCopy
    SET status = 'LOST'
    WHERE date_return_agreement > CURRENT_DATE AND date_actual_return IS NULL;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER set_book_copy_status_trigger
BEFORE INSERT ON BookCopy
FOR EACH ROW
EXECUTE FUNCTION set_book_copy_status();