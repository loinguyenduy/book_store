import java.util.List;

public class OrderManager {
  public static void sortOrderHistory(List<Order> orderHistory){
    int n = orderHistory.size();

    for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (orderHistory.get(j).getOrderDateTime().isAfter(orderHistory.get(minIndex).getOrderDateTime())) {
                    minIndex = j;
                }
            }

            Order temp = orderHistory.get(minIndex);
            orderHistory.set(minIndex, orderHistory.get(i));
            orderHistory.set(i, temp);
          }
  }

  public static Order searchOrder(String orderId, List<Order> orderHistory){
    for (Order order : orderHistory) {
        if (order.getOrderId().equals(orderId)) {
            return order;
        }
    }
    return null;
  }
}
