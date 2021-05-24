package com.danil.sheyukhin.haulmonttesttask.controller;

import com.danil.sheyukhin.haulmonttesttask.dao.ClientDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {
    ClientDao clientDao;

    @Autowired
    ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @GetMapping("/client")
    List<Client> getAllClients() {
        return clientDao.getAll();
    }

    @GetMapping("/client/{id}")
    Client getClientById(@PathVariable Integer id) {
        return clientDao.getById(id);
    }

    @DeleteMapping("/client/{id}")
    void deleteClient(@PathVariable Integer id) {
        clientDao.delete(id);
    }

    @PutMapping("/client")
    void updateClient(@RequestBody Client client) {
        clientDao.update(client);
    }

    @PostMapping("/client")
    int createClient(@RequestBody Client client) {
        return clientDao.create(client);
    }
}
