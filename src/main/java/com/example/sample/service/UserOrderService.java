package com.example.sample.service;


import com.example.sample.consumer.DTO.StocksDto;
import com.example.sample.consumer.service.StockService;
import com.example.sample.dto.UserOrderDTO;
import com.example.sample.models.UserOrders;
import com.example.sample.models.UserStockBalance;
import com.example.sample.models.UserStockBalanceId;
import com.example.sample.models.Users;
import com.example.sample.repository.UserOrdersRepository;
import com.example.sample.repository.UserStockBalanceRepository;
import com.example.sample.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderService {

    private final UserOrdersRepository userOrdersRepository;

    private final StockService stockService;

    private final UsersRepository usersRepository;

    private final UserStockBalanceRepository userStockBalanceRepository;

    public void returnDollarBalance(Double price, Long volume, Long id, Double old_balance, Double match_cost) {
        var order_cost = price * volume;
        var return_value = order_cost - match_cost;
        if (return_value > 0){
            Users users = usersRepository.findById(id).orElseThrow();
            users.setDollar_balance((old_balance + return_value));
        }
    }

    public void returnStockBalance(Users users, Long id_stock, Long old_balance, Long match_cost, Long order_cost ) {
        var return_value = order_cost - match_cost;
        if (return_value > 0){
            UserStockBalance userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(users, id_stock)).orElseThrow();
            var newStockVolume = userStockBalance.getVolume() + return_value;
            userStockBalance.setVolume(newStockVolume);
        }
    }


    public UserOrders updateStatus(Long id, Integer status) throws Exception {
        Optional<UserOrders> userOrders = userOrdersRepository.findById(id);
        if (userOrders.isPresent()){
            userOrders.get().setStatus(status);
            userOrdersRepository.save(userOrders.get());
            return userOrders.get();
        } else {
            System.out.println("erro");
            throw new Exception("ORDER_NOT_FOUND");
        }
    }


    public void checkRemainingVolume (Long idBuy, Long idSell, Long volumeBuy, Long volumeSell) {
        UserOrders userOrderBuy = userOrdersRepository.findById(idBuy).orElseThrow();
        UserOrders userOrderSell = userOrdersRepository.findById(idSell).orElseThrow();
        var remainingVolumeBuy = userOrderBuy.getRemaining_volume();
        var remainingVolumeSell = userOrderSell.getRemaining_volume();
        if (volumeBuy > volumeSell){
            remainingVolumeBuy = remainingVolumeBuy - volumeSell;
            remainingVolumeSell = remainingVolumeSell - volumeSell;
            userOrderBuy.setRemaining_volume(remainingVolumeBuy);
            userOrderSell.setRemaining_volume(remainingVolumeSell);
            if (remainingVolumeSell == 0){
                userOrderSell.setStatus(0);
            }
            if (remainingVolumeBuy == 0){
                userOrderBuy.setStatus(0);
            }
            System.out.println(remainingVolumeBuy);
            userOrdersRepository.save(userOrderBuy);
            userOrdersRepository.save(userOrderSell);
        }
        if (volumeSell > volumeBuy){
            remainingVolumeSell = remainingVolumeSell - volumeBuy;
            remainingVolumeBuy = remainingVolumeBuy - volumeBuy;
            userOrderBuy.setRemaining_volume(remainingVolumeBuy);
            userOrderSell.setRemaining_volume(remainingVolumeSell);
            if (remainingVolumeSell == 0){
                userOrderSell.setStatus(0);
            }
            if (remainingVolumeBuy == 0){
                userOrderBuy.setStatus(0);
            }
            userOrdersRepository.save(userOrderBuy);
            userOrdersRepository.save(userOrderSell);
        }
    }

    public void updateStockPrice(Long id_stock, String token){
        Double bid_min = userOrdersRepository.findByIdStockMinPriceBid(id_stock);
        Double bid_max = userOrdersRepository.findByIdStockMaxPriceBid(id_stock);
        Double ask_min = userOrdersRepository.findByIdStockMinPriceAsk(id_stock);
        Double ask_max = userOrdersRepository.findByIdStockMaxPriceAsk(id_stock);

        StocksDto stocksDto = new StocksDto(id_stock, bid_min, bid_max, ask_min, ask_max);

        stockService.UpdateStockbyPrice(stocksDto, token);

    }
    public void checkReturnBallance(Long idUser, Long volumeBuy, Double priceBuy, Double priceSell){
        Users users = usersRepository.findById(idUser).orElseThrow();
        var dollarBalance = users.getDollar_balance();
        if ((priceBuy - priceSell) > 0){
            var returnValue = (priceBuy - priceSell) * volumeBuy;
            users.setDollar_balance((dollarBalance + returnValue));
            usersRepository.save(users);
        }
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


    public UserOrderDTO salvar(UserOrderDTO userOrderDTO, String token) {
        Users users = usersRepository.findById(userOrderDTO.getId_user()).orElseThrow();
        if (userOrderDTO.getType() == 1) {
            var total_amount = userOrderDTO.getPrice() * userOrderDTO.getVolume();
            if (total_amount <= users.getDollar_balance()) {
                UserOrders orderBuy = userOrdersRepository.save(userOrderDTO.transformaParaObjeto(users));

                // retem o valor do usuario mesmo antes da ordem fechar
                var balance = users.getDollar_balance() - total_amount;
                System.out.println(balance);
                users.setDollar_balance(balance);
                usersRepository.save(users);

                // atualiza o bid min/bid max
                updateStockPrice(userOrderDTO.getId_stock(), token);

                // busca uma ordem equivalente
                List<UserOrders> userOrders1 = userOrdersRepository.findByStockAndTypeOrderAndIdUser(userOrderDTO.getId_stock(),
                        userOrderDTO.getType(), userOrderDTO.getId_user());

                for (UserOrders orderSell : userOrders1) {

                    //compara o volume da ordem do tipo compra com o volume da ordem do tipo compra
                    if (userOrderDTO.getType() == 1) {
                        // se o valor da ordem do tipo compra for maior ou igual
                        if ((userOrderDTO.getPrice() >= orderSell.getPrice())) {
                            checkRemainingVolume(orderBuy.getId(), orderSell.getId(), orderBuy.getVolume(), orderSell.getVolume());
                            //pega o saldo
                            double finalBalanceOrder = orderSell.getUsers().getDollar_balance();

                            // devolve os saldo restante ao comprador
                            checkReturnBallance(userOrderDTO.getId_user(), userOrderDTO.getVolume(), userOrderDTO.getPrice(), orderSell.getPrice());

                            //atualiza o dollar balance de quem vendeu
                            updateDollarBalance(orderSell.getPrice(), orderSell.getVolume(), orderSell.getUsers().getId(), orderSell.getType(), finalBalanceOrder);
                            //atualiza o stock balance de quem comprou
                            updateStockBalance(users, orderBuy.getId_stock(), orderBuy.getStock_symbol(), orderBuy.getStock_name(), orderBuy.getVolume(), orderSell.getVolume(), orderBuy.getType());
                        } else {
                            System.out.println("nenhuma ordem compativel no momento!");
                        }
                    }

                }
                return userOrderDTO;
            }
        } else if (userOrderDTO.getType() == 2) {
            UserStockBalance userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(users, userOrderDTO.getId_stock())).orElseThrow();
            if (userOrderDTO.getVolume() <= userStockBalance.getVolume()) {

                // salva a ordem
                UserOrders orderSell = userOrdersRepository.save(userOrderDTO.transformaParaObjeto(users));

                // retem o dinheiro
                var volume = userStockBalance.getVolume() - userOrderDTO.getVolume();
                userStockBalance.setVolume(volume);
                userStockBalanceRepository.save(userStockBalance);

                // atualiza o bid ask/ask max
                updateStockPrice(userOrderDTO.getId_stock(), token);

                // encontra a ordem compativel
                List<UserOrders> userOrders1 = userOrdersRepository.findByStockAndTypeOrderAndIdUser(userOrderDTO.getId_stock(),
                        userOrderDTO.getType(), userOrderDTO.getId_user());

                for (UserOrders orderBuy : userOrders1) {
                    System.out.println(orderBuy.getId());
                    if (orderSell.getPrice() <= orderBuy.getPrice()) {
                        checkRemainingVolume(orderBuy.getId(), orderSell.getId(), orderBuy.getVolume(), orderSell.getVolume());
                        double final_balance1 = orderSell.getUsers().getDollar_balance();
                        updateDollarBalance(userOrderDTO.getPrice(), userOrderDTO.getVolume(), userOrderDTO.getId_user(), userOrderDTO.getType(), final_balance1);
                        updateStockBalance(orderBuy.getUsers(), orderBuy.getId_stock(), orderBuy.getStock_symbol(), orderBuy.getStock_name(), orderBuy.getVolume(), orderSell.getVolume(), orderBuy.getType());
                    } else {
                        System.out.println("nenhuma ordem compativel no momento!");
                    }

                }
                return userOrderDTO;
            }
        } else {
            return userOrderDTO;
        }
        return userOrderDTO;
    }

    public Page<UserOrders> findOrdersPage(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userOrdersRepository.findAll(pageable);
    }

    public Page<UserOrders> findOrdersPageById(Long id, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userOrdersRepository.findByIdPageable(pageable, id);
    }
}