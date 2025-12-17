package com.library.app;

import java.util.*;

class Book {
    int id;
    String title;
    String author;
    boolean isAvailable;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }
}

class Member {
    int id;
    String name;

    public Member(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Library {
    private List<Book> bookList = new LinkedList<>();
    private List<Member> memberList = new ArrayList<>();
    private int nextBookId = 1;
    private int nextMemberId = 1;

    private int hashTableSize;
    private List<List<Book>> bookHashTable;
    private List<List<Member>> memberHashTable;

    public Library(int size) {
        this.hashTableSize = size;
        bookHashTable = new ArrayList<>(size);
        memberHashTable = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            bookHashTable.add(new LinkedList<>());
            memberHashTable.add(new LinkedList<>());
        }
    }

    private int hashFunction(int id) {
        return id % hashTableSize;
    }

    public void addBook(String title, String author) {
        Book book = new Book(nextBookId, title, author);
        bookList.add(book);
        int index = hashFunction(book.id);
        bookHashTable.get(index).add(book);
        System.out.println("\nKitaab safaltaapoorvak jodi gayi! ID: " + nextBookId);
        nextBookId++;
    }

    public void deleteBook(int id) {
        for (Iterator<Book> it = bookList.iterator(); it.hasNext();) {
            Book book = it.next();
            if (book.id == id) {
                int index = hashFunction(id);
                bookHashTable.get(index).remove(book);
                it.remove();
                System.out.println("\nKitaab ID " + id + " safaltaapoorvak hata di gayi hai.");
                return;
            }
        }
        System.out.println("\nError: Kitaab nahi mili. Kripya sahi ID daalein.");
    }

    public Book findBook(int id) {
        int index = hashFunction(id);
        for (Book book : bookHashTable.get(index)) {
            if (book.id == id) return book;
        }
        return null;
    }

    public void addMember(String name) {
        Member member = new Member(nextMemberId, name);
        memberList.add(member);
        int index = hashFunction(member.id);
        memberHashTable.get(index).add(member);
        System.out.println("\nSadasya safaltaapoorvak register ho gaya! ID: " + nextMemberId);
        nextMemberId++;
    }

    public Member findMember(int id) {
        int index = hashFunction(id);
        for (Member member : memberHashTable.get(index)) {
            if (member.id == id) return member;
        }
        return null;
    }

    public void issueBook(int bookId, int memberId) {
        Book book = findBook(bookId);
        Member member = findMember(memberId);

        if (book == null) {
            System.out.println("\nError: Kitaab nahi mili.");
            return;
        }
        if (member == null) {
            System.out.println("\nError: Sadasya nahi mila.");
            return;
        }

        if (book.isAvailable) {
            book.isAvailable = false;
            System.out.println("\nKitaab \"" + book.title + "\" sadasya \"" + member.name + "\" ko jaari ki gayi.");
        } else {
            System.out.println("\nError: Yeh kitaab pehle se hi jaari hai.");
        }
    }

    public void returnBook(int bookId) {
        Book book = findBook(bookId);

        if (book == null) {
            System.out.println("\nError: Kitaab nahi mili.");
            return;
        }

        if (!book.isAvailable) {
            book.isAvailable = true;
            System.out.println("\nKitaab \"" + book.title + "\" safaltaapoorvak wapas kar di gayi.");
        } else {
            System.out.println("\nError: Yeh kitaab pehle se hi wapas aa chuki hai.");
        }
    }

    public void displayAllBooks() {
        if (bookList.isEmpty()) {
            System.out.println("\nLibrary mein koi kitaab nahi hai.");
            return;
        }

        System.out.println("\n--- Sabhi Kitaaben ---");
        System.out.println("ID\tTitle\t\tAuthor\t\tAvailability");

        for (Book book : bookList) {
            System.out.println(book.id + "\t" + book.title + "\t" + book.author + "\t\t" +
                    (book.isAvailable ? "Uplabdh" : "Jaari"));
        }
    }

    public void displayAllMembers() {
        if (memberList.isEmpty()) {
            System.out.println("\nKoi sadasya register nahi hai.");
            return;
        }

        System.out.println("\n--- Sabhi Sadasya ---");
        System.out.println("ID\tName");

        for (Member member : memberList) {
            System.out.println(member.id + "\t" + member.name);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library(100);
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Kitaab jodein");
            System.out.println("2. Kitaab hataein");
            System.out.println("3. Kitaab khojein");
            System.out.println("4. Sadasya register karein");
            System.out.println("5. Sadasya khojein");
            System.out.println("6. Kitaab jaari karein");
            System.out.println("7. Kitaab wapas karein");
            System.out.println("8. Sabhi kitaaben dikhaein");
            System.out.println("9. Sabhi sadasyon ko dikhaein");
            System.out.println("10. Bahar niklein");
            System.out.print("Apna vikalp chunein: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            int id, bookId, memberId;
            String title, author, name;

            switch (choice) {
                case 1:
                    System.out.print("Kitaab ka sheershak: ");
                    title = sc.nextLine();
                    System.out.print("Kitaab ke lekhak ka naam: ");
                    author = sc.nextLine();
                    library.addBook(title, author);
                    break;
                case 2:
                    System.out.print("Hataane ke liye kitaab ka ID: ");
                    id = sc.nextInt();
                    library.deleteBook(id);
                    break;
                case 3:
                    System.out.print("Khojne ke liye kitaab ka ID: ");
                    id = sc.nextInt();
                    System.out.println(library.findBook(id) != null ? "\nKitaab mil gayi!" : "\nKitaab nahi mili.");
                    break;
                case 4:
                    System.out.print("Sadasya ka naam: ");
                    name = sc.nextLine();
                    library.addMember(name);
                    break;
                case 5:
                    System.out.print("Khojne ke liye sadasya ka ID: ");
                    id = sc.nextInt();
                    System.out.println(library.findMember(id) != null ? "\nSadasya mil gaya!" : "\nSadasya nahi mila.");
                    break;
                case 6:
                    System.out.print("Kitaab ka ID: ");
                    bookId = sc.nextInt();
                    System.out.print("Sadasya ka ID: ");
                    memberId = sc.nextInt();
                    library.issueBook(bookId, memberId);
                    break;
                case 7:
                    System.out.print("Wapas karne ke liye kitaab ka ID: ");
                    bookId = sc.nextInt();
                    library.returnBook(bookId);
                    break;
                case 8:
                    library.displayAllBooks();
                    break;
                case 9:
                    library.displayAllMembers();
                    break;
                case 10:
                    System.out.println("\nBahut dhanyavaad, alvida!");
                    break;
                default:
                    System.out.println("\nInvalid vikalp. 1–10 ka vikalp chunein.");
            }
        } while (choice != 10);

        sc.close();
    }
}
