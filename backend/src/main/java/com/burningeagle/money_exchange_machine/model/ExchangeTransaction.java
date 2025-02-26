package com.burningeagle.money_exchange_machine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "transactions")
public class ExchangeTransaction {

    @Id
    private String id;
    private int billAmount;
    private Map<Integer, Integer> exchangedCoins;
    private String exchangeType;

    public ExchangeTransaction(int billAmount, Map<Integer,Integer> exchangedCoins, String exchangeType){
        this.billAmount = billAmount;
        this.exchangedCoins = exchangedCoins;
        this.exchangeType = exchangeType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(int billAmount) {
        this.billAmount = billAmount;
    }

    public Map<Integer, Integer> getExchangedCoins() {
        return exchangedCoins;
    }

    public void setExchangedCoins(Map<Integer, Integer> exchangedCoins) {
        this.exchangedCoins = exchangedCoins;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }
}
