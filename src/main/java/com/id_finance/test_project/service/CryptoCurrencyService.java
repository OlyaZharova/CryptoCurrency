package com.id_finance.test_project.service;

import java.util.List;
import java.util.Optional;

public interface CryptoCurrencyService {
    Optional<List> showAll();

    Optional<Double> showPrice(String symbol);

    void updatePrice();

    void checkUpdates();

    boolean createUser(String username, String symbol);
}
