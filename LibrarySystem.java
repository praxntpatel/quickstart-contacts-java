import java.util.*;

public class LibrarySystem {
    static Scanner sc = new Scanner(System.in);
    static Map<String, String> users = new HashMap<>();
    static List<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        seedBooks();
        System.out.println("Welcome to the Library System!");

        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    if (loginUser()) {
                        libraryMenu();
                    }
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    static void registerUser() {
        System.out.print("Enter username: ");
        String user = sc.nextLine();
        System.out.print("Enter password: ");
        String pass = sc.nextLine();
        users.put(user, pass);
        System.out.println("User registered successfully.");
    }

    static boolean loginUser() {
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        if (users.containsKey(user) && users.get(user).equals(pass)) {
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    }

    static void libraryMenu() {
        while (true) {
            System.out.println("\nLibrary Menu:\n1. View All Books\n2. Search by Category\n3. Logout");
            System.out.print("Enter your choice: ");
            String ch = sc.nextLine();

            switch (ch) {
                case "1":
                    for (Book b : books) b.display();
                    break;
                case "2":
                    System.out.print("Enter category: ");
                    String cat = sc.nextLine();
                    boolean found = false;
                    for (Book b : books) {
                        if (b.category.equalsIgnoreCase(cat)) {
                            b.display();
                            found = true;
                        }
                    }
                    if (!found) System.out.println("No books found in this category.");
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    static void seedBooks() {
        books.add(new Book("The Alchemist", "Paulo Coelho", 1988, "Fiction"));
        books.add(new Book("Java: A Beginner's Guide", "Herbert Schildt", 2018, "Programming"));
        books.add(new Book("Clean Code", "Robert C. Martin", 2008, "Programming"));
        books.add(new Book("A Brief History of Time", "Stephen Hawking", 1988, "Science"));
    }
}
