package com.example.sample.repository;

import com.example.sample.models.UserOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface UserOrdersRepository extends JpaRepository<UserOrders, Long> {

    @Query(value = "SELECT * FROM user_orders uo where uo.id_stock = :id_stock  and uo.type <> :type and uo.id_user <> :id_user and status = 1 order by created_on", nativeQuery = true)
    List<UserOrders> findByStockAndTypeOrderAndIdUser(@Param("id_stock") Long idStock,
                                                      @Param("type") Integer type,
                                                      @Param("id_user") Long idUser);

    @Query(value = "SELECT * from user_orders uo where id_user = :id_user", nativeQuery = true)
    Page<UserOrders> findByIdPageable(Pageable pageable, @Param("id_user") Long id);

    @Modifying
    @Query(value = "UPDATE  user_orders set remaining_volume = :remaining_volume where id = :id", nativeQuery = true)
    Integer findByIdOrder(@Param("remaining_volume") Long remainingVolume,
                          @Param("id") Long id);


    @Modifying
    @Query(value = "update user_orders set status = 0 where id = :id", nativeQuery = true)
    Integer findbyIdStatus(@Param("id") Long id);

    @Query(value = "SELECT min(price) from user_orders uo where uo.id_stock = :id_stock and uo.type = 1 and uo.status = 1", nativeQuery = true)
    Double findByIdStockMinPriceBid(@Param("id_stock") Long idStock);

    @Query(value = "SELECT max(price) from user_orders uo where uo.id_stock = :id_stock and uo.type = 1 and uo.status = 1", nativeQuery = true)
    Double findByIdStockMaxPriceBid(@Param("id_stock") Long idStock);

    @Query(value = "SELECT min(price) from user_orders uo where uo.id_stock = :id_stock and uo.type = 2 and uo.status = 1", nativeQuery = true)
    Double findByIdStockMinPriceAsk(@Param("id_stock") Long idStock);

    @Query(value = "SELECT max(price) from user_orders uo where uo.id_stock = :id_stock and uo.type = 2 and uo.status = 1", nativeQuery = true)
    Double findByIdStockMaxPriceAsk(@Param("id_stock") Long idStock);

}




