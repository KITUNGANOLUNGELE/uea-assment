package cd.ac.uea.assessment.order;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Allows testing via browser (GET) - Optional helper for the assessment
    @GetMapping
    public String info() {
        return "Order API is ready. Use POST to place an order.";
    }

    @PostMapping
    public Order placeOrder(@RequestParam Long userId, @RequestParam Long productId) {
        return orderService.placeOrder(userId, productId);
    }
}