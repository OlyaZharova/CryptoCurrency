package com.id_finance.test_project.service;

import com.id_finance.test_project.dao.CryptoCurrencyDao;
import com.id_finance.test_project.dao.UserDao;
import com.id_finance.test_project.entity.User;
import com.id_finance.test_project.model.CryptoCurrencyInfo;
import com.id_finance.test_project.model.UserInfo;
import com.id_finance.test_project.util.CryptoCurrencyEnum;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    private static final String URL = "https://api.coinlore.net/api/ticker/?id=";
    private static final Logger log = Logger.getLogger(CryptoCurrencyServiceImpl.class);

    @Autowired
    private CryptoCurrencyDao cryptoCurrencyDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Optional<List> showAll() {
        List<CryptoCurrencyInfo> cryptoCurrencyInfos = cryptoCurrencyDao.showAll();
        if (cryptoCurrencyInfos.isEmpty()) {
            return Optional.empty();
        }
        List answer = new ArrayList();
        for (CryptoCurrencyInfo cryptoCurrency :
                cryptoCurrencyInfos) {
            answer.add(cryptoCurrency.getSymbol());
        }
        return Optional.of(answer);
    }

    @Transactional
    @Override
    public Optional<Double> showPrice(String symbol) {
        if (validationSymbol(symbol)) {
            return Optional.of(cryptoCurrencyDao.showPrice(symbol));
        } else {
            return Optional.empty();
        }

    }

    @Transactional
    @Override
    @Scheduled(fixedRate = 60000)
    public void updatePrice() {
        List<CryptoCurrencyInfo> cryptoCurrencies = cryptoCurrencyDao.showAll();
        if (!cryptoCurrencies.isEmpty()) {
            for (CryptoCurrencyInfo cryptoCurrency :
                    cryptoCurrencies) {
                String url = URL + cryptoCurrency.getId();
                try {
                    JSONObject response = JsonReader.read(url);
                    double price = response.getDouble("price_usd");
                    cryptoCurrency.setPrice(price);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            cryptoCurrencyDao.updatePrice(cryptoCurrencies);
            checkUpdates();
        }
    }

    @Transactional
    @Override
    public void checkUpdates() {
        List<UserInfo> userInfos = userDao.showAll();
        List<CryptoCurrencyInfo> cryptoCurrencyInfos = cryptoCurrencyDao.showAll();
        for (CryptoCurrencyInfo cryptoCurrency :
                cryptoCurrencyInfos) {
            for (UserInfo user :
                    userInfos) {
                if (user.getSymbol().equals(cryptoCurrency.getSymbol())) {
                    Double priceUser = user.getPrice();
                    Double price = cryptoCurrency.getPrice();
                    double percent = ((price - priceUser) / priceUser) * 100;
                    if (percent > 1) {
                        log.warn("Symbol: " + user.getSymbol() + " Username: " + user.getUsername() + " Percentage of change: " + percent + "%");
                    }
                }
            }
        }
    }

    @Override
    public boolean createUser(String username, String symbol) {
        if (username == null || symbol == null) {
            return false;
        }
        if (validationSymbol(symbol)) {
            User user = new User();
            Double price = cryptoCurrencyDao.showPrice(symbol);
            user.setUsername(username);
            user.setSymbol(symbol);
            user.setPrice(price);
            userDao.createUser(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean validationSymbol(String symbol) {
        boolean exists = true;
        try {
            CryptoCurrencyEnum.valueOf(symbol);
        } catch (IllegalArgumentException e) {
            exists = false;
        }
        return exists;
    }
}
