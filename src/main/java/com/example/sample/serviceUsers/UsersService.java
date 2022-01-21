package com.example.sample.serviceUsers;

import com.example.sample.models.UserOrders;
import com.example.sample.models.Users;
import com.example.sample.repository.UserOrdersRepository;
import com.example.sample.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public Users salvar(Users users) {
        return usersRepository.save(users);
    }

}
