package DTO;

public class BookCopy {
        public enum Status {
        AVAILABLE,  // The book copy is available for borrowing
        BORROWED,   // The book copy is currently borrowed
        LOST        // The book copy is lost
    }
    private int id;
    private String isbn; // Foreign key to reference the book
    private Status status; // Enum for status (you can define this enum)

    public BookCopy() {
    }
    public BookCopy(int id, String isbn, Status status) {
        this.id = id;
        this.isbn = isbn;
        this.status = status;
    }
    public BookCopy( String isbn) {
        this.isbn = isbn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookCopy{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", status=" + status +
                '}';
    }
}
