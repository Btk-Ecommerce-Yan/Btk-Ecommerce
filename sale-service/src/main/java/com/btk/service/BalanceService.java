package com.btk.service;

import com.btk.entity.Balance;
import com.btk.entity.Basket;
import com.btk.repository.IBalanceRepository;
import com.btk.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class BalanceService extends ServiceManager<Balance,String> {

    private final IBalanceRepository balanceRepository;


    public BalanceService(IBalanceRepository balanceRepository) {
        super(balanceRepository);
        this.balanceRepository = balanceRepository;
    }
}
