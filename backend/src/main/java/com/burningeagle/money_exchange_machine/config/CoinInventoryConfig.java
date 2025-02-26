package com.burningeagle.money_exchange_machine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "coin.inventory")
public class CoinInventoryConfig {

    private Map<Integer, Integer> coins;

    public Map<Integer, Integer> getCoins() {
        return coins;
    }

    public void setCoins(Map<Integer, Integer> coins) {
        this.coins = coins;
    }
}
