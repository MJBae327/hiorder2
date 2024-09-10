package hiorder.infra;

import hiorder.domain.*;

import java.util.List;
import javax.transaction.Transactional;

import hiorder.external.MenuDto;
import hiorder.external.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping("/orders")
@Transactional
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MenuService menuService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        MenuDto menu = menuService.getMenu(Long.valueOf(order.getMenuId()));
        order.setPrice(menu.getPrice() * order.getQuantity());
        order.setStatus("CREATED");
        order.setIsOrderable("YES");
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = (List<Order>) orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    BeanUtils.copyProperties(order, existingOrder, "id");
                    Order updatedOrder = orderRepository.save(existingOrder);
                    return ResponseEntity.ok(updatedOrder);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    orderRepository.delete(order);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
//>>> Clean Arch / Inbound Adaptor
