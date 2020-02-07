package com.demo.service;

import com.demo.dto.StockDto;
import com.demo.dto.StockRequestDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockRestController {
    private final StockService stockService;

    public StockRestController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * @return a list of stocks
     */
    @GetMapping
    public List<StockDto> getAllStocks() {
        return stockService.getAllStocks();
    }

    /**
     * @return one stock from the list
     */
    @GetMapping("{id}")
    public StockDto getStock(@PathVariable long id) {
        return stockService.getLastStock(id);
    }

    /**
     * @return the historical price changes of a stock
     */
    @GetMapping("{id}/history")
    public List<StockDto> getStockHistory(@PathVariable long id) {
        return stockService.getStockChangeHistory(id);
    }

    /**
     * updates the price of a single stock
     */
    @PutMapping("{id}")
    @ApiOperation("Updates existing stock or create if it doesnt exists")
    public ResponseEntity<String> updateStock(@PathVariable long id,
                                              @RequestBody StockRequestDto stockRequest) {
        if (stockService.updateStock(id, stockRequest.getName(), stockRequest.getCurrentPrice())) {
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not changed. Same price", HttpStatus.NOT_MODIFIED);
    }

    /**
     * creates a stock
     */
    @PostMapping
    @ApiOperation("Creates new Stock. If exists - ignores")
    public ResponseEntity<Long> creatStock(@RequestBody StockRequestDto stockRequestDto) {
        return new ResponseEntity<>(
                stockService.createNewStock(stockRequestDto.getName(), stockRequestDto.getCurrentPrice()),
                HttpStatus.CREATED
        );
    }


}
