package com.example.ecom.service;

import com.example.ecom.customException.NotFoundException;
import com.example.ecom.dtos.order.OrderRequest;
import com.example.ecom.dtos.order.OrderResponse;
import com.example.ecom.entity.Order;
import com.example.ecom.mapper.OrderMapper;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    public OrderResponse createOrder(OrderRequest order) {
        return orderMapper.toResponse(orderRepository.save(orderMapper.toEntity(order)));
    }

    public OrderResponse findOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new NotFoundException('O'));
        return orderMapper.toResponse(order);
    }

    public List<OrderResponse> getAllOrders(){
        return  (orderRepository.findAll().stream()
                .map(orderMapper::toResponse).toList());
    }

    public String deleteOrderById(UUID orderId){
        orderRepository.findById(orderId).orElseThrow(()-> new NotFoundException('O'));
        orderRepository.deleteOrderProductsByOrderId(orderId);
        //orderRepository.deleteById(orderId);
        return "Order Deleted";

    }

    public List<?> getOrderByUserId(UUID userId) {
        userRepository.findById(userId).orElseThrow(()-> new NotFoundException('U'));
        String message = "This user have no orders";
        List<String> errorList = new ArrayList<>();
        if (orderRepository.findByUserId(userId).isEmpty()) {
            errorList.add(message);
            return errorList;
        }
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toResponse).toList();
    }
}
