public class Book {

  //attribute
  private String bookId;
  private String title;
  private String author;
  private double price;

  //constructor
  public Book(String bookId, String title, String author, double price){
    this.bookId = bookId;
    this.title = title;
    this.author = author;
    this.price = price;
  }

  //getter
  public String getBookId(){
    return bookId;
  }

  public String getTitle(){
    return title;
  }

  public String getAuthor(){
    return author;
  }

  public double getPrice(){
    return price;
  }

  //setter
  public void setBookId(String bookId){
    this.bookId = bookId;
  }

  public void setTitle(String title){
    this.title = title;
  }

  public void setAuthor(String author){
    this.author = author;
  }

  public void setPrice(double price){
    this.price = price;
  }

}
