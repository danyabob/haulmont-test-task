package com.danil.sheyukhin.haulmonttesttask.controller;

import com.danil.sheyukhin.haulmonttesttask.dao.CreditDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CreditController {
    private CreditDao creditDao;

    @Autowired
    CreditController(CreditDao creditDao) {
        this.creditDao = creditDao;
    }

    @GetMapping("/credit")
    List<Credit> getAllCredits() {
        return creditDao.getAll();
    }

    @GetMapping("/credit/{id}")
    Credit getCreditById(@PathVariable Integer id) {
        return creditDao.getById(id);
    }

    @DeleteMapping("/credit/{id}")
    void deleteCredit(@PathVariable Integer id) {
        creditDao.delete(id);
    }

    @PutMapping("/credit")
    void updateCredit(@RequestBody Credit credit) {
        creditDao.update(credit);
    }

    @PostMapping("/credit")
    int createCredit(@RequestBody Credit credit) {
        return creditDao.create(credit);
    }
}
