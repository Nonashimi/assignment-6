package ex1;

import java.util.*;

public class Library {
    private BookInventorySystem bookInventorySystem;
    private UserManagementSystem userManagementSystem;

    public Library() {
        this.bookInventorySystem = new BookInventorySystem();
        this.userManagementSystem = new UserManagementSystem();
    }

    public boolean borrowBook(String bookTitle, String userName) {
        Book book = bookInventorySystem.findBookByTitle(bookTitle);
        if (book != null && book.isAvailable()) {
            User user = userManagementSystem.findUserByIDS(userName);
            if (user != null) {
                book.borrowBook();
                user.borrowBook(book);
                return true;
            }
        }
        return false;
    }

    public void getAllBooks(){
        bookInventorySystem.getAllBooks();
    }

    public boolean returnBook(String bookTitle, String userName) {
        Book book = bookInventorySystem.findBookByTitle(bookTitle);
        if (book != null && !book.isAvailable()) {
            User user = userManagementSystem.findUserByIDS(userName);
            if (user != null && user.hasBorrowedBook(book)) {
                book.returnBook();
                user.returnBook(book);
                return true;
            }
        }
        return false;
    }

    public List<Book> searchBookByTitle(String title) {
        return bookInventorySystem.searchBookByTitle(title);
    }

    public List<Book> searchBookByAuthor(String author) {
        return bookInventorySystem.searchBookByAuthor(author);
    }
    public boolean findUsers(String id){
        return userManagementSystem.findUserById(id);
    }

    public boolean checkBookAvailability(String bookTitle) {
        Book book = bookInventorySystem.findBookByTitle(bookTitle);
        return book != null && book.isAvailable();
    }

    public User findUserByIDS(String id){
        return userManagementSystem.findUserByIDS(id);
    }
}

class BookInventorySystem {
    private Map<String, Book> books;

    public BookInventorySystem() {
        this.books = new HashMap<>();
        books.put("The Great Gatsby", new Book("The Great Gatsby", "F. Scott Fitzgerald"));
        books.put("To Kill a Mockingbird", new Book("To Kill a Mockingbird", "Harper Lee"));
        books.put("1984", new Book("1984", "George Orwell"));
        books.put("The Catcher in the Rye", new Book("The Catcher in the Rye", "J.D. Salinger"));
        books.put("To Kill a Mockingbird", new Book("To Kill a Mockingbird", "Harper Lee"));
        books.put("1984", new Book("1984", "George Orwell"));
        books.put("Pride and Prejudice", new Book("Pride and Prejudice", "Jane Austen"));
        books.put("The Great Gatsby", new Book("The Great Gatsby", "F. Scott Fitzgerald"));
        books.put("The Lord of the Rings", new Book("The Lord of the Rings", "J.R.R. Tolkien"));
        books.put("The Hobbit", new Book("The Hobbit", "J.R.R. Tolkien"));
        books.put("The Chronicles of Narnia", new Book("The Chronicles of Narnia", "C.S. Lewis"));
        books.put("Harry Potter and the Philosopher's Stone", new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling"));
        books.put("Alice's Adventures in Wonderland", new Book("Alice's Adventures in Wonderland", "Lewis Carroll"));

    }
    public Book findBookByTitle(String title) {
        return books.get(title);
    }

    public List<Book> searchBookByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public void  getAllBooks() {
        for (Book book : books.values()) {
            System.out.println(book.getAuthor()+"  "+book.getTitle());
        }
    }

    public List<Book> searchBookByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().contains(title)) {
                result.add(book);
            }
        }
        return result;
    }
}

class UserManagementSystem {
     Map<String, User> users;

    public UserManagementSystem() {
        this.users = new HashMap<>();
        users.put("2201000", new User("Olzhas" , "2201000"));
        users.put("2201001", new User("Arman" , "2201001"));
        users.put("2201002" , new User("Arna" , "2201002"));
        users.put("2201003", new User("Aiaru" , "2201003"));
    }
    public User findUserByIDS(String id) {
        return users.get(id);
    }
    public boolean findUserById(String id){
        for (int i = 0; i < users.size(); i++) {
            if(users.containsKey(id)){
                return true;
        }
        }
        return false;
    }
}

class Book {
    private String title;
    private String author;
    private boolean available;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void borrowBook() {
        available = false;
    }

    public void returnBook() {
        available = true;
    }
}

class User {
    private String name;
    private String id;
    private List<Book> borrowedBooks;

    public User(String name , String id) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getId(){
        return id;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    public boolean hasBorrowedBook(Book book) {
        return borrowedBooks.contains(book);
    }
}

class LibraryManagementTest {
    public static void main(String[] args) {
        Library libraryFacade = new Library();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input your id: ");
        User user = null;
        String id = "";
        while (true) {
            id = scanner.nextLine();
            System.out.println();
            if (libraryFacade.findUsers(id)) {
                user = libraryFacade.findUserByIDS(id);
                break;
            }
            System.out.println("this id is not exist :< try again:>");
        }

        System.out.println("choose the one function :");
        int chose = 0;
        while (true) {
            while (true) {
                System.out.println("Checking book availability --> 1");
                System.out.println("Searching books by author --> 2");
                System.out.println("Show  all books --> 3");
                System.out.println("Borrowing a book --> 4");
                System.out.println("Returning a book --> 5");
                System.out.println("exit --> 6");
                System.out.print("--> ");
                chose = scanner.nextInt();
                scanner.nextLine(); // Используем nextLine() для считывания символа новой строки

                if (chose >= 1 && chose <= 6) {
                    break;
                }

                System.out.println("choose between 1-6 >:<");
            }

            if (chose == 1) {
                System.out.print("write name of book: ");
                String nameOfBook = scanner.nextLine();
                System.out.println("Title: " + nameOfBook + ", available: " + libraryFacade.checkBookAvailability(nameOfBook));
            } else if (chose == 2) {
                System.out.print("write name of author");
                String nameAuthor = scanner.nextLine();
                List<Book> booksByAuthor = libraryFacade.searchBookByAuthor(nameAuthor);
                for (Book book : booksByAuthor) {
                    System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor());
                }
            } else if (chose == 3) {
                libraryFacade.getAllBooks();
            } else if (chose == 4) {
                System.out.print("write name of book which you want to borrow");
                String nameOfBook = scanner.nextLine();
                System.out.println();
                boolean borrowResult = libraryFacade.borrowBook(nameOfBook, "User1");
                System.out.println("Borrowing result: " + (borrowResult ? "Success" : "Failure"));
            } else if (chose == 5) {
                System.out.print("write name of book which you want to return");
                String nameOfBook = scanner.nextLine();
                System.out.println();
                boolean returnResult = libraryFacade.returnBook(nameOfBook, "User1");
                System.out.println("Returning result: " + (returnResult ? "Success" : "Failure"));
            } else {
                break;
            }
        }
        System.out.println("good luck");
    }

}
