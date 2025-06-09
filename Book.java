public class Book {
    String title;
    String author;
    int year;
    String category;

    public Book(String title, String author, int year, String category) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.category = category;
    }

    public void display() {
        System.out.println("Title: " + title + ", Author: " + author + ", Year: " + year + ", Category: " + category);
    }
}
