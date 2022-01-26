package com.example.sample.repository;

import com.example.sample.models.UserOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Transactional
@Repository
public interface UserOrdersRepository extends JpaRepository<UserOrders, Long> {
   /* @Query(value = "SELECT * FROM user_orders WHERE type = ?1 and id_stock = ?2 and status = 1", nativeQuery = true)
    List<UserOrders> findByTypeStock(Integer type, Long id_stock);*/

    @Query(value = "SELECT * FROM user_orders uo where uo.id_stock = :id_stock  and uo.type <> :type and uo.id_user <> :id_user", nativeQuery = true)
    List<UserOrders> findByStockAndTypeOrderAndIdUser(@Param("id_stock") Long id_stock,
                                                      @Param("type") Integer type,
                                                      @Param("id_user") Long id_user);

    @Modifying
    @Query(value = "UPDATE  user_orders set remaining_volume = :remaining_volume where id = :id", nativeQuery = true)
    Integer findByIdOrder(@Param("remaining_volume") Long remaining_volume,
                          @Param("id") Long id);


}


