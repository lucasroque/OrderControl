package com.orders.orders.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.orders.dto.OrderDTO;
import com.orders.orders.dto.ProductDTO;
import com.orders.orders.entity.Order;
import com.orders.orders.entity.Product;
import com.orders.orders.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order saveOrder(OrderDTO orderDTO) {

        if (orderExists(orderDTO)) {
            throw new IllegalArgumentException("O cliente já possui um pedido com os mesmos produtos para esse dia.");
        }

        List<Product> products = orderDTO.getProducts().stream().map(productDTO -> {
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setValue(productDTO.getValue());

            return product;
        }).collect(Collectors.toList());

        Order order = new Order();
        order.setDate(orderDTO.getDate());
        order.setClient(orderDTO.getClient());
        order.setOrderValue(calculateOrderValue(orderDTO.getProducts()));
        order.setProducts(products);

        return orderRepository.save(order);
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    private boolean orderExists(OrderDTO orderDTO) {
        List<Order> orders = orderRepository.findByClientAndDate(orderDTO.getClient(), orderDTO.getDate());

        for (Order order : orders) {
            if (testSameProducts(orderDTO.getProducts(), order.getProducts())) {
                return true;
            }
        }

        return false;
    }

    private boolean testSameProducts(List<ProductDTO> productsDTO, List<Product> products) {
        if (productsDTO.size() != products.size()) {
            return false;
        }

        for (int i = 0; i < productsDTO.size(); i++) {
            ProductDTO productDTO = productsDTO.get(i);
            Product product = products.get(i);

            if (!Objects.equals(productDTO.getName(), product.getName())
                    || !Objects.equals(productDTO.getValue(), product.getValue())) {
                return false;
            }
        }

        return true;
    }

    private double calculateOrderValue(List<ProductDTO> products) {
        return products.stream()
                .mapToDouble(ProductDTO::getValue)
                .sum();
    }
}
