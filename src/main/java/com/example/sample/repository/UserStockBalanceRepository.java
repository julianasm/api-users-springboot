package com.example.sample.repository;

import com.example.sample.models.UserStockBalance;
import com.example.sample.models.UserStockBalanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UserStockBalanceRepository extends JpaRepository <UserStockBalance, UserStockBalanceId>{

    // verifica se ja existe no findbyId


    // inserir na tabela se for a primeira compra do usuario
    @Modifying
    @Query(value = "INSERT INTO user_stock_balances id =: id stock_symbol = :stock_symbol stock_name =: stock_name volume =: volume  ", nativeQuery = true)
    Integer findByIdCreateBalance(@Param("id") UserStockBalanceId id,
                          @Param("stock_symbol") String stock_symbol,
                          @Param("stock_name") String stock_name,
                          @Param("volume") Double volume);

    // se existirem na tabela, fazer o update na tabela
    @Modifying
    @Query(value = "UPDATE  user_stock_balances set volume = :volume where id = :id", nativeQuery = true)
    Integer findByIdUpdateBalance(@Param("id") UserStockBalanceId id,
                                    @Param("volume") Long volume);



}
