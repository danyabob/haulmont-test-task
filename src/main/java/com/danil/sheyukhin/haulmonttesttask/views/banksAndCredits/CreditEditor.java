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
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CreditEditor extends VerticalLayout implements KeyNotifier {
    private CreditDao creditDao;
    private Credit credit;
    private ChangeHandler changeHandler;

    TextField name = new TextField("Название");
    IntegerField limit = new IntegerField("Лимит, руб");
    IntegerField percentage = new IntegerField("Ставка, %");
    HorizontalLayout limitPercentageLayout = new HorizontalLayout(name, limit, percentage);

    Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    Button cancel = new Button("Отменить");
    Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Credit> binder = new Binder<>(Credit.class);

    @Autowired
    public CreditEditor(CreditDao creditDao) {
        this.creditDao = creditDao;

        name.setMaxLength(32);
        limit.setMin(0);
        limit.setMax(10000000);
        percentage.setMin(0);
        percentage.setMax(100);

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
