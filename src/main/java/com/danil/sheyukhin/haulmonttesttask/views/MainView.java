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

    public MainView(ClientDao clientDao) {
        this.clientDao = clientDao;
        this.clientGrid = new Grid<>(Client.class);
        clientGrid.setItems(clientDao.getAll());
        clientGrid.setColumns("name", "phone", "email", "passport");
        clientGrid.getColumnByKey("name").setHeader("ФИО");
        clientGrid.getColumnByKey("phone").setHeader("Телефон");
        clientGrid.getColumnByKey("email").setHeader("e-mail");
        clientGrid.getColumnByKey("passport").setHeader("Паспорт");
        add(clientGrid);
    }

//    public MainView(ClientDao clientDao) {
//        this.clientDao = clientDao;
//        this.clientGrid = new Grid<>();
//        clientGrid.setItems(clientDao.getAll());
//        clientGrid.addColumn(Client::getName).setHeader("ФИО");
//        clientGrid.addColumn(Client::getPhone).setHeader("Телефон");
//        clientGrid.addColumn(Client::getEmail).setHeader("e-mail");
//        clientGrid.addColumn(Client::getPassport).setHeader("Паспорт");
//        add(clientGrid);
//    }
}
