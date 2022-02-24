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
import com.example.sample.serviceOrder.UpdateOrderDto;
import com.example.sample.serviceOrder.UserOrderDTO;
import com.example.sample.serviceOrder.UserOrderService;
import com.example.sample.serviceUsers.UsersDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class UserOrdersResources {

    private final StockService stockService;

    private final UsersRepository usersRepository;

    private final UserStockBalanceRepository userStockBalanceRepository;

    private final UserOrderService userOrderService;

    private final StockController stockController;

    private final UserOrdersRepository userOrdersRepository;

    @CrossOrigin
    @GetMapping("/orders")
    public List<UserOrders> listaPedidos(){
        return userOrdersRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/orders/{id}")
    public List<UserOrders> listaOrdemPorId(@PathVariable Long id){
        return userOrdersRepository.findByIdUser(id);
    }

    @CrossOrigin
    @PostMapping("/order-update/{status}")
    public ResponseEntity<UserOrders> updateOrder(@RequestBody UpdateOrderDto dto, @PathVariable("status") Integer status, @RequestHeader("Authorization") String token) throws Exception {
        try {
            System.out.println("chegou aqui");
            return ResponseEntity.ok().body(userOrderService.updateStatus(dto.getId(), status));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    public void returnDollarBalance(Double price, Long volume, Long id, Double old_balance, Double match_cost) {
        var order_cost = price * volume;
        var return_value = order_cost - match_cost;
        if (return_value > 0){
            Users users = usersRepository.findById(id).orElseThrow();
            users.setDollar_balance((old_balance + return_value));
        }
    }

    public void returnStockBalance(Long id, Long id_stock, Double old_balance, Double match_cost, Double order_cost ) {
        var return_value = order_cost - match_cost;

    }

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
            if (volume_sell > volume_buy){;
                UserStockBalance userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(user_id, stock_id)).orElseThrow();
                var volume = userStockBalance.getVolume() - volume_buy;
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
            System.out.println("bateu aqui 1");
            return true;
        } else if ((typeOrder == 1) && (typeDto == 2) && (volumeOrder <= volumeDto)) {
            var volumeBuy = remainingOrder - volumeOrder;
            var volumeSell = remainingDto - volumeOrder;
            userOrdersRepository.findByIdOrder(volumeSell, dtoId);
            userOrdersRepository.findByIdOrder(volumeBuy, orderId);
            if (volumeSell == 0){
                userOrdersRepository.findbyIdStatus(dtoId);
            }
            if (volumeBuy == 0){
                userOrdersRepository.findbyIdStatus(orderId);
            }
            System.out.println("bateu aqui 2");
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
            System.out.println("bateu aqui 3");
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
            System.out.println("bateu aqui 4");
            return true;
        } else {
            return false;
        }

    };


    public void updateStockPrice(Long id_stock, String token){
            Double bid_min = userOrdersRepository.findByIdStockMinPriceBid(id_stock);
            Double bid_max = userOrdersRepository.findByIdStockMaxPriceBid(id_stock);
            Double ask_min = userOrdersRepository.findByIdStockMinPriceAsk(id_stock);
            Double ask_max = userOrdersRepository.findByIdStockMaxPriceAsk(id_stock);

        StocksDto stocksDto = new StocksDto(id_stock, bid_min, bid_max, ask_min, ask_max);

        stockService.UpdateStockbyPrice(stocksDto, token);

        }

    @CrossOrigin
    @PostMapping("/new_order")
    public ResponseEntity<List<UserOrders>> salvar(@RequestBody UserOrderDTO dto, @RequestHeader("Authorization") String token) {
        Users users = usersRepository.findById(dto.getId_user()).orElseThrow();
        if (dto.getType() == 1) {
            var total_amount = dto.getPrice() * dto.getVolume();
            if (total_amount <= users.getDollar_balance()) {
                UserOrders userOrders = userOrderService.salvar(dto.transformaParaObjeto(users));

                // retem o valor do usuario mesmo antes da ordem fechar
                var balance = users.getDollar_balance() - total_amount;
                System.out.println(balance);
                users.setDollar_balance(balance);
                usersRepository.save(users);

                // atualiza o bid min/bid max
                updateStockPrice(dto.getId_stock(), token);

                // busca uma ordem equivalente
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
                            //updateDollarBalance(order.getPrice(), dto.getVolume(), dto.getId_user(), dto.getType(), final_balance1);
                            updateDollarBalance(order.getPrice(), order.getVolume(), order.getUsers().getId(), order.getType(), final_balance2);
                            updateStockBalance(users, dto.getId_stock(), dto.getStock_symbol(), dto.getStock_name(), dto.getVolume(), order.getVolume(), dto.getType());
                            //updateStockBalance(order.getUsers(), order.getId_stock(), order.getStock_symbol(), order.getStock_name(), dto.getVolume(), order.getVolume(), order.getType());
                        } else {
                            System.out.println("nenhuma ordem compativel no momento!");
                        }
                    }

                }
                return new ResponseEntity(userOrders, HttpStatus.CREATED);
            }
        } else if (dto.getType() == 2) {
            UserStockBalance userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(users, dto.getId_stock())).orElseThrow();
                if (dto.getVolume() <= userStockBalance.getVolume()) {

                    // salva a ordem
                    UserOrders userOrders = userOrderService.salvar(dto.transformaParaObjeto(users));

                    // retem o dinheiro
                    var volume = userStockBalance.getVolume() - dto.getVolume();
                    userStockBalance.setVolume(volume);
                    userStockBalanceRepository.save(userStockBalance);

                    // atualiza o bid ask/ask max
                    updateStockPrice(dto.getId_stock(), token);

                    // encontra a ordem compativel
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
                                //updateDollarBalance(dto.getPrice(), order.getVolume(), order.getUsers().getId(), order.getType(), final_balance2);
                                //updateStockBalance(users, dto.getId_stock(), dto.getStock_symbol(), dto.getStock_name(), order.getVolume(), dto.getVolume(), dto.getType());
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