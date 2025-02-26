package com.burningeagle.money_exchange_machine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "coin_inventory")
public class CoinInventory {

   @Id
   private String id;
   private Map<Integer, Integer> coins;

    public CoinInventory(Map<Integer,Integer> coins){
        this.coins = coins;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<Integer, Integer> getCoins() {
        return coins;
    }

    public void setCoins(Map<Integer, Integer> coins) {
        this.coins = coins;
    }
}
