package br.com.fiap.checkpoint2.controller;

import br.com.fiap.checkpoint2.model.OrderModel;
import br.com.fiap.checkpoint2.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Object> crateOrder(@Valid @RequestBody OrderModel order) {
        try{
            OrderModel orderModel = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderModel);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public List<OrderModel> ReadOrders() {
        return orderService.readAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrder(@PathVariable Long id) {
        try{
            OrderModel orderModel = orderService.readOrderById(id);
            return ResponseEntity.status(HttpStatus.OK).body(orderModel);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderModel order) {
        try{
            OrderModel orderModel = orderService.updateOrder(id, order);
            return ResponseEntity.status(HttpStatus.OK).body(orderModel);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id) {
        try{
            orderService.deleteOrderById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Pedido deletado com sucesso");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
