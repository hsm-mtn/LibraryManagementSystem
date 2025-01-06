import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Book {
    private String bookId;
    private String bookName;
    private String author;
    private int yearOfPublication;
    private boolean isAvailable;

    public Book(String bookId, String bookName, String author, int yearOfPublication, boolean isAvailable) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.isAvailable = isAvailable;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "ID: " + bookId + ", Title: " + bookName + ", Author: " + author + ", Year: " + yearOfPublication + ", Available: " + (isAvailable ? "Yes" : "No");
    }
}

public class LibraryManagementSystem {

    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        try {
            loadBooks(books);
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the library. How can we help you today?");
            System.out.println("1. Add a book");
            System.out.println("2. List all books");
            System.out.println("3. Search for a book");
            System.out.println("4. Delete a book");
            System.out.println("5. Borrow a book");
            System.out.println("6. Return a book");
            System.out.println("7. Save and exit");
            System.out.print("Please select an option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBooks(sc, books);
                    break;
                case 2:
                    listBooks(books);
                    break;
                case 3:
                    searchBooks(sc, books);
                    break;
                case 4:
                    deleteBooks(sc, books);
                    break;
                case 5:
                    borrowBooks(sc, books);
                    break;
                case 6:
                    returnBooks(sc, books);
                    break;
                case 7:
                    saveBooks(books);
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void addBooks(Scanner sc, List<Book> books) {
        try {
            System.out.println("Add the info about your book:");
            System.out.print("ID: ");
            String getBookId = sc.nextLine();
            if (getBookId.isEmpty()) {
                System.out.println("This field cannot be empty.");
                return;
            }

            System.out.print("Title: ");
            String getBookName = sc.nextLine();
            if (getBookName.isEmpty()) {
                System.out.println("This field cannot be empty.");
                return;
            }

            System.out.print("Author: ");
            String getAuthor = sc.nextLine();
            if (getAuthor.isEmpty()) {
                System.out.println("Author set to 'Unknown'.");
                getAuthor = "Unknown";
            }

            System.out.print("Year of publication: ");
            int getYearOfPublication = sc.nextInt();
            sc.nextLine(); // Consume newline
            if (getYearOfPublication <= 0) {
                System.out.println("Invalid year. Please enter a positive number.");
                return;
            }

            books.add(new Book(getBookId, getBookName, getAuthor, getYearOfPublication, true));
            System.out.println("The book has been added successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid input!");
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
    }

    public static void listBooks(List<Book> books) {
        try {
            System.out.println("Showing all books:");
            if (books.isEmpty()) {
                System.out.println("Sorry, the list is empty.");
            } else {
                for (Book bk : books) {
                    System.out.println(bk);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
    }

    public static void searchBooks(Scanner sc, List<Book> books) {
        try {
            System.out.print("Please enter the ID or title of the book you're looking for: ");
            String search = sc.nextLine();
            boolean found = false;
            for (Book bk : books) {
                if (bk.getBookName().equalsIgnoreCase(search) || bk.getBookId().equals(search)) {
                    System.out.println(bk);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Sorry, book not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid input!");
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
    }

    public static void deleteBooks(Scanner sc, List<Book> books) {
        try {
            System.out.print("Please enter the ID or title of the book you want to delete: ");
            String search = sc.nextLine();
            boolean found = false;
            for (Book bk : books) {
                if (bk.getBookName().equalsIgnoreCase(search) || bk.getBookId().equals(search)) {
                    System.out.println(bk);
                    System.out.print("Are you sure you want to delete this book? (yes/no): ");
                    String answer = sc.nextLine();
                    if (answer.equalsIgnoreCase("yes")) {
                        books.remove(bk);
                        System.out.println("The book has been deleted successfully.");
                    } else {
                        System.out.println("Deletion canceled.");
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Sorry, book not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid input!");
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
    }

    public static void borrowBooks(Scanner sc, List<Book> books) {
        try {
            System.out.print("Which book do you want to borrow? (Enter ID or title): ");
            String answer = sc.nextLine();
            boolean found = false;
            for (Book bk : books) {
                if (bk.getBookId().equalsIgnoreCase(answer) || bk.getBookName().equalsIgnoreCase(answer)) {
                    found = true;
                    if (bk.isAvailable()) {
                        bk.setAvailable(false);
                        System.out.println("You have successfully borrowed the book.");
                    } else {
                        System.out.println("The book is already borrowed.");
                    }
                    break;
                }
            }
            if (!found) {
                System.out.println("Sorry, book not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid input!");
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
    }

    public static void returnBooks(Scanner sc, List<Book> books) {
        try {
            System.out.print("Which book do you want to return? (Enter ID or title): ");
            String answer = sc.nextLine();
            boolean found = false;
            for (Book bk : books) {
                if (bk.getBookId().equalsIgnoreCase(answer) || bk.getBookName().equalsIgnoreCase(answer)) {
                    found = true;
                    if (!bk.isAvailable()) {
                        bk.setAvailable(true);
                        System.out.println("You have successfully returned the book.");
                    } else {
                        System.out.println("The book is already available.");
                    }
                    break;
                }
            }
            if (!found) {
                System.out.println("Sorry, book not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid input!");
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
    }

    public static void saveBooks(List<Book> books) {
        try (FileWriter writer = new FileWriter("books.txt")) {
            for (Book bk : books) {
                writer.write(bk.getBookId() + "," + bk.getBookName() + "," + bk.getAuthor() + "," + bk.getYearOfPublication() + "," + bk.isAvailable() + "\n");
            }
            System.out.println("Books saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    public static void loadBooks(List<Book> books) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    books.add(new Book(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Boolean.parseBoolean(parts[4])));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting with an empty library.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}