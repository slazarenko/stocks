package com.demo.service;

import com.demo.dto.StockDto;
import com.demo.entity.StockEntity;
import com.demo.mapper.StockMapper;
import com.demo.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StockService {
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    //private static final

    public StockService(StockRepository stockRepository,
                        StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    public List<StockDto> getStockChangeHistory(long stockId) {
        return stockRepository.findAllById(stockId).stream()
                .map(stockMapper::mapToDto)
                .sorted(Comparator.comparing(StockDto::getLastUpdate, Comparator.nullsFirst(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    public List<StockDto> getAllStocks() {
        return stockRepository.findAllStocks().stream()
                .map(stockMapper::mapToDto)
                .sorted(Comparator.comparing(StockDto::getLastUpdate, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    public StockDto getLastStock(long id) {
        return stockMapper.mapToDto(stockRepository.getLastStock(id));
    }


    /**
     * Update stock or creates if doesnt exist
     *
     * @return true when updated or new created, false when there was no price change for existing.
     */
    public boolean updateStock(long stockId, String name, BigDecimal price) {
        StockEntity latestStock = stockRepository.getLastStock(stockId);
        boolean exists = latestStock != null;
        if (exists && price.compareTo(latestStock.getCurrentPrice()) == 0 && name.equals(latestStock.getName())) {
            return false;
        }
        StockEntity newStockEntry = new StockEntity(stockId, name, price);
        stockRepository.save(newStockEntry);

        if (exists) {
            latestStock.setLastUpdate(LocalDateTime.now());
            stockRepository.save(latestStock);
        }
        return true;
    }

    /**
     * @return identifier for just created Stock
     */

    public long createNewStock(String name, BigDecimal price) {
        StockEntity newStockEntity = new StockEntity(name, price);
        return stockRepository.save(newStockEntity).getId();
    }
}
