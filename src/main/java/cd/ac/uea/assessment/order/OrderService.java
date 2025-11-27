package cd.ac.uea.assessment.order;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public OrderService(OrderRepository orderRepository, InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    public Order placeOrder(Long userId, Long productId) {
        bool inventoryClient.checkStock(productId);
        
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setStatus("PLACED");
        
        return orderRepository.save(order);
    }
}
