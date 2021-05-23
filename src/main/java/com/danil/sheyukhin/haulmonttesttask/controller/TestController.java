package com.danil.sheyukhin.haulmonttesttask.controller;

import com.danil.sheyukhin.haulmonttesttask.dao.BankDao;
import com.danil.sheyukhin.haulmonttesttask.dao.ClientDao;
import com.danil.sheyukhin.haulmonttesttask.dao.CreditDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Bank;
import com.danil.sheyukhin.haulmonttesttask.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {
    ClientDao clientDao;
    BankDao bankDao;
    CreditDao creditDao;

    @Autowired
    TestController(ClientDao clientDao, BankDao bankDao, CreditDao creditDao) {
        this.clientDao = clientDao;
        this.bankDao = bankDao;
        this.creditDao = creditDao;
    }

    @GetMapping("/client")
    List<Client> getAll() {
        return clientDao.getAll();
    }

    @GetMapping("/client/{id}")
    Client getById(@PathVariable Integer id) {
        return clientDao.getById(id);
    }

    @DeleteMapping("/client/{id}")
    void delete(@PathVariable Integer id) {
        clientDao.delete(id);
    }

    @PutMapping("/client")
    void update(@RequestBody Client client) {
        clientDao.update(client);
    }

    @PostMapping("/client")
    int create(@RequestBody Client client) {
        return clientDao.create(client);
    }

    @GetMapping("/bank")
    List<Bank> getAllBanks() {
        return bankDao.getAll();
    }
}
