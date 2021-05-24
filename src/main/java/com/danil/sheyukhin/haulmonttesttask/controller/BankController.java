package com.danil.sheyukhin.haulmonttesttask.controller;

import com.danil.sheyukhin.haulmonttesttask.dao.BankDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankController {
    private BankDao bankDao;

    @Autowired
    BankController(BankDao bankDao) {
        this.bankDao = bankDao;
    }

    @GetMapping("/bank")
    List<Bank> getAllBanks() {
        return bankDao.getAll();
    }

    @GetMapping("/bank/{id}")
    Bank getBankById(@PathVariable Integer id) {
        return bankDao.getById(id);
    }

    @DeleteMapping("/bank/{id}")
    void deleteBank(@PathVariable Integer id) {
        bankDao.delete(id);
    }

    @PutMapping("/bank")
    void updateBank(@RequestBody Bank bank) {
        bankDao.update(bank);
    }

    @PostMapping("/bank")
    int createBank(@RequestBody Bank bank) {
        return bankDao.create(bank);
    }
}
