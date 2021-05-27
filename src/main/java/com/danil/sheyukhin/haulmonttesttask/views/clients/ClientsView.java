/*
 * Copyright (c) 2021.
 * Danil Sheyukhin
 * danya.bob@gmail.com
 */

package com.danil.sheyukhin.haulmonttesttask.views.clients;

import com.danil.sheyukhin.haulmonttesttask.dao.ClientDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Client;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.stream.Stream;

@Route("")
@PageTitle("Список клиентов")
@Theme(value = Lumo.class)
public class ClientsView extends VerticalLayout {
    private ClientDao clientDao;
    private ClientEditor clientEditor;
    private Grid<Client> clientGrid;
    private Button newClientButton;
    private TextField filterTextField;

    public ClientsView(ClientDao clientDao, ClientEditor clientEditor) {
        this.clientDao = clientDao;
        this.clientEditor = clientEditor;

        newClientButton = new Button("Добавить клиента", VaadinIcon.PLUS.create());
        newClientButton.getElement().getThemeList().add("primary");
        newClientButton.addClickListener(e -> clientEditor.editClient(new Client(null, "", "", "", "", 0)));

        filterTextField = new TextField();
        filterTextField.setPlaceholder("Быстрый поиск");
        filterTextField.setValueChangeMode(ValueChangeMode.EAGER);
        filterTextField.addValueChangeListener(e -> clientGrid.setItems(fetchClient(e.getValue())));
        HorizontalLayout addButtonLayout = new HorizontalLayout(newClientButton, filterTextField);

        clientGrid = new Grid<>(Client.class);
        clientGrid.setHeight("50%");
        clientGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        clientGrid.asSingleSelect().addValueChangeListener(e -> {
            clientEditor.editClient(e.getValue());
        });
        loadClients();

        clientEditor.setChangeHandler(() -> {
            clientEditor.setVisible(false);
            loadClients();
        });

        setHeightFull();
        add(menuBar(), addButtonLayout, clientGrid, clientEditor);
    }

    private void loadClients() {
        clientGrid.setItems(clientDao.getAll());
        clientGrid.setColumns("name", "phone", "email", "passport");
        clientGrid.getColumnByKey("name").setHeader("ФИО");
        clientGrid.getColumnByKey("phone").setHeader("Телефон");
        clientGrid.getColumnByKey("email").setHeader("e-mail");
        clientGrid.getColumnByKey("passport").setHeader("Паспорт");
    }

    public Stream<Client> fetchClient(String filter) {
        return clientDao.getAll().stream()
                .filter(client -> filter == null ||
                        client.getName().toLowerCase().startsWith(filter.toLowerCase()) ||
                        client.getPhone().toLowerCase().startsWith(filter.toLowerCase()) ||
                        client.getEmail().toLowerCase().startsWith(filter.toLowerCase()) ||
                        client.getPassport().toLowerCase().startsWith(filter.toLowerCase()));
    }

    public static HorizontalLayout menuBar() {
        Button clientButton = new Button("Список клиентов", VaadinIcon.USER.create());
        clientButton.addClickListener(e -> UI.getCurrent().navigate(""));
        Button bankButton = new Button("Банки и доступные кредиты", VaadinIcon.BOOK_PERCENT.create());
        bankButton.addClickListener(e -> UI.getCurrent().navigate("banks"));
        Button offerButton = new Button("Действующие кредиты", VaadinIcon.MONEY.create());
        offerButton.addClickListener(e -> UI.getCurrent().navigate("offers"));
        Button calcButton = new Button("Рассчитать кредит", VaadinIcon.CALC.create());
        calcButton.addClickListener(e -> UI.getCurrent().navigate("choose"));
        calcButton.getElement().getStyle().set("margin-left", "auto");

        HorizontalLayout horizontalLayout = new HorizontalLayout(clientButton, bankButton, offerButton, calcButton);
        horizontalLayout.setWidthFull();
        return horizontalLayout;
    }
}
