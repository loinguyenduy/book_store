public class Customer { 
  private static int nextId = 1;
  //attribute
  private String customerId;
  private String name;
  private String phoneNumber;
  private String address;

  //constructor
  public Customer(String name, String phoneNumber, String address){
    this.customerId = "C" + nextId++;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  //getter
  public String getCustomerId() {
    return customerId;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getAddress() {
    return address;
  }

  //setter
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  
}
