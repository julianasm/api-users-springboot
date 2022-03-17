package com.example.sample.service;

import com.example.sample.dto.UserStockBalanceDTO;
import com.example.sample.models.UserStockBalance;
import com.example.sample.models.UserStockBalanceId;
import com.example.sample.models.Users;
import com.example.sample.repository.UserStockBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStockBalanceService {

    private final UserStockBalanceRepository userStockBalanceRepository;


    public UserStockBalance salvar(UserStockBalance userStockBalance) {
        return userStockBalanceRepository.save(userStockBalance);
    }

    public Optional<UserStockBalance> findById(Users users, Long idStock){
        return userStockBalanceRepository.findById(new UserStockBalanceId(users, idStock));
    }

    public List<UserStockBalanceDTO> listByIdUser(Long id){
        return userStockBalanceRepository.findAllByIdUser(id).stream().map(UserStockBalanceDTO::new).toList();
    }


}
