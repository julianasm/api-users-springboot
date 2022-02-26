package com.example.sample.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateOrderDto {

    Long id;


    public UpdateOrderDto(Long id){
        this.id = id;
    }

    public UpdateOrderDto(){}


}





