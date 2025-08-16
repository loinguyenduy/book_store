import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static Book[] bookList;
    private static MyArrayQueue orderQueue;
    private static final List<Order> orderHistory = new ArrayList<>();

    public static void main(String[] args) {
        initData();
        loadDataFromFile();
        displayMenu();
        
        
    }

    private static void initData() {
        bookList = new Book[10];
        bookList[0] = new Book("B7", "English Grammar in Use", "Raymond Murphy", 15.99);
        bookList[1] = new Book("B3", "Clean Code", "Robert C. Martin", 22.50);
        bookList[2] = new Book("B10", "Football Tactics", "Pep Guardiola", 18.00);
        bookList[3] = new Book("B1", "IELTS Advantage", "Richard Brown", 13.99);
        bookList[4] = new Book("B8", "The Lean Startup", "Eric Ries", 20.00);
        bookList[5] = new Book("B5", "Java Programming", "James Gosling", 19.99);
        bookList[6] = new Book("B2", "Soccer Skills", "Cristiano Ronaldo", 17.50);
        bookList[7] = new Book("B9", "Bodybuilding Bible", "Arnold Schwarzenegger", 21.00);
        bookList[8] = new Book("B4", "Business Model Generation", "Alexander Osterwalder", 16.50);
        bookList[9] = new Book("B6", "The Art of Gym", "Ryan Terry", 14.99);

    orderQueue = new MyArrayQueue(10);
        BookManager.sortById(bookList);
        System.out.println("--- SYSTEM INITIALIZED ---");
    }

    private static void displayMenu() {
        while (true) {
            System.out.println("\n--- ONLINE BOOKSTORE MENU ---");
            System.out.println("1. View Books");
            System.out.println("2. Place New Order");
            System.out.println("3. Process Order");
            System.out.println("4. View Order History");
            System.out.println("5. Search Order");
            System.out.println("6. Exit");
            System.out.print("Please choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showInforBook();
                case "2" -> placeNewOrder();
                case "3" -> processOrder();
                case "4" -> viewOrderHistory();
                case "5" -> searchOrder();
                case "6" -> {
                    saveDataToFile();
                    System.out.println("Exiting program. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void saveDataToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("order_history.txt"))) {
            for (Order order : orderHistory) {
                writer.println(order.insertFile());
            }
            System.out.println("Order history is saved successfully!");
        } catch (IOException e) {
            System.out.println("Error while saving file: " + e.getMessage());
        }
    }

    private static void loadDataFromFile() {
        int maxOrderId = 0;
        try (Scanner fileScanner = new Scanner(new File("order_history.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                
                // Trích xuất thông tin khách hàng và sách
                Customer customer = new Customer(parts[1], parts[2], parts[3]);
                Order loadedOrder = new Order(customer, LocalDateTime.parse(parts[5]));

                // Lấy số thứ tự orderId để cập nhật nextId
                String orderIdStr = parts[0];
                if (orderIdStr.length() > 1 && orderIdStr.charAt(0) == 'O') {
                    try {
                        int orderNum = Integer.parseInt(orderIdStr.substring(1));
                        if (orderNum > maxOrderId) maxOrderId = orderNum;
                    } catch (NumberFormatException ignored) {}
                }
                
                // Tách các ID sách
                String[] bookIds = parts[6].split(";");
                for (String bookId : bookIds) {
                    Book bookToAdd = findBookById(bookId);
                    if (bookToAdd != null) {
                        loadedOrder.addBook(bookToAdd);
                    }
                }
                
                // Thêm đơn hàng vào danh sách lịch sử
                orderHistory.add(loadedOrder);
            }
            Order.nextId = maxOrderId + 1;
            System.out.println("Loaded " + orderHistory.size() + " order from file.");
        } catch (FileNotFoundException e) {
            System.out.println("Order history not found!");
        }
    }

    private static void placeNewOrder() {
        System.out.println("\n--- PLACE NEW ORDER ---");
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Customer Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Shipping Address: ");
        String address = scanner.nextLine();
        
        Customer newCustomer = new Customer(name, phone, address);
        Order newOrder = new Order(newCustomer, LocalDateTime.now());
        
        System.out.println("\nEnter book IDs to add to the order (type 'done' to finish):");
        showInforBook();
        
        while (true) {
            System.out.print("Book ID: ");
            String bookId = scanner.nextLine();
            if (bookId.equalsIgnoreCase("done")) {
                break;
            }
            
            Book bookToAdd = findBookById(bookId);
            if (bookToAdd != null) {
                newOrder.addBook(bookToAdd);
                System.out.println("Added '" + bookToAdd.getTitle() + "' to the order.");
            } else {
                System.out.println("Book with ID '" + bookId + "' not found. Please try again.");
            }
        }
        
        if (newOrder.getBooksInOrder().isEmpty()) {
            System.out.println("Order was cancelled because no books were added.");
            return;
        }

        if (orderQueue.enqueue(newOrder)) {
            System.out.println("Order " + newOrder.getOrderId() + " was placed successfully!");
        } else {
            System.out.println("The queue is full, cannot place more orders.");
        }
    }

    private static void processOrder() {
        System.out.println("\n--- PROCESSING ORDER ---");
        if (orderQueue.isEmpty()) {
            System.out.println("There are no orders in the queue to process.");
            return;
        }

        Order processedOrder = orderQueue.dequeue();
        if (processedOrder != null) {
            System.out.println("Processing order: " + processedOrder.getOrderId());
            orderHistory.add(processedOrder);
            processedOrder.showInfor();
        }
    }

    private static void viewOrderHistory() {
        System.out.println("\n--- ORDER HISTORY ---");
        if (orderHistory.isEmpty()) {
            System.out.println("The order history is empty.");
            return;
        }
        
        OrderManager.sortOrderHistory(orderHistory);

        for (Order order : orderHistory) {
            order.showInfor();
            System.out.println("------------------------------------");
        }
    }

    private static void searchOrder() {
        System.out.println("\n--- SEARCH ORDER ---");
        System.out.print("Please enter the Order ID to search: ");
        String orderId = scanner.nextLine();

        Order foundOrder = OrderManager.searchOrder(orderId, orderHistory);
        
        if (foundOrder != null) {
            System.out.println("Order found! Here are the details:");
            foundOrder.showInfor();
        } else {
            System.out.println("Order with ID '" + orderId + "' was not found in the history.");
        }
    }

    private static void showInforBook() {
        System.out.println("\n--- BOOK CATALOG ---");
        for (Book book : bookList) {
            System.out.println("- ID: " + book.getBookId() + " | Title: " + book.getTitle() + " | Author: " + book.getAuthor() + " | Price: $" + book.getPrice());
        }
    }

    private static Book findBookById(String bookId) {
        for (Book book : bookList) {
            if (book.getBookId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }
}