package com.orders.orders.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {
    private LocalDate date;
    private String client;
    private List<ProductDTO> products;
}
