package com.example.sample.service;

import com.example.sample.dto.SaveUserDto;
import com.example.sample.dto.UsersDTO;
import com.example.sample.handleError.NotFoundException;
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
        return usersRepository.findById(id).orElseThrow();
    }


    public UsersDTO findByUsername(String username) throws NotFoundException {
       Optional<Users> users =  usersRepository.findByUsername(username);
       if (users.isPresent()){
            return new UsersDTO(users.get().getId());
       } else {
           throw new NotFoundException("NOT_FOUND");
       }
    }

    public List<UsersDTO> listAllDto() {
        return usersRepository.findAll().stream().map((Users users) -> new UsersDTO(users.getId())).toList();
    }
}
