package com.danil.sheyukhin.haulmonttesttask.views.CreditsView;

import com.danil.sheyukhin.haulmonttesttask.dao.CreditDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Credit;
import com.danil.sheyukhin.haulmonttesttask.views.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("credits")
@Theme(value = Lumo.class)
public class CreditsView extends VerticalLayout {
    private CreditDao creditDao;
    private CreditEditor creditEditor;
    private Grid<Credit> creditGrid;
    private Button newCreditButton;

    public CreditsView(CreditDao creditDao, CreditEditor creditEditor) {
        this.creditDao = creditDao;
        this.creditEditor = creditEditor;

        creditGrid = new Grid<>(Credit.class);
        newCreditButton = new Button("Добавить кредит", VaadinIcon.PLUS.create());
        newCreditButton.addClickListener(e -> creditEditor.editCredit(new Credit(null, 1, 0, 0)));
        HorizontalLayout addButtonLayout = new HorizontalLayout(newCreditButton);

        loadCredits();

        creditGrid.setHeight("300px");
        creditGrid.asSingleSelect().addValueChangeListener(e -> {
            creditEditor.editCredit(e.getValue());
        });

        creditEditor.setChangeHandler(() -> {
            creditEditor.setVisible(false);
            loadCredits();
        });

        add(MainView.menuBar(), addButtonLayout, creditGrid, creditEditor);
    }

    private void loadCredits() {
        creditGrid.setItems(creditDao.getAll());
        creditGrid.setColumns("limit", "percentage");
        creditGrid.getColumnByKey("limit").setHeader("Лимит кредита");
        creditGrid.getColumnByKey("percentage").setHeader("Процентная ставка");
    }
}
