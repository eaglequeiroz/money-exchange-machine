package com.burningeagle.money_exchange_machine.service.impl;

import com.burningeagle.money_exchange_machine.config.CoinInventoryConfig;
import com.burningeagle.money_exchange_machine.exception.InventoryNotFoundException;
import com.burningeagle.money_exchange_machine.exception.NotEnoughCoinException;
import com.burningeagle.money_exchange_machine.model.CoinInventory;
import com.burningeagle.money_exchange_machine.model.ExchangeTransaction;
import com.burningeagle.money_exchange_machine.repository.CoinInventoryRepository;
import com.burningeagle.money_exchange_machine.repository.ExchangeTransactionRepository;
import com.burningeagle.money_exchange_machine.service.MoneyExchangeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MoneyExchangeServiceImpl implements MoneyExchangeService {

    private final ExchangeTransactionRepository transactionRepository;
    private final CoinInventoryRepository inventoryRepository;
    private final CoinInventoryConfig coinInventoryConfig;

    public MoneyExchangeServiceImpl(ExchangeTransactionRepository transactionRepository,
                                    CoinInventoryRepository inventoryRepository,
                                    CoinInventoryConfig coinInventoryConfig) {
        this.transactionRepository = transactionRepository;
        this.inventoryRepository = inventoryRepository;
        this.coinInventoryConfig = coinInventoryConfig;
    }

    @Bean
    public CommandLineRunner initializeInventory() {
        return args -> {
            if (inventoryRepository.count() == 0) {
                inventoryRepository.save(new CoinInventory(coinInventoryConfig.getCoins()));
            }
        };
    }

    @Override
    public ExchangeTransaction exchangeMoney(int bill, boolean maxCoins) {
        int amountInCents = bill * 100;
        Map<Integer, Integer> exchangedCoins = new HashMap<>();

        Optional<CoinInventory> inventoryOpt = inventoryRepository.findAll().stream().findFirst();
        if (inventoryOpt.isEmpty()) {
            try {
                throw new InventoryNotFoundException("No inventory found");
            } catch (InventoryNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        CoinInventory inventory = inventoryOpt.get();
        Map<Integer, Integer> coinInventory = inventory.getCoins();

        if (!canExchange(amountInCents, coinInventory)) {
            try {
                throw new NotEnoughCoinException("Not enough coins for exchange");
            } catch (NotEnoughCoinException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        int[] coinValues = maxCoins ? new int[]{1, 5, 10, 25} : new int[]{25, 10, 5, 1};
        for (int coin : coinValues) {
            amountInCents = distributeCoins(amountInCents, coin, exchangedCoins, coinInventory);
        }

        ExchangeTransaction transaction = new ExchangeTransaction(bill, exchangedCoins, maxCoins ? "MAX" : "MIN");
        transactionRepository.save(transaction);
        inventoryRepository.save(inventory);
        return transaction;
    }

    @Override
    public void increaseCoinInventory(Map<Integer, Integer> additionalCoins) {
        Optional<CoinInventory> inventoryOpt = inventoryRepository.findAll().stream().findFirst();
        if (inventoryOpt.isEmpty()) {
            try {
                throw new InventoryNotFoundException("No inventory found");
            } catch (InventoryNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        CoinInventory inventory = inventoryOpt.get();
        Map<Integer, Integer> coinInventory = inventory.getCoins();

        additionalCoins.forEach((coin, count) -> coinInventory.put(coin, coinInventory.getOrDefault(coin, 0) + count));
        inventoryRepository.save(inventory);
    }

    private boolean canExchange(int amount, Map<Integer, Integer> coinInventory) {
        int sumAvailable = coinInventory.entrySet().stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue())
                .sum();
        return sumAvailable >= amount;
    }

    private int distributeCoins(int amount, int coinValue, Map<Integer, Integer> exchangedCoins, Map<Integer, Integer> coinInventory) {
        int numCoins = Math.min(amount / coinValue, coinInventory.getOrDefault(coinValue, 0));
        if (numCoins > 0) {
            exchangedCoins.put(coinValue, numCoins);
            coinInventory.put(coinValue, coinInventory.get(coinValue) - numCoins);
        }
        return amount - (numCoins * coinValue);
    }
}
