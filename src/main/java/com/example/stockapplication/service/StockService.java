package com.example.stockapplication.service;

import com.example.stockapplication.Constants.Constants;
import com.example.stockapplication.domain.StockDTO;
import com.example.stockapplication.entity.*;
import com.example.stockapplication.exception.AccessRepositoryException;
import com.example.stockapplication.exception.StockAlreadyExistsException;
import com.example.stockapplication.exception.StockNotFoundException;
import com.example.stockapplication.exception.UserNotFoundException;
import com.example.stockapplication.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void addStockTicket(String stockName) {
        Optional<StockTicket> stockTicketOptional = stockTicketRepository.findByStockName(stockName);
        stockTicketOptional.orElseThrow(RuntimeException::new);

        StockTicket stockTicket = new StockTicket();
        stockTicket.setStockName(stockName);
        stockTicket.setCreatedDate(LocalDateTime.now());
        stockTicket.setLastUpdated(LocalDateTime.now());

        try {
            stockTicketRepository.save(stockTicket);
        } catch (Exception e) {
            log.error("ERROR SERVICE");
            throw new AccessRepositoryException("GG_ERROR DO NOT SAVE!");
        }
    }



    public List<StockDTO> getStocksByDate(LocalDate date) {
        List<StockDTO> stocks = stockRepository
                .findByCreatedDate(date.atStartOfDay())
                .stream()
                .map(Stock::stockDTO)
                .collect(Collectors.toList());
        stocks.forEach(stock -> stock.setLabel(labelingStock(stock)));
        return stocks;
    }

    public List<StockDTO> searchStock(String stockSymbol, LocalDate startDate, LocalDate endDate) {
        List<StockDTO> stocks = stockRepository
                .findByStockSymbolAndTimeBetween(stockSymbol, startDate.atStartOfDay(), endDate.atStartOfDay())
                .stream()
                .map(Stock::stockDTO)
                .collect(Collectors.toList());

        return stocks;
    }

    public List<StockDTO> getAllStocks() {
        List<StockDTO> stocks = stockRepository
                                .findAll()
                                .stream()
                                .map(Stock::stockDTO)
                                .collect(Collectors.toList());
        stocks.forEach(stock -> stock.setLabel(labelingStock(stock)));
        return stocks;
    };


    @Transactional
    public void addStock(StockDTO stockDTO) {
        Stock stock = new Stock(stockDTO);
        int count = stockRepository.countByStockSymbolAndCreatedDate(stock.getStockSymbol(), LocalDate.now());
        if (count > 0) {
            throw new StockAlreadyExistsException("Stock already exists in system!");
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

        BoughtUserStock boughtUserStock = new BoughtUserStock();
        boughtUserStock.setUser(userOptional.get());
        boughtUserStock.setStock(stockOptional.get());
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

        BoughtUserStock boughtUserStock = new BoughtUserStock();
        boughtUserStock.setUser(userOptional.get());
        boughtUserStock.setStock(stockOptional.get());
        try {
            boughtUserStockRepository.save(boughtUserStock);
        } catch (Exception e) {
            log.error("SERVER ERROR");
            throw new AccessRepositoryException("HAVE ERROR");
        }
    }

    public List<StockDTO> getBoughtStocks(int userId) {
        return boughtUserStockRepository
                .findByUser_Id(userId)
                .stream()
                .map(boughtUserStock -> boughtUserStock.getStock().stockDTO())
                .collect(Collectors.toList());
    }

    public List<StockDTO> getFavoriteStocks(int userId) {
        return userStockRepository
                .findByUser_Id(userId)
                .stream()
                .map(userStock -> userStock.getStock().stockDTO())
                .collect(Collectors.toList());
    }

    @Transactional
    public void addFavoriteStock(int userId, int stockId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.orElseThrow(() -> new UserNotFoundException(userId));

        Optional<Stock> stockOptional = stockRepository.findById(stockId);
        stockOptional.orElseThrow(() -> new StockNotFoundException(stockId));

        UserStock userStock = new UserStock();
        userStock.setUser(userOptional.get());
        userStock.setStock(stockOptional.get());
        try {
            userStockRepository.save(userStock);
        } catch (Exception e) {
            log.error("SERVER ERROR");
            throw new AccessRepositoryException("HAVE SERVER");
        }
    }

    public String judgingStock(String stockSymbol, float currentStockPrice) {
        LocalDate startDate = LocalDate.now().minusDays(Constants.ADAY);
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
        float netProfit = stock.getNetProfit(); //lợi nhuận ròng
        float stockPrice = stock.getStockPrice(); // giá cổ phiếu
        float revenue = stock.getRevenue(); // tổng doanh thu
        float currentAssets = stock.getCurrentAssets(); // tài sản hiện tại
        float totalLiabilities = stock.getTotalLiabilities(); // tổng nợ phải trả
        float currentDebt = stock.getCurrentDebt(); // nợ hiện tại
        float totalAssets = stock.getTotalAssets(); // tổng tài sản
        float eps = stock.getEps(); //loi nhuan tren mot chung khoan

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

        String label = ruleRepository.findByIndexs(profitability, activity, liquidity, debt, market).get(0);
        return label;
    }
}
