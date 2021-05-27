/*
 * Copyright (c) 2021.
 * Danil Sheyukhin
 * danya.bob@gmail.com
 */

package com.danil.sheyukhin.haulmonttesttask.views.banksAndCredits;

import com.danil.sheyukhin.haulmonttesttask.dao.Dao;
import com.danil.sheyukhin.haulmonttesttask.entities.Credit;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class CreditEditor extends VerticalLayout implements KeyNotifier {
    private Dao<Credit> creditDao;
    private Credit credit;
    private ChangeHandler changeHandler;

    private TextField name;
    private IntegerField limit;
    private IntegerField percentage;
    private Button save;
    private Button cancel;
    private Button delete;
    private Binder<Credit> binder;

    public CreditEditor(Dao<Credit> creditDao) {
        this.creditDao = creditDao;

        name = new TextField("Название");
        name.setMaxLength(32);

        limit = new IntegerField("Лимит, руб");

        percentage = new IntegerField("Ставка, %");

        HorizontalLayout limitPercentageLayout = new HorizontalLayout(name, limit, percentage);

        save = new Button("Сохранить", VaadinIcon.CHECK.create());
        save.getElement().getThemeList().add("primary");
        save.addClickListener(e -> {
            if (limit.getValue() > limit.getMin() && limit.getValue() <= limit.getMax() &&
                    percentage.getValue() > percentage.getMin() && percentage.getValue() <= percentage.getMax()) {
                save();
            }
        });

        cancel = new Button("Отменить");
        cancel.addClickListener(e -> editCredit(null));

        delete = new Button("Удалить", VaadinIcon.TRASH.create());
        delete.getElement().getThemeList().add("error");
        delete.addClickListener(e -> delete());

        HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

        binder = new Binder<>(Credit.class);
        binder.forField(limit).withValidator(e -> e >= 1 && e <= 100_000_000, "1 - 100 000 000").
                bind(Credit::getLimit, Credit::setLimit);
        binder.forField(percentage).withValidator(e -> e >= 1 && e <= 100, "1 - 100").
                bind(Credit::getPercentage, Credit::setPercentage);
        binder.bindInstanceFields(this);


        setVisible(false);
        setSpacing(true);
        add(limitPercentageLayout, actions);
    }

    private void delete() {
        creditDao.delete(credit.getId());
        changeHandler.onChange();
    }

    private void save() {
        if (credit.getId() != null) {
            creditDao.update(credit);
        } else {
            creditDao.create(credit);
        }
        changeHandler.onChange();
    }

    public final void editCredit(Credit newCredit) {
        if (newCredit == null) {
            setVisible(false);
            return;
        }
        if (newCredit.getId() != null) {
            credit = creditDao.getById(newCredit.getId());
        } else {
            credit = newCredit;
        }

        binder.setBean(credit);

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
