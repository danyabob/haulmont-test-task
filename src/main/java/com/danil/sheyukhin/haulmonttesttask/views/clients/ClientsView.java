package com.danil.sheyukhin.haulmonttesttask.views.clients;

import com.danil.sheyukhin.haulmonttesttask.dao.ClientDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Client;
import com.danil.sheyukhin.haulmonttesttask.views.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("clients")
@Theme(value = Lumo.class)
public class ClientsView extends VerticalLayout {
    private ClientDao clientDao;
    private ClientEditor clientEditor;
    private Grid<Client> clientGrid;
    private Button newClientButton;

    public ClientsView(ClientDao clientDao, ClientEditor clientEditor) {
        this.clientDao = clientDao;
        this.clientEditor = clientEditor;

        newClientButton = new Button("Добавить клиента", VaadinIcon.PLUS.create());
        newClientButton.addClickListener(e -> clientEditor.editClient(new Client(null, "", "", "", "", 0)));
        HorizontalLayout addButtonLayout = new HorizontalLayout(newClientButton);

        clientGrid = new Grid<>(Client.class);
        clientGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        clientGrid.setHeight("300px");
        clientGrid.asSingleSelect().addValueChangeListener(e -> {
            clientEditor.editClient(e.getValue());
        });
        loadClients();

        clientEditor.setChangeHandler(() -> {
            clientEditor.setVisible(false);
            loadClients();
        });

        add(MainView.menuBar(), addButtonLayout, clientGrid, clientEditor);
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
