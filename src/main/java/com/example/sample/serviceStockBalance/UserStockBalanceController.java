package com.example.sample.serviceStockBalance;

import com.example.sample.models.UserOrders;
import com.example.sample.models.UserStockBalance;
import com.example.sample.models.Users;
import com.example.sample.repository.UsersRepository;
import com.example.sample.serviceOrder.UserOrderDTO;
import com.example.sample.serviceOrder.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserStockBalanceController {

    private final UserStockBalanceService userStockBalanceService;

    @Autowired
    public UserStockBalanceController(UserStockBalanceService userStockBalanceService) {
        this.userStockBalanceService = userStockBalanceService;
    }

    @Autowired
    UsersRepository usersRepository;



}
