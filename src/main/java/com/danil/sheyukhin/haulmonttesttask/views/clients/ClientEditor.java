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
    private Dao<Client> clientDao;
    private Client client;

    TextField name = new TextField("ФИО");
    TextField phone = new TextField("Телефон");
    TextField email = new TextField("e-mail");
    TextField passport = new TextField("Паспорт");
    HorizontalLayout namePhoneEmailPassport = new HorizontalLayout(name, phone, email, passport);

    Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    Button cancel = new Button("Отменить");
    Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Client> binder = new Binder<>(Client.class);
    private ChangeHandler changeHandler;

    @Autowired
    public ClientEditor(Dao<Client> clientDao) {
        this.clientDao = clientDao;

        name.setWidthFull();
        name.setMaxLength(32);
        phone.setMaxLength(32);
        email.setMaxLength(32);
        passport.setMaxLength(32);
        namePhoneEmailPassport.setSizeFull();
        add(namePhoneEmailPassport, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editClient(null));
        setVisible(false);
    }

    void delete() {
        clientDao.delete(client.getId());
        changeHandler.onChange();
    }

    void save() {
        if (client.getId() != null) {
            clientDao.update(client);
        } else {
            clientDao.create(client);
        }
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editClient(Client newClient) {
        if (newClient == null) {
            setVisible(false);
            return;
        }
        if (newClient.getId() != null) {
            client = clientDao.getById(newClient.getId());
        }
        else {
            client = newClient;
        }

        binder.setBean(client);

        setVisible(true);

        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
