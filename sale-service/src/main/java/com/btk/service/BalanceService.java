package com.btk.service;

import com.btk.dto.request.CreateBalanceRequestDto;
import com.btk.dto.response.AddBalanceResponseDto;
import com.btk.entity.Balance;
import com.btk.exception.ErrorType;
import com.btk.exception.SaleManagerException;
import com.btk.manager.IUserManager;
import com.btk.mapper.IBalanceMapper;
import com.btk.repository.IBalanceRepository;
import com.btk.utility.JwtTokenProvider;
import com.btk.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceService extends ServiceManager<Balance, String> {

    private final IBalanceRepository balanceRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserManager userManager;

    public BalanceService(IBalanceRepository balanceRepository, JwtTokenProvider jwtTokenProvider, IUserManager userManager) {
        super(balanceRepository);
        this.balanceRepository = balanceRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userManager = userManager;
    }

    public Boolean createBalance(Long authId) {
        String userId = userManager.findByAuthId(authId).getBody();
        Balance balance = Balance.builder().amountOfBalance(0d).userId(userId).build();
        save(balance);
        return true;

//        throw new SaleManagerException(ErrorType.BALANCE_EXIST_ERROR);
    }

    public AddBalanceResponseDto addBalance(Double amountOfBalance, String token) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        String userId = userManager.findByAuthId(authId.get()).getBody();
        System.out.println(userId);
        Optional<Balance> optionalBalance = balanceRepository.findByUserId(userId);
        System.out.println(optionalBalance);
        if (!optionalBalance.isEmpty()) {
            Double balance = optionalBalance.get().getAmountOfBalance();
            optionalBalance.get().setAmountOfBalance(amountOfBalance + balance);
            update(optionalBalance.get());
            return IBalanceMapper.INSTANCE.balanceToAddBalanceResponseDto(optionalBalance.get());
        } else {
            throw new RuntimeException("Böyle bir balance bulunamadı");
        }

    }

}
