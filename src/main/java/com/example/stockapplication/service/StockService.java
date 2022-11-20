package com.example.stockapplication.service;

import com.example.stockapplication.Constants.Constants;
import com.example.stockapplication.domain.StockDTO;
import com.example.stockapplication.domain.StockTicketDTO;
import com.example.stockapplication.entity.*;
import com.example.stockapplication.exception.*;
import com.example.stockapplication.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
    private final StockRepository stockRepository;

    private final RuleRepository ruleRepository;

    private final UserRepository userRepository;

    private final UserStockRepository userStockRepository;

    private final BoughtUserStockRepository boughtUserStockRepository;

    private final StockTicketRepository stockTicketRepository;

    public List<String> getStockTickets() {
        return stockTicketRepository.findAllStockName();
    }

    @Transactional
    public void addStockTicket(StockTicketDTO stockTicketDTO) {
        String stockName = stockTicketDTO.getStockName();
        StockTicket stockTicketOptional = stockTicketRepository.findByStockName(stockName);
        if (stockTicketOptional != null) {
            log.error("ERROR SERVICE");
            throw new AccessRepositoryException("HAVE ERROR");
        }

        StockTicket stockTicket = new StockTicket(stockTicketDTO);
        try {
            stockTicketRepository.save(stockTicket);
        } catch (Exception e) {
            log.error("ERROR SERVICE");
            throw new AccessRepositoryException("GG_ERROR DO NOT SAVE!");
        }
    }

    public List<StockDTO> getStocksByDate(LocalDate date) {
        List<StockDTO> stocks = stockRepository.findByCreatedDate(date.atStartOfDay());
        stocks.forEach(stock -> stock.setLabel(labelingStock(stock)));

        return stocks;
    }

    public List<StockDTO> searchStock(String stockSymbol, LocalDate startDate, LocalDate endDate) {
        List<StockDTO> stocks = stockRepository
                .findByStockSymbolAndTimeBetween(stockSymbol, startDate.atStartOfDay(), endDate.atStartOfDay());

        return stocks;
    }

    public List<StockDTO> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        if (!CollectionUtils.isEmpty(stocks)) {
            return new ArrayList<>();
        }
        List<StockDTO> stockDTOS = stockRepository.findAll()
                                .stream()
                                .map(Stock::stockDTO)
                                .collect(Collectors.toList());
        stockDTOS.forEach(stockDTO -> stockDTO.setLabel(labelingStock(stockDTO)));

        return stockDTOS;
    };


    @Transactional
    public void addStock(StockDTO stockDTO) {
        Stock stock = new Stock(stockDTO);
        int count = stockRepository.countByStockSymbolAndCreatedDate(stock.getStockSymbol(), LocalDate.now().atStartOfDay());
        if (count > 0) {
            throw new StockAlreadyExistsException("Stock already exists in system by date!");
        }

        try {
            stockRepository.save(stock);
        } catch (Exception e) {
            log.error("SERVER ERROR");
            throw new AccessRepositoryException("HAVE ERROR");
        }
    }

    @Transactional
    public void updateStock(int stockId, StockDTO stockDTO) {
        Optional<Stock> stockOptional = stockRepository.findById(stockId);
        stockOptional.orElseThrow(() -> new StockNotFoundException(stockId));

        Stock stock = stockOptional.get();
        stock.setStockSymbol(stockDTO.getStockSymbol());
        stock.setNetProfit(stock.getNetProfit());
        stock.setStockPrice(stockDTO.getStockPrice());
        stock.setEps(stockDTO.getEps());
        stock.setRevenue(stockDTO.getRevenue());
        stock.setCurrentAssets(stock.getCurrentAssets());
        stock.setCurrentDebt(stock.getCurrentDebt());
        stock.setTotalAssets(stockDTO.getTotalAssets());
        stock.setTotalLiabilities(stockDTO.getTotalLiabilities());
        stock.setLastUpdated(LocalDateTime.now());

        try {
            stockRepository.save(stock);
        } catch (Exception e) {
            log.error("ERROR SERVICE");
            throw new AccessRepositoryException("HAVE ERROR");
        }
    }

    @Transactional
    public void processBuyStock(int userId, int stockId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.orElseThrow(() -> new UserNotFoundException(userId));

        Optional<Stock> stockOptional = stockRepository.findById(stockId);
        stockOptional.orElseThrow(() -> new StockNotFoundException(stockId));
        Stock stock = stockOptional.get();

        BoughtUserStock boughtUserStock = new BoughtUserStock(userOptional.get(), stock, stock.getStockPrice(), stock.getStockSymbol());
        try {
            boughtUserStockRepository.save(boughtUserStock);
        } catch (Exception e) {
            log.error("SERVER ERROR");
            throw new AccessRepositoryException("HAVE ERROR");
        }
    }

    @Transactional
    public void buyStock(String username, int stockId) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        userOptional.orElseThrow(() -> new UserNotFoundException(username));

        Optional<Stock> stockOptional = stockRepository.findById(stockId);
        stockOptional.orElseThrow(() -> new StockNotFoundException(stockId));
        Stock stock = stockOptional.get();

        BoughtUserStock boughtUserStock = new BoughtUserStock(userOptional.get(), stock, stock.getStockPrice(), stock.getStockSymbol());
        try {
            boughtUserStockRepository.save(boughtUserStock);
        } catch (Exception e) {
            log.error("SERVER ERROR");
            throw new AccessRepositoryException("HAVE ERROR");
        }
    }

    public List<StockDTO> getBoughtStocks(int userId) {
        List<BoughtUserStock> boughtUserStocks = boughtUserStockRepository.findByUser_Id(userId);
        if (CollectionUtils.isEmpty(boughtUserStocks)) {
            return new ArrayList<>();
        }

        return  boughtUserStocks
                .stream()
                .map(boughtUserStock -> boughtUserStock.getStock().stockDTO())
                .collect(Collectors.toList());
    }

    public List<StockDTO> getFavoriteStocks(int userId) {
        List<UserStock> userStocks = userStockRepository.findByUser_Id(userId);
        if (CollectionUtils.isEmpty(userStocks)) {
            return new ArrayList<>();
        }
        List<StockDTO> stockDTOS = userStocks
                .stream()
                .map(userStock -> userStock.getStock().stockDTO())
                .collect(Collectors.toList());
        stockDTOS.forEach(stockDTO -> stockDTO.setLabel(labelingStock(stockDTO)));

        return stockDTOS;
    }

    @Transactional
    public void addFavoriteStock(int userId, int stockId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.orElseThrow(() -> new UserNotFoundException(userId));

        Optional<Stock> stockOptional = stockRepository.findById(stockId);
        stockOptional.orElseThrow(() -> new StockNotFoundException(stockId));

        UserStock userStock = new UserStock(userOptional.get(), stockOptional.get());
        try {
            userStockRepository.save(userStock);
        } catch (Exception e) {
            log.error("SERVER ERROR");
            throw new AccessRepositoryException("HAVE SERVER");
        }
    }

    public String judgingStock(String stockSymbol, float currentStockPrice) {
        LocalDate startDate = LocalDate.now().minusDays(Constants.ONE);
        LocalDate endDate = LocalDate.now();
        float beforeStockPrice = stockRepository.findStockPriceByStockSymBolAndDate(stockSymbol, startDate, endDate).get(0);
        boolean isStockIncrease = currentStockPrice >= 1.2 * beforeStockPrice;
        boolean isStockDecrease = currentStockPrice <= 0.92 * beforeStockPrice;

        if (isStockIncrease || isStockDecrease) {
            return Constants.SELL;
        }

        return Constants.UNKNOWN;
    }


    public String labelingStock(StockDTO stock) {
        float netProfit = stock.getNetProfit();
        float stockPrice = stock.getStockPrice();
        float revenue = stock.getRevenue();
        float currentAssets = stock.getCurrentAssets();
        float totalLiabilities = stock.getTotalLiabilities();
        float currentDebt = stock.getCurrentDebt();
        float totalAssets = stock.getTotalAssets();
        float eps = stock.getEps();

        float returnOnAsset = netProfit / totalAssets;
        float totalAssetsRatio = revenue / totalAssets;
        float liquidityRatio = currentAssets / currentDebt;
        float debtRatio = totalLiabilities / totalAssets;
        float financialMarketPos = stockPrice / eps;

        boolean profitability = returnOnAsset >= 1;
        boolean activity = totalAssetsRatio >= 1;
        boolean liquidity = liquidityRatio >= 1;
        boolean debt = debtRatio >= 1;
        boolean market = financialMarketPos >= 1;

        return ruleRepository.findByIndexs(profitability, activity, liquidity, debt, market).get(0);
    }

    @Transactional
    public void deleteLikedStock(int userId, int stockId) {
        Optional<UserStock> userStockOptional = userStockRepository.findByUser_IdAndStock_Id(userId, stockId);
        if (userStockOptional.isEmpty()) {
            log.error("HAVE ERROR");
            throw new UserStockLikeNotFoundException("have not stock");
        }

        try {
            userStockRepository.delete(userStockOptional.get());
        } catch (Exception e) {
            throw new AccessRepositoryException("HAVE ERROR IN DATABASE");
        }
    }

    @Transactional
    public void deleteBoughtStock(int userId, int stockId) {
        Optional<BoughtUserStock> boughtUserStockOptional = boughtUserStockRepository.findByUser_IdAndStock_Id(userId, stockId);
        if (boughtUserStockOptional.isEmpty()) {
            log.error("HAVE ERROR");
            throw new UserStockLikeNotFoundException("have not stock");
        }

        try {
            boughtUserStockRepository.delete(boughtUserStockOptional.get());
        } catch (Exception e) {
            log.error("HAVE ERROR");
            throw new AccessRepositoryException("HAVE ERROR IN DATABASE");
        }
    }
}
