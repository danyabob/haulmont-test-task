package com.danil.sheyukhin.haulmonttesttask.controller;

import com.danil.sheyukhin.haulmonttesttask.creditCalc.Payment;
import com.danil.sheyukhin.haulmonttesttask.dao.OfferDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OfferController {
    private OfferDao offerDao;

    @Autowired
    OfferController(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    @GetMapping("/offer")
    List<Offer> getAllOffers() {
        return offerDao.getAll();
    }

    @GetMapping("/offer/{id}")
    Offer getOfferById(@PathVariable Integer id) {
        return offerDao.getById(id);
    }

    @DeleteMapping("/offer/{id}")
    void deleteOffer(@PathVariable Integer id) {
        offerDao.delete(id);
    }

    @PutMapping("/offer")
    void updateOffer(@RequestBody Offer offer) {
        offerDao.update(offer);
    }

    @PostMapping("/offer")
    int createOffer(@RequestBody Offer offer) {
        return offerDao.create(offer);
    }

    @GetMapping("/payment")
    List<Payment> getPayments() {
        Offer offer = offerDao.getById(1);
        return offer.getPayments();
    }
}
