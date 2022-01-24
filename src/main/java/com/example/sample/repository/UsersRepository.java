package com.example.sample.repository;

import com.example.sample.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Modifying
    @Query(value = "UPDATE users set dollar_balance = :dollar_ballance where id = :id", nativeQuery = true)
    Integer findbyIdSetDollarBalance(@Param("id") Long id,
                                     @Param("dollar_balance") Double dollar_balance);



}