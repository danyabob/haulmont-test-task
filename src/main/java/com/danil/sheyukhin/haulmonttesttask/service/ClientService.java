package com.danil.sheyukhin.haulmonttesttask.service;

import com.danil.sheyukhin.haulmonttesttask.dao.ClientDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Client;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class ClientService {
    private ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public Stream<Client> fetch(String filter, int offset, int limit) {
        return clientDao.getAll().stream()
                .filter(client -> filter == null || client.toString()
                        .toLowerCase().startsWith(filter.toLowerCase()))
                .skip(offset).limit(limit);
    }

    public int count(String filter) {
        return (int) clientDao.getAll().stream()
                .filter(client -> filter == null || client.toString()
                        .toLowerCase().startsWith(filter.toLowerCase()))
                .count();
    }

    public Stream<Client> fetchPage(String filter, int page, int pageSize) {
        return clientDao.getAll().stream()
                .filter(client -> filter == null || client.toString()
                        .toLowerCase().startsWith(filter.toLowerCase()))
                .skip((long) page * pageSize).limit(pageSize);
    }

    public int count() {
        return clientDao.getAll().size();
    }

    public List<Client> fetchAll() {
        return clientDao.getAll();
    }
}
