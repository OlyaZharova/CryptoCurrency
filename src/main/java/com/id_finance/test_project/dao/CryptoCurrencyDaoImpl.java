package com.id_finance.test_project.dao;

import com.id_finance.test_project.entity.CryptoCurrency;
import com.id_finance.test_project.model.CryptoCurrencyInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CryptoCurrencyDaoImpl implements CryptoCurrencyDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Double showPrice(String symbol) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(CryptoCurrency.class, symbol).getPrice();
    }

    @Override
    public List<CryptoCurrencyInfo> showAll() {
        String sql = "Select new " + CryptoCurrencyInfo.class.getName()
                + " (e.id,e.symbol,e.price) " + " from "
                + CryptoCurrency.class.getName() + " e ";
        Session session = this.sessionFactory.getCurrentSession();
        Query<CryptoCurrencyInfo> query = session.createQuery(sql, CryptoCurrencyInfo.class);
        return query.getResultList();
    }

    @Override
    public void updatePrice(List<CryptoCurrencyInfo> cryptoCurrencies) {
        Session session = this.sessionFactory.getCurrentSession();
        for (CryptoCurrencyInfo cryptoCurrency :
                cryptoCurrencies) {
            CryptoCurrency cryptoCurrency1 = new CryptoCurrency();
            cryptoCurrency1.setId(cryptoCurrency.getId());
            cryptoCurrency1.setSymbol(cryptoCurrency.getSymbol());
            cryptoCurrency1.setPrice(cryptoCurrency.getPrice());
            session.update(cryptoCurrency1);
        }
    }
}
