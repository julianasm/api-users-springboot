package com.example.sample.serviceStockBalance;

import com.example.sample.models.UserStockBalance;
import com.example.sample.models.UserStockBalanceId;
import com.example.sample.models.Users;
import com.example.sample.repository.UserStockBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStockBalanceService {

    private final UserStockBalanceRepository userStockBalanceRepository;


    public UserStockBalance salvar(UserStockBalance userStockBalance) {
        return userStockBalanceRepository.save(userStockBalance);
    }

    public Optional<UserStockBalance> findById(Users users, Long id_stock){
        return userStockBalanceRepository.findById(new UserStockBalanceId(users, id_stock));
    }
}
