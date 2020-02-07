package com.demo.mapper;

import com.demo.dto.StockDto;
import com.demo.entity.StockEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {
    StockDto mapToDto(StockEntity entity);
}
