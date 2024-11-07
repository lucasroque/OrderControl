package com.orders.orders.controller;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.orders.orders.dto.OrderDTO;
import com.orders.orders.dto.ProductDTO;
import com.orders.orders.service.OrderService;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testControllerSaveOrder() throws Exception {

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

        mockMvc.perform(post("/api/order")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk());
    }
}
