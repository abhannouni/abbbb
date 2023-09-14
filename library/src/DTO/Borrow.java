package DTO;

import java.util.Date;

public class Borrow {
    private int id;
    private int bookCopyId; // Foreign key to reference the book copy
    private int borrowerId; // Foreign key to reference the borrower
    private Date dateBorrow;
    private Date dateReturnAgreement;
    private Date dateReturn;


    public Borrow(int id, int bookCopyId, int borrowerId, Date dateBorrow, Date dateReturnAgreement, Date dateReturn) {
        this.id = id;
        this.bookCopyId = bookCopyId;
        this.borrowerId = borrowerId;
        this.dateBorrow = dateBorrow;
        this.dateReturnAgreement = dateReturnAgreement;
        this.dateReturn = dateReturn;
    }

    public Borrow(int bookCopyId2, int borrowerId2, Date dateBorrow2, Date dateReturnAgreement2) {
        this.bookCopyId = bookCopyId2;
        this.borrowerId = borrowerId2;
        this.dateBorrow = dateBorrow2;
        this.dateReturnAgreement = dateReturnAgreement2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(int bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Date getDateBorrow() {
        return dateBorrow;
    }

    public void setDateBorrow(Date dateBorrow) {
        this.dateBorrow = dateBorrow;
    }

    public Date getDateReturnAgreement() {
        return dateReturnAgreement;
    }

    public void setDateReturnAgreement(Date dateReturnAgreement) {
        this.dateReturnAgreement = dateReturnAgreement;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", bookCopyId=" + bookCopyId +
                ", borrowerId=" + borrowerId +
                ", dateBorrow=" + dateBorrow +
                ", dateReturnAgreement=" + dateReturnAgreement +
                ", dateReturn=" + dateReturn +
                '}';
    }
}
