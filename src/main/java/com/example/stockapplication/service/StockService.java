package com.example.stockapplication.service;

import com.example.stockapplication.domain.StockDTO;
import com.example.stockapplication.entity.BoughtUserStock;
import com.example.stockapplication.entity.Stock;
import com.example.stockapplication.entity.User;
import com.example.stockapplication.entity.UserStock;
import com.example.stockapplication.exception.StockNotFoundException;
import com.example.stockapplication.exception.UserNotFoundException;
import com.example.stockapplication.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
    private final StockRepository stockRepository;

    private final RuleRepository ruleRepository;

    private final UserRepository userRepository;

    private final UserStockRepository userStockRepository;

    private BoughtUserStockRepository boughtUserStockRepository;

    public List<StockDTO> getStocksByDate(LocalDate date) {
        List<StockDTO> stocks = stockRepository
                .findByCreatedDate(date.atStartOfDay())
                .stream()
                .map(Stock::stockDTO)
                .collect(Collectors.toList());
        stocks.forEach(stock -> stock.setLabel(labelingStock(stock)));
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

    public void addStock(StockDTO stockDTO) {
        Stock stock = new Stock(stockDTO);

        try {
            stockRepository.save(stock);
        } catch (Exception e) {
            log.error("SERVER ERROR");
        }
    }

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
        }
    }

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
        }
    }
}
