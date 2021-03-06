package com.example.sample.repository;

import com.example.sample.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Modifying
    @Query(value = "UPDATE users set dollar_balance = :dollar_balance where id = :id", nativeQuery = true)
    Integer findbyIdSetDollarBalance(@Param("id") Long id,
                                     @Param("dollar_balance") Double dollarBalance);

    @Query(value = "SELECT * FROM users where username = :username", nativeQuery = true)
    Optional<Users> findByUsername(@Param("username") String username);

}