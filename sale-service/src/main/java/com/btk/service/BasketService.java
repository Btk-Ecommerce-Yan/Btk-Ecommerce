package com.btk.service;

import com.btk.dto.request.AddProductToBasketRequestDto;
import com.btk.dto.response.UserProfileResponseDto;
import com.btk.entity.Basket;
import com.btk.entity.enums.ERole;
import com.btk.entity.enums.EStatus;
import com.btk.exception.ErrorType;
import com.btk.exception.SaleManagerException;
import com.btk.manager.IUserManager;
import com.btk.mapper.IBasketMapper;
import com.btk.repository.IBasketRepository;
import com.btk.utility.JwtTokenProvider;
import com.btk.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService extends ServiceManager<Basket, String> {
    private final IBasketRepository basketRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserManager userManager;

    public BasketService(IBasketRepository basketRepository, JwtTokenProvider jwtTokenProvider, IUserManager userManager) {
        super(basketRepository);
        this.basketRepository = basketRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userManager = userManager;
    }

    public Boolean addProductToBasket(String token, AddProductToBasketRequestDto dto) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if (authId.isEmpty()) {
            throw new SaleManagerException(ErrorType.INVALID_TOKEN);
        }
        String user = userManager.findByAuthId(authId.get()).getBody();
        System.out.println(user);
        List<String> roles = jwtTokenProvider.getRoleFromToken(token);
        if (roles.contains(ERole.USER.toString())) {
            Basket basket = IBasketMapper.INSTANCE.addProductToBasketToBasket(dto);
            basket.setUserId(user);
            save(basket);
            return true;
        } else {
            throw new SaleManagerException(ErrorType.INVALID_ROLE);
        }
    }



}
