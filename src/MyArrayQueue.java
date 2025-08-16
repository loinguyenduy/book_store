public class MyArrayQueue {
  private Order[] orders;
  private int frontIndex;
  private int rearIndex;
  private int size; //total elements in arrays

  //Constructor
  MyArrayQueue(int capacity){     //capacity: maximum size
    orders = new Order[capacity];
    frontIndex = 0; 
    rearIndex = -1;
    size = 0;
  }

  //Enqueue
  public boolean enqueue(Order order){
    // if (isFull()) {
    //         return false;
    //     }
    //     rearIndex++;
    //     orders[rearIndex] = order;
    //     size++;
    //     return true;
    if(!isFull()){
      rearIndex = (rearIndex + 1) % orders.length;
      orders[rearIndex] = order;
      size++;
      return true;
    }
    return false;
  }

  //Dequeue
  public Order dequeue(){
    // if (isEmpty()) {
    //         return null;
    //     }
    //     Order dequeuedOrder = orders[frontIndex];
    //     for (int i = 0; i < size - 1; i++) {
    //         orders[i] = orders[i + 1];
    //     }
    //     rearIndex--;
    //     size--;
    //     return dequeuedOrder;
    if(!isEmpty()){
      Order dequeuedOrder = orders[frontIndex];
      frontIndex = (frontIndex + 1) % orders.length;
      size--;
      return dequeuedOrder;
    }
    return null;
  }

  //Check available size of array
  public boolean isFull(){
    if(size == orders.length)
      return true;
    return false;
  }

  //Check empty queue
  public boolean isEmpty(){
    if(size == 0)
      return true;
    return false;
  }

  public int getSize(){
        return size;
    }
  
}
