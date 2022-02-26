package com.example.sample.service;

import com.example.sample.dto.SaveUserDto;
import com.example.sample.dto.UsersDTO;
import com.example.sample.models.Users;
import com.example.sample.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public SaveUserDto salvar(SaveUserDto saveUserDto) {
        Users users = saveUserDto.transformaParaObjeto();
        usersRepository.save(users);
        return saveUserDto;
    }

    public UsersDTO findByUsername(String username) throws Exception{
       Optional<Users> users =  usersRepository.findByUsername(username);
       if (users.isPresent()){;
            return new UsersDTO(users.get().getId());
       } else {
           throw new Exception("NOT_FOUND");
       }
    }

}
