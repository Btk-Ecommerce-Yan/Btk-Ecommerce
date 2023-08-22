package com.btk.service;

import com.btk.dto.request.AddProductToBasketRequestDto;
import com.btk.dto.request.TotalPriceRequestDto;
import com.btk.entity.Basket;
import com.btk.entity.enums.ERole;
import com.btk.exception.ErrorType;
import com.btk.exception.SaleManagerException;
import com.btk.manager.IProductManager;
import com.btk.manager.IUserManager;
import com.btk.mapper.IBasketMapper;
import com.btk.repository.IBasketRepository;
import com.btk.utility.JwtTokenProvider;
import com.btk.utility.ServiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BasketService extends ServiceManager<Basket, String> {
    private final IBasketRepository basketRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserManager userManager;
    private final IProductManager productManager;

    public BasketService(IBasketRepository basketRepository, JwtTokenProvider jwtTokenProvider, IUserManager userManager, IProductManager productManager) {
        super(basketRepository);
        this.basketRepository = basketRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userManager = userManager;
        this.productManager = productManager;
    }

    public Boolean addProductToBasket(String token, AddProductToBasketRequestDto dto) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if (authId.isEmpty()) {
            throw new SaleManagerException(ErrorType.INVALID_TOKEN);
        }
        String user = userManager.findByAuthId(authId.get()).getBody();
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
    //@Cacheable(value = "findAll")
    public List<Basket> findAll(){
        return basketRepository.findAll();
    }
    public Double totalPriceInBasket(String token, TotalPriceRequestDto dto){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if (authId.isEmpty()) {
            throw new SaleManagerException(ErrorType.INVALID_TOKEN);
        }
        List<String> roles = jwtTokenProvider.getRoleFromToken(token);
        if (roles.contains(ERole.USER.toString())) {
            Basket basket=basketRepository.findOptionalByBasketId(dto.getBasketId()).get();
            List<String> productIds = basket.getProductIds();
            Double totalPrice =  productIds.stream()
                    .map(productId -> productManager.findPriceByProductId(productId).getBody())
                    .mapToDouble(Double::doubleValue)
                    .sum();
            return totalPrice;
        }
        return 0.0;
    }



}
