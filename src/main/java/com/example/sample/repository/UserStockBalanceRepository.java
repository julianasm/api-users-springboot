package com.example.sample.repository;

import com.example.sample.models.UserStockBalance;
import com.example.sample.models.UserStockBalanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface UserStockBalanceRepository extends JpaRepository <UserStockBalance, UserStockBalanceId>{
    UserStockBalance findAllById(UserStockBalanceId userStockBalanceId);

    @Query(value = "SELECT * from user_stock_balances where id_user = :id_user", nativeQuery = true)
    List<UserStockBalance> findAllByIdUser(@Param("id_user") Long idUser);


}
