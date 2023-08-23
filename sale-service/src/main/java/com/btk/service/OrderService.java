package com.btk.service;

import com.btk.dto.request.CreateOrderRequestDto;
import com.btk.entity.Balance;
import com.btk.entity.Basket;
import com.btk.entity.Order;
import com.btk.entity.enums.ERole;
import com.btk.entity.enums.EStatus;
import com.btk.exception.ErrorType;
import com.btk.exception.SaleManagerException;
import com.btk.manager.IUserManager;
import com.btk.mapper.IOrderMapper;
import com.btk.repository.IOrderRepository;
import com.btk.utility.CodeGenerator;
import com.btk.utility.JwtTokenProvider;
import com.btk.utility.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService extends ServiceManager<Order, String> {

    private final IOrderRepository orderRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BasketService basketService;
    private final BalanceService balanceService;
    private final IUserManager userManager;


    public OrderService(IOrderRepository orderRepository, JwtTokenProvider jwtTokenProvider, BasketService basketService, BalanceService balanceService, IUserManager userManager) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.basketService = basketService;
        this.balanceService = balanceService;
        this.userManager = userManager;
    }

    // TODO : PRODUCTLARIN TOPLAM FİYATLARI İÇİN METOT ÇAĞIRILACAK VE ONA GÖRE ONAYLANIP KONTROL EDİLECEK
    @Transactional
    public String createOrder(CreateOrderRequestDto dto, String token) {
        List<String> roles = jwtTokenProvider.getRoleFromToken(token);
        if (!roles.contains(ERole.USER.toString())) {
            throw new SaleManagerException(ErrorType.INVALID_ROLE);
        } else {
            Order order = IOrderMapper.INSTANCE.createOrderRequestDtoToOrder(dto);
            save(order);
            Optional<Basket> optionalBasket = basketService.findById(dto.getBasketId());
            optionalBasket.get().setStatus(EStatus.PASSIVE);
            basketService.update(optionalBasket.get());
            order.setOrderNumber(CodeGenerator.generateCode());
            Optional<Balance> balance = balanceService.findById(dto.getBalanceId());
            return order.getOrderNumber();
        }


    }
}
