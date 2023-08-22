package com.btk.mapper;

import com.btk.dto.request.CreateOrderRequestDto;
import com.btk.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface IOrderMapper {
    IOrderMapper INSTANCE = Mappers.getMapper(IOrderMapper.class);

    Order createOrderRequestDtoToOrder(CreateOrderRequestDto dto);
}