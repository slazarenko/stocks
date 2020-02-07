package com.demo.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockRequestDto implements Serializable {
    private String name;
    private BigDecimal currentPrice;

    public StockRequestDto() {
    }

    public StockRequestDto(String name, BigDecimal price) {
        this.name = name;
        this.currentPrice = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

}
