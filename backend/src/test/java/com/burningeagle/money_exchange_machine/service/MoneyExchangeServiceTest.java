package com.burningeagle.money_exchange_machine.service;

import com.burningeagle.money_exchange_machine.config.CoinInventoryConfig;
import com.burningeagle.money_exchange_machine.model.CoinInventory;
import com.burningeagle.money_exchange_machine.model.ExchangeTransaction;
import com.burningeagle.money_exchange_machine.repository.CoinInventoryRepository;
import com.burningeagle.money_exchange_machine.repository.ExchangeTransactionRepository;
import com.burningeagle.money_exchange_machine.service.impl.MoneyExchangeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MoneyExchangeServiceTest {

    @InjectMocks
    private MoneyExchangeServiceImpl exchangeService;

    @Mock
    private ExchangeTransactionRepository transactionRepository;

    @Mock
    private CoinInventoryRepository inventoryRepository;

    @Mock
    private CoinInventoryConfig coinInventoryConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExchangeMoney_MinCoins_Success() {
        Map<Integer, Integer> initialCoins = new HashMap<>();
        initialCoins.put(25, 10);
        initialCoins.put(10, 10);
        initialCoins.put(5, 10);
        initialCoins.put(1, 10);
        CoinInventory inventory = new CoinInventory(initialCoins);

        when(inventoryRepository.findAll()).thenReturn(java.util.List.of(inventory));

        ExchangeTransaction transaction = exchangeService.exchangeMoney(1, false);

        assertNotNull(transaction);
        assertEquals(1, transaction.getBillAmount());
        assertFalse(transaction.getExchangedCoins().isEmpty());
        verify(transactionRepository, times(1)).save(any(ExchangeTransaction.class));
    }

    @Test
    void testExchangeMoney_NotEnoughCoins_Failure() {
        Map<Integer, Integer> initialCoins = new HashMap<>();
        initialCoins.put(25, 1);
        CoinInventory inventory = new CoinInventory(initialCoins);

        when(inventoryRepository.findAll()).thenReturn(java.util.List.of(inventory));

        Exception exception = assertThrows(RuntimeException.class, () ->
                exchangeService.exchangeMoney(5, false));

        assertEquals("Not enough coins for exchange", exception.getMessage());
    }

    @Test
    void testIncreaseCoinInventory_Success() {
        Map<Integer, Integer> initialCoins = new HashMap<>();
        initialCoins.put(25, 10);
        initialCoins.put(10, 10);
        CoinInventory inventory = new CoinInventory(initialCoins);

        when(inventoryRepository.findAll()).thenReturn(java.util.List.of(inventory));

        Map<Integer, Integer> additionalCoins = new HashMap<>();
        additionalCoins.put(25, 5);
        additionalCoins.put(10, 5);

        exchangeService.increaseCoinInventory(additionalCoins);

        assertEquals(15, inventory.getCoins().get(25));
        assertEquals(15, inventory.getCoins().get(10));
        verify(inventoryRepository, times(1)).save(any(CoinInventory.class));
    }

}
