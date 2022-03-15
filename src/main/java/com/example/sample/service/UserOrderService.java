package com.example.sample.service;


import com.example.sample.consumer.dto.StocksDto;
import com.example.sample.consumer.service.StockService;
import com.example.sample.dto.UserOrderDTO;
import com.example.sample.handleerror.NotFoundException;
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


    public UserOrders updateStatus(Long id, Integer status) throws NotFoundException {
        Optional<UserOrders> userOrders = userOrdersRepository.findById(id);
        if (userOrders.isPresent()){
            userOrders.get().setStatus(status);
            userOrdersRepository.save(userOrders.get());
            return userOrders.get();
        } else {
            throw new NotFoundException("ORDER_NOT_FOUND");
        }
    }


    public void checkRemainingVolume (Long idBuy, Long idSell, Long volumeBuy, Long volumeSell) {
        UserOrders userOrderBuy = userOrdersRepository.findById(idBuy).orElseThrow();
        UserOrders userOrderSell = userOrdersRepository.findById(idSell).orElseThrow();
        var remainingVolumeBuy = userOrderBuy.getRemainingVolume();
        var remainingVolumeSell = userOrderSell.getRemainingVolume();
        if (volumeBuy > volumeSell){
            remainingVolumeBuy = remainingVolumeBuy - volumeSell;
            remainingVolumeSell = remainingVolumeSell - volumeSell;
            userOrderBuy.setRemainingVolume(remainingVolumeBuy);
            userOrderSell.setRemainingVolume(remainingVolumeSell);
            if (remainingVolumeSell == 0){
                userOrderSell.setStatus(0);
            }
            if (remainingVolumeBuy == 0){
                userOrderBuy.setStatus(0);
            }
            userOrdersRepository.save(userOrderBuy);
            userOrdersRepository.save(userOrderSell);
        }
        if (volumeSell > volumeBuy){
            remainingVolumeSell = remainingVolumeSell - volumeBuy;
            remainingVolumeBuy = remainingVolumeBuy - volumeBuy;
            userOrderBuy.setRemainingVolume(remainingVolumeBuy);
            userOrderSell.setRemainingVolume(remainingVolumeSell);
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

    public void updateStockPrice(Long idStock, String token){
        Double bidMin = userOrdersRepository.findByIdStockMinPriceBid(idStock);
        Double bidMax = userOrdersRepository.findByIdStockMaxPriceBid(idStock);
        Double askMin = userOrdersRepository.findByIdStockMinPriceAsk(idStock);
        Double askMax = userOrdersRepository.findByIdStockMaxPriceAsk(idStock);

        StocksDto stocksDto = new StocksDto(idStock, bidMin, bidMax, askMin, askMax);

        stockService.updateStockbyPrice(stocksDto, token);

    }
    public void checkReturnBallance(Long idUser, Long volumeBuy, Double priceBuy, Double priceSell){
        Users users = usersRepository.findById(idUser).orElseThrow();
        var dollarBalance = users.getDollarBalance();
        if ((priceBuy - priceSell) > 0){
            var returnValue = (priceBuy - priceSell) * volumeBuy;
            users.setDollarBalance((dollarBalance + returnValue));
            usersRepository.save(users);
        }
    }

    public void updateDollarBalance(Double price, Long volume, Long id, int type, Double finalBalance) {
        if (type == 1) {
            finalBalance = finalBalance - (price * volume);
            usersRepository.findbyIdSetDollarBalance(id, finalBalance);
        } else if (type == 2) {
            finalBalance = finalBalance + (price * volume);
            usersRepository.findbyIdSetDollarBalance(id, finalBalance);
        }
    }

    public void updateStockBalance(Users userId, Long stockId, String stockSymbol, String stockName, Long volumeBuy, Long volumeSell, int type) {
        if (type == 2){
            if (volumeSell > volumeBuy){
                UserStockBalance userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(userId, stockId)).orElseThrow();
                var volume = userStockBalance.getVolume() - volumeBuy;
                userStockBalance.setVolume(volume);
                userStockBalanceRepository.save(userStockBalance);
            } else {
                UserStockBalance userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(userId, stockId)).orElseThrow();
                var volume = userStockBalance.getVolume() - volumeSell;
                userStockBalance.setVolume(volume);
                userStockBalanceRepository.save(userStockBalance);
            }
        }
        if (type == 1){
            if (volumeBuy > volumeSell){
                var balance = volumeBuy - volumeSell;
                Optional<UserStockBalance> userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(userId, stockId));
                if(userStockBalance.isPresent()){
                    balance = userStockBalance.get().getVolume() + balance;
                    userStockBalance.get().setVolume(balance);
                    userStockBalanceRepository.save(userStockBalance.get());
                } else {
                    userStockBalanceRepository.save(new UserStockBalance(new UserStockBalanceId(userId, stockId), stockSymbol, stockName, balance));
                }
            } else {
                int initializer = 0;
                var balance = Long.valueOf(initializer);
                UserStockBalance userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(userId, stockId)).orElse(
                        userStockBalanceRepository.save(new UserStockBalance(new UserStockBalanceId(userId, stockId), stockSymbol, stockName, balance)));
                balance = volumeBuy + userStockBalance.getVolume();
                userStockBalance.setVolume(balance);
                userStockBalanceRepository.save(userStockBalance);
            }

        }
    }


    public UserOrderDTO saveBuy(UserOrderDTO userOrderDTO, String token){
        Users users = usersRepository.findById(userOrderDTO.getIdUser()).orElseThrow();
        var totalAmount = userOrderDTO.getPrice() * userOrderDTO.getVolume();
        if (totalAmount <= users.getDollarBalance()) {
            UserOrders orderBuy = userOrdersRepository.save(userOrderDTO.transformaParaObjeto(users));

            // retem o valor do usuario mesmo antes da ordem fechar
            var balance = users.getDollarBalance() - totalAmount;
            users.setDollarBalance(balance);
            usersRepository.save(users);

            // atualiza o bid min/bid max
            updateStockPrice(userOrderDTO.getIdStock(), token);

            // busca uma ordem equivalente
            List<UserOrders> userOrders1 = userOrdersRepository.findByStockAndTypeOrderAndIdUser(userOrderDTO.getIdStock(),
                    userOrderDTO.getType(), userOrderDTO.getIdUser());

            for (UserOrders orderSell : userOrders1) {

                //compara o volume da ordem do tipo compra com o volume da ordem do tipo compra
                // se o valor da ordem do tipo compra for maior ou igual
                if ((userOrderDTO.getPrice() >= orderSell.getPrice())) {
                    checkRemainingVolume(orderBuy.getId(), orderSell.getId(), orderBuy.getVolume(), orderSell.getVolume());
                    //pega o saldo
                    double finalBalanceOrder = orderSell.getUsers().getDollarBalance();
                    //atualiza o dollar balance de quem vendeu
                    updateDollarBalance(orderSell.getPrice(), orderSell.getVolume(), orderSell.getUsers().getId(), orderSell.getType(), finalBalanceOrder);
                    //atualiza o stock balance de quem comprou
                    updateStockBalance(users, orderBuy.getIdStock(), orderBuy.getStockSymbol(), orderBuy.getStockName(), orderBuy.getVolume(), orderSell.getVolume(), orderBuy.getType());
                }

            }
            return userOrderDTO;
        }
        return userOrderDTO;
    }

    public UserOrderDTO saveSell(UserOrderDTO userOrderDTO, String token){
        Users users = usersRepository.findById(userOrderDTO.getIdUser()).orElseThrow();
        UserStockBalance userStockBalance = userStockBalanceRepository.findById(new UserStockBalanceId(users, userOrderDTO.getIdStock())).orElseThrow();
        if (userOrderDTO.getVolume() <= userStockBalance.getVolume()) {

            // salva a ordem
            UserOrders orderSell = userOrdersRepository.save(userOrderDTO.transformaParaObjeto(users));

            // retem o dinheiro
            var volume = userStockBalance.getVolume() - userOrderDTO.getVolume();
            userStockBalance.setVolume(volume);
            userStockBalanceRepository.save(userStockBalance);

            // atualiza o bid ask/ask max
            updateStockPrice(userOrderDTO.getIdStock(), token);

            // encontra a ordem compativel
            List<UserOrders> userOrders1 = userOrdersRepository.findByStockAndTypeOrderAndIdUser(userOrderDTO.getIdStock(),
                    userOrderDTO.getType(), userOrderDTO.getIdUser());

            for (UserOrders orderBuy : userOrders1) {
                if (orderSell.getPrice() <= orderBuy.getPrice()) {
                    checkRemainingVolume(orderBuy.getId(), orderSell.getId(), orderBuy.getVolume(), orderSell.getVolume());
                    double finalBalance1 = orderSell.getUsers().getDollarBalance();
                    updateDollarBalance(userOrderDTO.getPrice(), userOrderDTO.getVolume(), userOrderDTO.getIdUser(), userOrderDTO.getType(), finalBalance1);
                    updateStockBalance(orderBuy.getUsers(), orderBuy.getIdStock(), orderBuy.getStockSymbol(), orderBuy.getStockName(), orderBuy.getVolume(), orderSell.getVolume(), orderBuy.getType());
                }
            }
            return userOrderDTO;
        }
        return userOrderDTO;
    }

    public UserOrderDTO salvar(UserOrderDTO userOrderDTO, String token) {
        if (userOrderDTO.getType() == 1) {
            saveBuy(userOrderDTO, token);
        } else if (userOrderDTO.getType() == 2) {
            saveSell(userOrderDTO, token);
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