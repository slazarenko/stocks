package com.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockDto extends StockRequestDto {
    private long id;
    private LocalDateTime lastUpdate;

    public StockDto() {
    }

    public StockDto(long stockId, String name, BigDecimal price) {
        super(name, price);
        this.id = stockId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
