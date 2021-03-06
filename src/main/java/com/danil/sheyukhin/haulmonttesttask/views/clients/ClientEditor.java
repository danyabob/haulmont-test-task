/*
 * Copyright (c) 2021.
 * Danil Sheyukhin
 * danya.bob@gmail.com
 */

package com.danil.sheyukhin.haulmonttesttask.views.clients;

import com.danil.sheyukhin.haulmonttesttask.dao.Dao;
import com.danil.sheyukhin.haulmonttesttask.entities.Client;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class ClientEditor extends VerticalLayout implements KeyNotifier {
    private final Dao<Client> clientDao;
    private Client client;

    private TextField name;
    private TextField phone;
    private TextField email;
    private TextField passport;

    private Button save;
    private Button cancel;
    private Button delete;

    private Binder<Client> binder;
    private ChangeHandler changeHandler;

    @Autowired
    public ClientEditor(Dao<Client> clientDao) {
        this.clientDao = clientDao;

        name = new TextField("ФИО");
        name.setWidthFull();
        name.setMaxLength(32);

        phone = new TextField("Телефон");
        phone.setMaxLength(32);

        email = new TextField("e-mail");
        email.setMaxLength(32);

        passport = new TextField("Паспорт");
        passport.setMaxLength(32);

        HorizontalLayout namePhoneEmailPassport = new HorizontalLayout(name, phone, email, passport);
        namePhoneEmailPassport.setSizeFull();

        save = new Button("Сохранить", VaadinIcon.CHECK.create());
        save.getElement().getThemeList().add("primary");
        save.addClickListener(e -> save());

        cancel = new Button("Отменить");
        cancel.addClickListener(e -> editClient(null));

        delete = new Button("Удалить", VaadinIcon.TRASH.create());
        delete.getElement().getThemeList().add("error");
        delete.addClickListener(e -> delete());

        HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

        binder = new Binder<>(Client.class);
        binder.bindInstanceFields(this);

        setSpacing(true);
        setVisible(false);
        add(namePhoneEmailPassport, actions);
    }

    private void delete() {
        clientDao.delete(client.getId());
        changeHandler.onChange();
    }

    private void save() {
        if (client.getId() != null) {
            clientDao.update(client);
        } else {
            clientDao.create(client);
        }
        changeHandler.onChange();
    }

    public final void editClient(Client newClient) {
        if (newClient == null) {
            setVisible(false);
            return;
        }
        if (newClient.getId() != null) {
            client = clientDao.getById(newClient.getId());
        } else {
            client = newClient;
        }

        binder.setBean(client);

        setVisible(true);

        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public interface ChangeHandler {
        void onChange();
    }

}
