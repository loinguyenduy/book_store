import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
  public static int nextId = 1;
  //attribute
  private String orderId;
  private Customer customer;
  private double totalAmount;
  private List<Book> booksInOrder;
  private LocalDateTime orderDateTime;

  //constructor
  public Order(Customer customer, LocalDateTime orDateTime){
    this.orderId = "O" + nextId++;
    this.customer = customer;
    this.booksInOrder = new ArrayList<>();
    this.totalAmount = 0.0;
    this.orderDateTime = orDateTime;
  }


  //getter
  public String getOrderId() {
    return orderId;
  }

  public Customer getCustomer() {
    return customer;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public List<Book> getBooksInOrder() {
    return booksInOrder;
  }

  public LocalDateTime getOrderDateTime(){
    return orderDateTime;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setBooksInOrder(List<Book> booksInOrder) {
    this.booksInOrder = booksInOrder;
  }

  
  public void addBook(Book book){
    this.booksInOrder.add(book);
    this.totalAmount += book.getPrice();
    
  }

  public void showInfor(){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    System.out.println("Order ID: " + this.orderId);
    System.out.println("Customer ID: " + this.customer.getCustomerId());
    System.out.println("Customer Name: " + this.customer.getName());
    System.out.println("Customer Phone: " + this.customer.getPhoneNumber());
    System.out.println("Total Amount: " + this.totalAmount);
    System.out.println("Order Date: " + this.orderDateTime.format(formatter));
    System.out.println("Books in Order:");
      for (Book book : booksInOrder) {
          System.out.println("  - " + book.getTitle() + " (" + book.getBookId() + ")");
    }
  }

  public String insertFile(){
    StringBuilder sb = new StringBuilder();
    sb.append(orderId).append(",");
    sb.append(customer.getName()).append(",");
    sb.append(customer.getPhoneNumber()).append(",");
    sb.append(customer.getAddress()).append(",");
    sb.append(totalAmount).append(",");
    sb.append(orderDateTime.toString()).append(",");

    for (int i = 0; i < booksInOrder.size(); i++) {
        sb.append(booksInOrder.get(i).getBookId());
        if (i < booksInOrder.size() - 1) {
            sb.append(";");
        }
    }
    return sb.toString();
  }
}

