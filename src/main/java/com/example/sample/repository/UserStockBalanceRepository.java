package com.example.sample.repository;

import com.example.sample.models.UserStockBalance;
import com.example.sample.models.UserStockBalanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UserStockBalanceRepository extends JpaRepository <UserStockBalance, UserStockBalanceId>{


    // se existirem na tabela, fazer o update na tabela
    /*@Modifying
    @Query(value = "UPDATE  user_stock_balances set volume = :volume where id = :id_user and id_stock = :id_stock", nativeQuery = true)
    Integer findByIdUpdateBalance(@Param("id_user"), Long id_user,
                                    @Param("volume") Long volume);
*/


}
