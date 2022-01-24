package com.example.sample.resources;

import com.example.sample.consumer.controller.StockController;
import com.example.sample.models.UserOrders;
import com.example.sample.models.UserStockBalance;
import com.example.sample.models.Users;
import com.example.sample.repository.UserOrdersRepository;
import com.example.sample.repository.UsersRepository;
import com.example.sample.serviceOrder.UserOrderDTO;
import com.example.sample.serviceOrder.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserOrdersResources {

    @Autowired
    UserOrdersRepository userOrdersRepository;

    @GetMapping("/orders")
    public List<UserOrders> listaPedidos(){
        return userOrdersRepository.findAll();
    }

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserStockBalance userStockBalance;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    StockController stockController;


    public int updateDollarBalance() {
        return 1;
    }

    public int updateStockBalance() {
        return 1;
    }

    public boolean checkDollarBalance(double price, double volume, double balance) {
        if ((price * volume ) <= balance){
            balance = balance - (price * volume);
            return true;
        } else if ((price * volume) >= balance){
            System.out.println("Saldo insuficiente");
            return false;
        } else {
            System.out.println("Operação inválida");
            return false;
        }
    };


    @PostMapping("/newOrder")
    public ResponseEntity<List<UserOrders>> salvar(@RequestBody UserOrderDTO dto) {
        Users users = usersRepository.findById(dto.getId_user()).orElseThrow();
        UserOrders userOrders = userOrderService.salvar(dto.transformaParaObjeto(users));

        List<UserOrders> userOrders1 = userOrdersRepository.findByStockAndTypeOrderAndIdUser(dto.getId_stock(),
                dto.getType(), dto.getId_user());

        for (UserOrders order : userOrders1){
            System.out.println(order.getVolume());
            //compara o volume da ordem do tipo compra com o volume da ordem do tipo compra
            if ((order.getVolume() >= dto.getVolume()) && (dto.getType() == 1) && (order.getStatus() == 1) ) {
                // se o valor da ordem do tipo compra for maior ou igual
                if (dto.getPrice() >= order.getPrice()) {
                    var volume = order.getRemaining_volume() - dto.getRemaining_volume();
                    if (volume == 0) {
                        userOrdersRepository.findbyIdOrderandStatus(order.getId());
                    }
                   userOrdersRepository.findByIdOrder(volume, order.getId());
                } else {
                    System.out.println("nenhuma ordem compativel no momento!");
                }
            } else if ((order.getVolume() <= dto.getVolume()) && (dto.getType() == 2 ) && (order.getStatus() == 1)) {
                if (dto.getPrice() <= order.getPrice()) {
                    var volume = order.getRemaining_volume() - dto.getRemaining_volume();
                    if (volume == 0) {
                        userOrdersRepository.findbyIdOrderandStatus(order.getId());
                    }
                    userOrdersRepository.findByIdOrder(volume, order.getId());
                    } else {
                        System.out.println("nenhuma ordem compativel no momento!");
                }
            } else {
                System.out.println("Nenhuma ordem aberta");
            }
        }

        return new ResponseEntity<>(userOrders1, HttpStatus.CREATED);

    }

}