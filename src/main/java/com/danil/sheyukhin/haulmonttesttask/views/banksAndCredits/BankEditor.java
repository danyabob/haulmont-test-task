package com.danil.sheyukhin.haulmonttesttask.views.banksAndCredits;

import com.danil.sheyukhin.haulmonttesttask.dao.BankDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Bank;
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
public class BankEditor extends VerticalLayout implements KeyNotifier {
    private BankDao bankDao;
    private Bank bank;

    TextField name = new TextField("Название банка");

    Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    Button cancel = new Button("Отменить");
    Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Bank> binder = new Binder<>(Bank.class);
    private ChangeHandler changeHandler;

    @Autowired
    public BankEditor(BankDao bankDao) {
        this.bankDao = bankDao;

        add(name, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editBank(null));
        setVisible(false);
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

    public interface ChangeHandler {
        void onChange();
    }

    public final void editBank(Bank newBank) {
        if (newBank == null) {
            setVisible(false);
            return;
        }
        if (newBank.getId() != null) {
            bank = bankDao.getById(newBank.getId());
        }
        else {
            bank = newBank;
        }

        binder.setBean(bank);

        setVisible(true);

        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}
