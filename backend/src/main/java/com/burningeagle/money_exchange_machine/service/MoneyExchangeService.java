package com.burningeagle.money_exchange_machine.service;

import com.burningeagle.money_exchange_machine.model.ExchangeTransaction;

import java.util.Map;

public interface MoneyExchangeService {

    ExchangeTransaction exchangeMoney(int bill, boolean maxCoins);
    void increaseCoinInventory(Map<Integer,Integer>additionalCoins);
}
