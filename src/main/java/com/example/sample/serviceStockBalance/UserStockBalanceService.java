package com.example.sample.serviceStockBalance;

import com.example.sample.models.UserOrders;
import com.example.sample.models.UserStockBalance;
import com.example.sample.repository.UserOrdersRepository;
import com.example.sample.repository.UserStockBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStockBalanceService {

    private final UserStockBalanceRepository userStockBalanceRepository;

    @Autowired
    public UserStockBalanceService(UserStockBalanceRepository userStockBalanceRepository) {
        this.userStockBalanceRepository = userStockBalanceRepository;
    }


    public UserStockBalance salvar(UserStockBalance userStockBalance) {
        return userStockBalanceRepository.save(userStockBalance);
    }
}
