package com.example.sample.serviceOrder;


import com.example.sample.models.UserOrders;
import com.example.sample.repository.UserOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOrderService {

    private final UserOrdersRepository userOrdersRepository;

    @Autowired
    public UserOrderService(UserOrdersRepository userOrdersRepository) {
        this.userOrdersRepository = userOrdersRepository;
    }


    public UserOrders salvar(UserOrders userOrders) {
        System.out.println(userOrders.getUsers());
        return userOrdersRepository.save(userOrders);
    }
}