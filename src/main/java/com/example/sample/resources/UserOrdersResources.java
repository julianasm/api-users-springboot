package com.example.sample.resources;

import com.example.sample.consumer.DTO.StocksDto;
import com.example.sample.consumer.controller.StockController;
import com.example.sample.consumer.service.StockService;
import com.example.sample.models.UserOrders;
import com.example.sample.models.UserStockBalance;
import com.example.sample.models.UserStockBalanceId;
import com.example.sample.models.Users;

import com.example.sample.repository.UserOrdersRepository;
import com.example.sample.repository.UserStockBalanceRepository;
import com.example.sample.repository.UsersRepository;
import com.example.sample.serviceOrder.UserOrderDTO;
import com.example.sample.serviceOrder.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    StockService stockService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserStockBalanceRepository userStockBalanceRepository;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    StockController stockController;


    public double updateDollarBalance(Double price, Long volume, Long id, int type, Double final_balance) {
        if (type == 1) {
            final_balance = final_balance - (price * volume);
            usersRepository.findbyIdSetDollarBalance(id, final_balance);
        } else if (type == 2) {
            final_balance = final_balance + (price * volume);
            usersRepository.findbyIdSetDollarBalance(id, final_balance);
        }
        return final_balance;
    }

    public int updateStockBalance() {
        return 1;
    }


    public boolean checkRemainingVolume(Long volumeOrder, Long volumeDto, Long remainingOrder, Long remainingDto, int typeOrder, int typeDto, Long orderId, Long dtoId){
        if ((typeOrder == 1) && (typeDto == 2) && (volumeOrder >= volumeDto)) {
            var volumeBuy = remainingOrder - remainingDto;
            var volumeSell = remainingDto - volumeDto;
            userOrdersRepository.findByIdOrder(volumeSell, orderId);
            userOrdersRepository.findByIdOrder(volumeBuy, dtoId);
            if (volumeSell == 0){
                userOrdersRepository.findbyIdStatus(orderId);
            } else if (volumeBuy == 0){
                userOrdersRepository.findbyIdStatus(dtoId);
            }
            return true;
        } else if ((typeOrder == 1) && (typeDto == 2) && (volumeOrder <= volumeDto)) {
            var volumeBuy = remainingOrder - volumeOrder;
            var volumeSell = remainingDto - volumeOrder;
            userOrdersRepository.findByIdOrder(volumeSell, orderId);
            userOrdersRepository.findByIdOrder(volumeBuy, dtoId);
            if (volumeSell == 0){
                userOrdersRepository.findbyIdStatus(orderId);
            } else if (volumeBuy == 0){
                userOrdersRepository.findbyIdStatus(dtoId);
            }
            return true;
        } else if ((typeOrder == 2) && (typeDto == 1) && (volumeOrder <= volumeDto)) {
            var volumeBuy = remainingDto - remainingOrder;
            var volumeSell = remainingOrder - volumeOrder;
            userOrdersRepository.findByIdOrder(volumeSell, orderId);
            userOrdersRepository.findByIdOrder(volumeBuy, dtoId);
            if (volumeSell == 0){
                userOrdersRepository.findbyIdStatus(orderId);
            } else if (volumeBuy == 0){
                userOrdersRepository.findbyIdStatus(dtoId);
            }
            return true;
        }  else if ((typeOrder == 2) && (typeDto == 1) && (volumeOrder >= volumeDto)) {
            var volumeBuy = remainingDto - volumeDto;
            var volumeSell = remainingOrder - volumeDto;
            userOrdersRepository.findByIdOrder(volumeSell, orderId);
            userOrdersRepository.findByIdOrder(volumeBuy, dtoId);
            if (volumeSell == 0){
                userOrdersRepository.findbyIdStatus(orderId);
            } else if (volumeBuy == 0){
                userOrdersRepository.findbyIdStatus(dtoId);
            }
            return true;
        } else {
            return false;
        }

    };


    public void updateStockPrice(Long id_stock){
            Double bid_min_price = userOrdersRepository.findByIdStockMinPriceBid(id_stock);
            Double bid_max_price = userOrdersRepository.findByIdStockMaxPriceBid(id_stock);
            Double ask_min_price = userOrdersRepository.findByIdStockMinPriceAsk(id_stock);
            Double ask_max_price = userOrdersRepository.findByIdStockMaxPriceAsk(id_stock);

        StocksDto stocksDto = new StocksDto(id_stock, bid_min_price, bid_max_price, ask_min_price, ask_max_price);

        stockService.UpdateStockbyPrice(stocksDto);

        }



    @PostMapping("/newOrder")
    public ResponseEntity<List<UserOrders>> salvar(@RequestBody UserOrderDTO dto) {
        Users users = usersRepository.findById(dto.getId_user()).orElseThrow();
        System.out.println(dto.getType());
        if (dto.getType() == 1) {
            var total_amount = dto.getPrice() * dto.getVolume();
            if (total_amount <= users.getDollar_balance()) {
                UserOrders userOrders = userOrderService.salvar(dto.transformaParaObjeto(users));

                updateStockPrice(dto.getId_stock());

                List<UserOrders> userOrders1 = userOrdersRepository.findByStockAndTypeOrderAndIdUser(dto.getId_stock(),
                        dto.getType(), dto.getId_user());

                for (UserOrders order : userOrders1) {

                    //compara o volume da ordem do tipo compra com o volume da ordem do tipo compra
                    if ((order.getVolume() >= dto.getVolume()) && (dto.getType() == 1)) {
                        // se o valor da ordem do tipo compra for maior ou igual
                        if ((dto.getPrice() >= order.getPrice())) {
                            checkRemainingVolume(order.getVolume(), dto.getVolume(),
                                    order.getRemaining_volume(), dto.getRemaining_volume(),
                                    order.getType(), dto.getType(),
                                    order.getId(), userOrders.getId());
                            //pega o saldo
                            double final_balance1 = userOrders.getUsers().getDollar_balance();
                            double final_balance2 = order.getUsers().getDollar_balance();
                            updateDollarBalance(dto.getPrice(), dto.getVolume(), dto.getId_user(), dto.getType(), final_balance1);
                            updateDollarBalance(order.getPrice(), order.getVolume(), order.getUsers().getId(), order.getType(), final_balance2);
                        } else {
                            System.out.println("nenhuma ordem compativel no momento!");
                        }
                    }

                }
                return new ResponseEntity(userOrders, HttpStatus.CREATED);
            }
        } else if (dto.getType() == 2) {
            Optional<UserStockBalance> userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(users, dto.getId_stock()));
                if (dto.getVolume() <= userStockBalance.get().getVolume()) {
                    UserOrders userOrders = userOrderService.salvar(dto.transformaParaObjeto(users));
                    List<UserOrders> userOrders1 = userOrdersRepository.findByStockAndTypeOrderAndIdUser(dto.getId_stock(),
                            dto.getType(), dto.getId_user());

                    for (UserOrders order : userOrders1) {
                        if ((order.getVolume() <= dto.getVolume())) {
                            if (dto.getPrice() <= order.getPrice()) {
                                checkRemainingVolume(order.getVolume(), dto.getVolume(),
                                        order.getRemaining_volume(), dto.getRemaining_volume(),
                                        order.getType(), dto.getType(),
                                        order.getId(), userOrders.getId());
                                double final_balance1 = userOrders.getUsers().getDollar_balance();
                                double final_balance2 = order.getUsers().getDollar_balance();
                                updateDollarBalance(dto.getPrice(), dto.getVolume(), dto.getId_user(), dto.getType(), final_balance1);
                                updateDollarBalance(order.getPrice(), order.getVolume(), order.getUsers().getId(), order.getType(), final_balance2);
                            } else {
                                System.out.println("nenhuma ordem compativel no momento!");
                            }
                        } else {
                            System.out.println("Nenhuma ordem aberta");
                        }

                    }
                    return new ResponseEntity(userOrders, HttpStatus.CREATED);
                }
            } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.valueOf(205));
    }

}