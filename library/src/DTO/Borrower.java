package DTO;

public class Borrower {
    private int id;
    private String name;
    private String cne;

    public Borrower(int id, String name, String cne) {
        this.id = id;
        this.name = name;
        this.cne = cne;
    }

    public Borrower(String name2, String cne2) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cne='" + cne + '\'' +
                '}';
    }
}
