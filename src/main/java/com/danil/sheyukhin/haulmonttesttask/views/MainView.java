package com.danil.sheyukhin.haulmonttesttask.views;

import com.danil.sheyukhin.haulmonttesttask.dao.ClientDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Client;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {
    private ClientDao clientDao;
    private Grid<Client> clientGrid;

    private void listClients() {
        clientGrid.setItems(clientDao.getAll());
    }

    public MainView(ClientDao clientDao) {
        this.clientDao = clientDao;
        this.clientGrid = new Grid<>(Client.class);
        add(clientGrid);
        listClients();
    }
}
