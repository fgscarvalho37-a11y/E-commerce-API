package com.felipe.ecommerce_api.service;

import com.felipe.ecommerce_api.dto.OrderDTO;
import com.felipe.ecommerce_api.model.Order;
import com.felipe.ecommerce_api.model.OrderItem;
import com.felipe.ecommerce_api.model.Product;
import com.felipe.ecommerce_api.repository.OrderRepository;
import com.felipe.ecommerce_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public OrderDTO create(OrderDTO dto) {
        Order order = new Order();
        order.setCustomerName(dto.getCustomerName());
        order.setCustomerEmail(dto.getCustomerEmail());

        BigDecimal total = BigDecimal.ZERO;

        for (OrderDTO.OrderItemRequest itemRequest : dto.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemRequest.getProductId()));

            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + product.getName());
            }

            // Dá baixa no estoque
            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            OrderItem item = new OrderItem(product, itemRequest.getQuantity());
            order.addItem(item);

            total = total.add(item.getSubtotal());
        }

        order.setTotalAmount(total);
        Order saved = orderRepository.save(order);

        return toDTO(saved);
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
        return toDTO(order);
    }

    @Transactional
    public OrderDTO updateStatus(Long id, Order.OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
        order.setStatus(status);
        return toDTO(orderRepository.save(order));
    }

    @Transactional
    public OrderDTO cancel(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));

        if (order.getStatus() == Order.OrderStatus.CANCELLED) {
            throw new RuntimeException("Pedido já está cancelado");
        }

        // Devolve o estoque
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        return toDTO(orderRepository.save(order));
    }

    private OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        return dto;
    }
}