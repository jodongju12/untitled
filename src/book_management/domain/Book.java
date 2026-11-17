package book_management.domain;

public class Book {
    private String isbn;
    private String name;
    private String author;
    private int price;
    private int inventory;
    private String publish_name;
    private String category_name;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return 0;
    }

    public void setPrice(int price) {
    }

    public void setCategory_name(String categoryName) {
    }

    public void setPublish_name(String publishName) {
    }

    public Object getPublish_name() {
        return null;
    }

    public Object getInventory() {
        return null;
    }

    public Object getCategory_name() {
        return null;
    }
}