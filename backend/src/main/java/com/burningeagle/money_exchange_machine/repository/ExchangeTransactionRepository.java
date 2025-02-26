package com.burningeagle.money_exchange_machine.repository;

import com.burningeagle.money_exchange_machine.model.ExchangeTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeTransactionRepository extends MongoRepository<ExchangeTransaction, String> {
}
