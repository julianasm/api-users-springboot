package com.example.sample.dto;


import com.example.sample.models.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SaveUserDto {

    private String username;

    private String password;

    private Double dollarBalance;

    private Boolean enabled = true;


    public Users transformaParaObjeto(){
        return new Users(
                username,
                password,
                dollarBalance,
                enabled
        );
    }
}
