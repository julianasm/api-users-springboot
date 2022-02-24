package com.example.sample.serviceOrder;


import com.example.sample.models.UserOrders;
import com.example.sample.repository.UserOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderService {

    private final UserOrdersRepository userOrdersRepository;


    public UserOrders salvar(UserOrders userOrders) {
        System.out.println(userOrders.getUsers());
        return userOrdersRepository.save(userOrders);
    }

    public UserOrders updateStatus(Long id, Integer status) throws Exception {
        Optional<UserOrders> userOrders = userOrdersRepository.findById(id);
        if (userOrders.isPresent()){
            userOrders.get().setStatus(status);
            userOrdersRepository.save(userOrders.get());
            return userOrders.get();
        } else {
            System.out.println("erro");
            throw new Exception("ORDER_NOT_FOUND");
        }
    }
}