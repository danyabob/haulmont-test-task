/*
 * Copyright (c) 2021.
 * Danil Sheyukhin
 * danya.bob@gmail.com
 */

package com.danil.sheyukhin.haulmonttesttask.views.banksAndCredits;

import com.danil.sheyukhin.haulmonttesttask.dao.CreditDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Client;
import com.danil.sheyukhin.haulmonttesttask.entities.Credit;
import com.vaadin.flow.component.Key;
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
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CreditEditor extends VerticalLayout implements KeyNotifier {
    private CreditDao creditDao;
    private Credit credit;
    private ChangeHandler changeHandler;

    private TextField name = new TextField("Название");
    private IntegerField limit = new IntegerField("Лимит, руб");
    private IntegerField percentage = new IntegerField("Ставка, %");
    private HorizontalLayout limitPercentageLayout = new HorizontalLayout(name, limit, percentage);

    private Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Отменить");
    private Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<Credit> binder = new Binder<>(Credit.class);

    @Autowired
    public CreditEditor(CreditDao creditDao) {
        this.creditDao = creditDao;

        name.setMaxLength(32);

        binder.forField(limit).withValidator(e -> e >= 1 && e <= 100_000_000, "1 - 100 000 000").
                bind(Credit::getLimit, Credit::setLimit);
        binder.forField(percentage).withValidator(e -> e >= 1 && e <= 100, "1 - 100").
                bind(Credit::getPercentage, Credit::setPercentage);

        add(limitPercentageLayout, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        save.addClickListener(e -> {
            if (limit.getValue() > limit.getMin() && limit.getValue() <= limit.getMax() &&
                    percentage.getValue() > percentage.getMin() && percentage.getValue() <= percentage.getMax()) {
                save();
            }
        });
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCredit(null));
        setVisible(false);
    }

    void delete() {
        creditDao.delete(credit.getId());
        changeHandler.onChange();
    }

    void save() {
        if (credit.getId() != null) {
            creditDao.update(credit);
        } else {
            creditDao.create(credit);
        }
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editCredit(Credit newCredit) {
        if (newCredit == null) {
            setVisible(false);
            return;
        }
        if (newCredit.getId() != null) {
            credit = creditDao.getById(newCredit.getId());
        }
        else {
            credit = newCredit;
        }

        binder.setBean(credit);

        setVisible(true);

        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
