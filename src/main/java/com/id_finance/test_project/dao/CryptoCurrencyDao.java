package com.id_finance.test_project.dao;

import com.id_finance.test_project.model.CryptoCurrencyInfo;

import java.util.List;

public interface CryptoCurrencyDao {

    Double showPrice(String symbol);

    List<CryptoCurrencyInfo> showAll();

    void updatePrice(List<CryptoCurrencyInfo> cryptoCurrencies);
}
