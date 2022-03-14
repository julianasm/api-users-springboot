package com.example.sample.service;

import com.example.sample.dto.SaveUserDto;
import com.example.sample.dto.UsersDTO;
import com.example.sample.models.Users;
import com.example.sample.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;


    public SaveUserDto salvar(SaveUserDto saveUserDto) {
        Users users = saveUserDto.transformaParaObjeto();
        usersRepository.save(users);
        return saveUserDto;
    }

    public List<Users> listAll(){
        return usersRepository.findAll();
    }

    public Users findById(Long id) {
        Users users = usersRepository.findById(id).orElseThrow();
        return users;
    }


    public UsersDTO findByUsername(String username) throws Exception{
       Optional<Users> users =  usersRepository.findByUsername(username);
       if (users.isPresent()){;
            return new UsersDTO(users.get().getId());
       } else {
           throw new Exception("NOT_FOUND");
       }
    }

    public List<UsersDTO> listAllDto() {
        return usersRepository.findAll().stream().map((Users users) -> new UsersDTO(users.getId())).toList();
    }
}
