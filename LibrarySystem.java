import java.util.ArrayList;
import java.util.Scanner;

// Base class
class Book {
    protected String title;
    protected String author;
    protected int id;
    protected boolean isBorrowed;

    public Book(String title, String author, int id) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.isBorrowed = false;
    }

    public void borrow() {
        if (!isBorrowed) {
            isBorrowed = true;
            System.out.println(title + " has been borrowed.");
        } else {
            System.out.println(title + " is already borrowed.");
        }
    }

    public void returnBook() {
        if (isBorrowed) {
            isBorrowed = false;
            System.out.println(title + " has been returned.");
        } else {
            System.out.println(title + " was not borrowed.");
        }
    }

    public void displayInfo() {
        System.out.println("ID: " + id + " | Title: " + title + " | Author: " + author + " | Borrowed: " + isBorrowed);
    }

    public boolean matches(String keyword) {
        return title.toLowerCase().contains(keyword.toLowerCase()) || author.toLowerCase().contains(keyword.toLowerCase());
    }
}

// Subclass: Textbook
class Textbook extends Book {
    private String subject;

    public Textbook(String title, String author, int id, String subject) {
        super(title, author, id);
        this.subject = subject;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Subject: " + subject);
    }
}

// Subclass: Novel
class Novel extends Book {
    private String genre;

    public Novel(String title, String author, int id, String genre) {
        super(title, author, id);
        this.genre = genre;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Genre: " + genre);
    }
}

// Main class
public class LibrarySystem {
    private static ArrayList<Book> books = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    private static void addSampleBooks() {
        books.add(new Textbook("Data Structures", "Mark Allen", 1, "Computer Science"));
        books.add(new Novel("The Alchemist", "Paulo Coelho", 2, "Fiction"));
        books.add(new Textbook("Physics Fundamentals", "Isaac Newton", 3, "Physics"));
        books.add(new Novel("1984", "George Orwell", 4, "Dystopian"));
    }

    private static void displayMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. Display All Books");
        System.out.println("2. Search Book");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void displayAllBooks() {
        for (Book book : books) {
            book.displayInfo();
            System.out.println("-----------------------");
        }
    }

    private static void searchBook() {
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine();
        boolean found = false;
        for (Book book : books) {
            if (book.matches(keyword)) {
                book.displayInfo();
                System.out.println("-----------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found with the given keyword.");
        }
    }

    private static void borrowBook() {
        System.out.print("Enter Book ID to borrow: ");
        int id = Integer.parseInt(scanner.nextLine());
        for (Book book : books) {
            if (book.id == id) {
                book.borrow();
                return;
            }
        }
        System.out.println("Book with ID " + id + " not found.");
    }

    private static void returnBook() {
        System.out.print("Enter Book ID to return: ");
        int id = Integer.parseInt(scanner.nextLine());
        for (Book book : books) {
            if (book.id == id) {
                book.returnBook();
                return;
            }
        }
        System.out.println("Book with ID " + id + " not found.");
    }

    public static void main(String[] args) {
        addSampleBooks();
        while (true) {
            displayMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    displayAllBooks();
                    break;
                case "2":
                    searchBook();
                    break;
                case "3":
                    borrowBook();
                    break;
                case "4":
                    returnBook();
                    break;
                case "5":
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}