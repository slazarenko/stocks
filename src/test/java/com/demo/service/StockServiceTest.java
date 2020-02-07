package com.demo.service;

import com.demo.dto.StockDto;
import com.demo.entity.StockEntity;
import com.demo.mapper.StockMapper;
import com.demo.mapper.StockMapperImpl;
import com.demo.repository.StockRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {
    @Mock
    private StockRepository stockRepository;

    private StockService stockService;

    private StockMapper stockMapper = new StockMapperImpl();

    @Before
    public void beforeTest() {
        stockService = new StockService(stockRepository, stockMapper);
        //passing array inside array to allow "add"operation
        List<StockEntity> stockEntities = new ArrayList<>(Arrays.asList(
                createStockEntity(1, BigDecimal.ONE, LocalDateTime.of(2019, 1, 1, 1, 1)),
                createStockEntity(1, BigDecimal.TEN, null),
                createStockEntity(2, BigDecimal.TEN, LocalDateTime.of(2019, 1, 1, 1, 1)),
                createStockEntity(2, BigDecimal.ONE, null),
                createStockEntity(3, BigDecimal.ONE, null)
        ));
        doReturn(
                stockEntities.stream().filter(s -> s.getLastUpdate() == null).collect(Collectors.toList())
        ).when(stockRepository).findAllStocks();
        when(stockRepository.findAllById(anyLong()))
                .thenAnswer(i ->
                        stockEntities.stream()
                                .filter(s -> i.getArguments()[0].equals(s.getId()))
                                .collect(Collectors.toList())
                );
        when(stockRepository.getLastStock(anyLong()))
                .thenAnswer(i ->
                        stockEntities.stream()
                                .filter(s -> i.getArguments()[0].equals(s.getId()) && s.getLastUpdate() == null)
                                .findFirst().orElse(null)
                );
        when(stockRepository.save(any(StockEntity.class)))
                .thenAnswer(i -> {
                    stockEntities.add((StockEntity) i.getArguments()[0]);
                    return i.getArguments()[0];
                });
    }

    private StockEntity createStockEntity(long id, BigDecimal price, LocalDateTime lastUpdate) {
        StockEntity entity = new StockEntity();
        entity.setId(id);
        entity.setCurrentPrice(price);
        entity.setLastUpdate(lastUpdate);
        return entity;
    }


    @Test
    public void getStockChangeHistory() {
        assertEquals(2, stockService.getStockChangeHistory(1).size());
        assertTrue(stockService.getStockChangeHistory(0).isEmpty());
    }

    @Test
    public void getAllStocks() {
        assertEquals(3, stockService.getAllStocks().size());
    }

    @Test
    public void getLastStock() {
        StockDto stockDto = stockService.getLastStock(1);
        assertEquals(1, stockDto.getId());
        assertEquals(0, BigDecimal.TEN.compareTo(stockDto.getCurrentPrice()));
        assertNull(stockDto.getLastUpdate());
    }

    @Test
    public void updateStockPrice() {
        BigDecimal newPrice = new BigDecimal(3.57);
        stockService.updateStock(1, "stockName", newPrice);
        StockDto stockDto = stockService.getLastStock(1);
        assertEquals(1, stockDto.getId());
        assertEquals(0, newPrice.compareTo(stockDto.getCurrentPrice()));
        assertNull(stockDto.getLastUpdate());
    }
}
