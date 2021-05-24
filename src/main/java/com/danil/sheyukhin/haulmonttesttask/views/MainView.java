package com.danil.sheyukhin.haulmonttesttask.views;

import com.danil.sheyukhin.haulmonttesttask.dao.ClientDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Client;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {
    private ClientDao clientDao;
    private ClientEditor clientEditor;
    private Grid<Client> clientGrid;
    private Button addNewClientButton;

    public MainView(ClientDao clientDao, ClientEditor clientEditor) {
        this.clientDao = clientDao;
        this.clientEditor = clientEditor;
        this.clientGrid = new Grid<>(Client.class);
        this.addNewClientButton = new Button("Новый клиент", VaadinIcon.PLUS.create());

        loadClients();

        HorizontalLayout actions = new HorizontalLayout(addNewClientButton);
        add(actions, clientGrid, clientEditor);

        clientGrid.asSingleSelect().addValueChangeListener(e -> {
            clientEditor.editClient(e.getValue());
        });

        addNewClientButton.addClickListener(e -> clientEditor.editClient(new Client(null, "", "", "", "", null)));

        clientEditor.setChangeHandler(() -> {
            clientEditor.setVisible(false);
            loadClients();
        });



    }

    private void loadClients() {
        clientGrid.setItems(clientDao.getAll());
        clientGrid.setColumns("name", "phone", "email", "passport");
        clientGrid.getColumnByKey("name").setHeader("ФИО");
        clientGrid.getColumnByKey("phone").setHeader("Телефон");
        clientGrid.getColumnByKey("email").setHeader("e-mail");
        clientGrid.getColumnByKey("passport").setHeader("Паспорт");
    }
}
