package com.id_finance.test_project.controller;

import com.id_finance.test_project.service.CryptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CryptoCurrencyController {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @GetMapping("/cryptocurrency")
    public ResponseEntity<?> showAll() {
        Optional<List> answer = cryptoCurrencyService.showAll();
        return answer.isEmpty() ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @GetMapping("/cryptocurrency/{symbol}")
    public ResponseEntity<?> showPrice(@PathVariable String symbol) {
        Optional<Double> price = cryptoCurrencyService.showPrice(symbol);
        return price.isEmpty() ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(price, HttpStatus.OK);
    }

    @PostMapping("/cryptocurrency/{username}/{symbol}")
    public ResponseEntity<?> notify(@PathVariable String username, @PathVariable String symbol) {

        boolean flag = cryptoCurrencyService.createUser(username, symbol);

        return flag ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}