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

    public void updateStockBalance(Users user_id, Long stock_id, String stock_symbol, String stock_name, Long volume_buy, Long volume_sell, int type) {
        if (type == 2){
            if (volume_sell > volume_buy){
                var balance = volume_sell - volume_buy;
                UserStockBalance userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(user_id, stock_id)).orElseThrow();
                var volume = userStockBalance.getVolume() - balance;
                userStockBalance.setVolume(volume);
                userStockBalanceRepository.save(userStockBalance);
            } else if (volume_sell <= volume_buy){
                UserStockBalance userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(user_id, stock_id)).orElseThrow();
                var volume = userStockBalance.getVolume() - volume_sell;
                userStockBalance.setVolume(volume);
                userStockBalanceRepository.save(userStockBalance);
            }
        }
        if (type == 1){
            if (volume_buy > volume_sell){
                var balance = volume_buy - volume_sell;
                userStockBalanceRepository.findById(new UserStockBalanceId(user_id, stock_id)).orElse(
                        userStockBalanceRepository.save(new UserStockBalance(new UserStockBalanceId(user_id, stock_id), stock_symbol, stock_name, balance)));
            } else if (volume_buy <= volume_sell){
                int initializer = 0;
                var balance = Long.valueOf(initializer);
                UserStockBalance userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(user_id, stock_id)).orElse(
                        userStockBalanceRepository.save(new UserStockBalance(new UserStockBalanceId(user_id, stock_id), stock_symbol, stock_name, balance)));
                balance = volume_buy + userStockBalance.getVolume();
                userStockBalance.setVolume(balance);
                userStockBalanceRepository.save(userStockBalance);
            }

        }
    }


    public boolean checkRemainingVolume(Long volumeOrder, Long volumeDto, Long remainingOrder, Long remainingDto, int typeOrder, int typeDto, Long orderId, Long dtoId){
        if ((typeOrder == 1) && (typeDto == 2) && (volumeOrder >= volumeDto)) {
            var volumeBuy = remainingOrder - remainingDto;
            var volumeSell = remainingDto - volumeDto;
            userOrdersRepository.findByIdOrder(volumeSell, orderId);
            userOrdersRepository.findByIdOrder(volumeBuy, dtoId);
            if (volumeSell == 0){
                userOrdersRepository.findbyIdStatus(orderId);
            }
            if (volumeBuy == 0){
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
            }
            if (volumeBuy == 0){
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
            }
            if (volumeBuy == 0){
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
            }
            if (volumeBuy == 0){
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


    @PostMapping("/new_order")
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
                            updateDollarBalance(order.getPrice(), dto.getVolume(), dto.getId_user(), dto.getType(), final_balance1);
                            updateDollarBalance(order.getPrice(), order.getVolume(), order.getUsers().getId(), order.getType(), final_balance2);
                            updateStockBalance(users, dto.getId_stock(), dto.getStock_symbol(), dto.getStock_name(), dto.getVolume(), order.getVolume(), dto.getType());
                            updateStockBalance(order.getUsers(), order.getId_stock(), order.getStock_symbol(), order.getStock_name(), dto.getVolume(), order.getVolume(), order.getType());
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
                                updateDollarBalance(dto.getPrice(), order.getVolume(), order.getUsers().getId(), order.getType(), final_balance2);
                                updateStockBalance(users, dto.getId_stock(), dto.getStock_symbol(), dto.getStock_name(), order.getVolume(), dto.getVolume(), dto.getType());
                                updateStockBalance(order.getUsers(), order.getId_stock(), order.getStock_symbol(), order.getStock_name(), order.getVolume(), dto.getVolume(), order.getType());
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