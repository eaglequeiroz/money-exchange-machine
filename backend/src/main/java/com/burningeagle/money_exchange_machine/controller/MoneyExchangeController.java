package com.burningeagle.money_exchange_machine.controller;

import com.burningeagle.money_exchange_machine.model.ExchangeTransaction;
import com.burningeagle.money_exchange_machine.service.MoneyExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exchange")
@CrossOrigin(origins = "http://localhost:3000")
public class MoneyExchangeController {

    private final MoneyExchangeService exchangeService;

    public MoneyExchangeController(MoneyExchangeService exchangeService){
        this.exchangeService = exchangeService;
    }

    @PostMapping
    public ResponseEntity<String> exchange(@RequestParam int bill, @RequestParam(defaultValue = "false") boolean maxCoins){
        ExchangeTransaction transaction = exchangeService.exchangeMoney(bill, maxCoins);

        String statement = transaction.getExchangedCoins().entrySet().stream()
                .map(entry ->
                        "* " + entry.getValue() +
                        " coins (" + entry.getKey() + " cents) = $" +
                        (entry.getValue() * entry.getKey() / 100.0))
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok("Exchanged $" + bill + " successfully!\n\nStatement:\n" + statement);
    }

    @PostMapping("/increase")
    public ResponseEntity<String> increaseCoins(@RequestBody Map<Integer, Integer> additionalCoins) {
        exchangeService.increaseCoinInventory(additionalCoins);
        return ResponseEntity.ok("Coin inventory successfully updated!");
    }
}
