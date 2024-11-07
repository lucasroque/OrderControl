package com.orders.orders.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.orders.orders.dto.OrderDTO;
import com.orders.orders.dto.ProductDTO;
import com.orders.orders.entity.Order;
import com.orders.orders.entity.Product;
import com.orders.orders.repository.OrderRepository;

public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testServiceSaveOrder() {
        ProductDTO productOne = new ProductDTO();
        productOne.setName("Mão Robótica");
        productOne.setValue(999.0);

        ProductDTO productTwo = new ProductDTO();
        productTwo.setName("Droid");
        productTwo.setValue(2999.0);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDate(LocalDate.now());
        orderDTO.setClient("Luke Skywalker");
        orderDTO.setProducts(Arrays.asList(productOne, productTwo));

        Order order = new Order();
        order.setId(1L);
        order.setClient("Luke Skywalker");
        order.setDate(LocalDate.now());
        order.setOrderValue(3998.0);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order response = orderService.saveOrder(orderDTO);

        assertNotNull(response);
        assertEquals("Luke Skywalker", response.getClient());
        assertEquals(3998.0, response.getOrderValue());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testServiceSaveExistingOrder() {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Aço Beskar");
        productDTO.setValue(100.0);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setClient("Mando");
        orderDTO.setDate(LocalDate.now());
        orderDTO.setProducts(Arrays.asList(productDTO));

        Product product = new Product();
        product.setName("Aço Beskar");
        product.setValue(100.0);

        Order existingOrder = new Order();
        existingOrder.setClient("Mando");
        existingOrder.setDate(LocalDate.now());
        existingOrder.setOrderValue(100.0);
        existingOrder.setProducts(Arrays.asList(product));

        when(orderRepository.findByClientAndDate("Mando", LocalDate.now())).thenReturn(List.of(existingOrder));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.saveOrder(orderDTO);
        });

        assertEquals("O cliente já possui um pedido com os mesmos produtos para esse dia.", exception.getMessage());
    }
}
