package com.btk.controller;

import com.btk.dto.response.GetProductDescriptionsFromProductServiceResponseDto;
import com.btk.entity.Order;
import com.btk.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static com.btk.constant.ApiUrls.*;


@RestController
@RequestMapping(ORDER)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(CREATE_ORDER+"/{balanceId}/{token}")
    public ResponseEntity<String> createOrder(@PathVariable String balanceId, @PathVariable String token){
        return ResponseEntity.ok(orderService.createOrder(balanceId,token));
    }
    @GetMapping(SHOW_HISTORY_OF_ORDERS+"/{token}")
    public ResponseEntity<List<GetProductDescriptionsFromProductServiceResponseDto>> showHistoryOfOrders(@PathVariable String token){
        return ResponseEntity.ok(orderService.showHistoryOfOrders(token));
    }
    @GetMapping(SHOW_HISTORY_OF_ORDERS_ACCORDING_TO_DATE+"/{token}")
    public ResponseEntity <List<GetProductDescriptionsFromProductServiceResponseDto>> filterHistoryOfOrdersWithDate(@PathVariable String token , @RequestParam String date1 , @RequestParam String date2) throws ParseException {
        return ResponseEntity.ok(orderService.filterHistoryOfOrdersWithDate(token, date1, date2));
    }
}
