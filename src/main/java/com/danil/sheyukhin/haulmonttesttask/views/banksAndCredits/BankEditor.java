/*
 * Copyright (c) 2021.
 * Danil Sheyukhin
 * danya.bob@gmail.com
 */

package com.danil.sheyukhin.haulmonttesttask.views.banksAndCredits;

import com.danil.sheyukhin.haulmonttesttask.dao.Dao;
import com.danil.sheyukhin.haulmonttesttask.entities.Bank;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class BankEditor extends VerticalLayout implements KeyNotifier {
    private Dao<Bank> bankDao;
    private Bank bank;
    private ChangeHandler changeHandler;

    private TextField name;
    private Button save;
    private Button cancel;
    private Button delete;
    private Binder<Bank> binder;

    public BankEditor(Dao<Bank> bankDao) {
        this.bankDao = bankDao;

        name = new TextField("Название банка");
        name.setMaxLength(32);

        save = new Button("Сохранить", VaadinIcon.CHECK.create());
        save.getElement().getThemeList().add("primary");
        save.addClickListener(e -> save());

        cancel = new Button("Отменить");
        cancel.addClickListener(e -> editBank(null));

        delete = new Button("Удалить", VaadinIcon.TRASH.create());
        delete.getElement().getThemeList().add("error");
        delete.addClickListener(e -> delete());

        HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

        binder = new Binder<>(Bank.class);
        binder.bindInstanceFields(this);

        setSpacing(true);
        setVisible(false);
        add(name, actions);
    }

    void delete() {
        bankDao.delete(bank.getId());
        changeHandler.onChange();
    }

    void save() {
        if (bank.getId() != null) {
            bankDao.update(bank);
        } else {
            bankDao.create(bank);
        }
        changeHandler.onChange();
    }

    public final void editBank(Bank newBank) {
        if (newBank == null) {
            setVisible(false);
            return;
        }
        if (newBank.getId() != null) {
            bank = bankDao.getById(newBank.getId());
        } else {
            bank = newBank;
        }

        binder.setBean(bank);

        name.focus();

        setVisible(true);
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public interface ChangeHandler {
        void onChange();
    }
}
