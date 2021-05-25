package com.danil.sheyukhin.haulmonttesttask.views.banks;

import com.danil.sheyukhin.haulmonttesttask.dao.BankDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Bank;
import com.danil.sheyukhin.haulmonttesttask.entities.Client;
import com.danil.sheyukhin.haulmonttesttask.views.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("banks")
@Theme(value = Lumo.class)
public class BanksView extends VerticalLayout {
    private BankDao bankDao;
    private BankEditor bankEditor;
    private Grid<Bank> bankGrid;
    private Button newBankButton;

    public BanksView(BankDao bankDao, BankEditor bankEditor) {
        this.bankDao = bankDao;
        this.bankEditor = bankEditor;
        bankGrid = new Grid<>(Bank.class);
        newBankButton = new Button("Добавить банк", VaadinIcon.PLUS.create());
        newBankButton.addClickListener(e -> bankEditor.editBank(new Bank(null, "")));

        loadBanks();

        bankGrid.setHeight("300px");
        bankGrid.asSingleSelect().addValueChangeListener(e -> {
            bankEditor.editBank(e.getValue());
        });

        HorizontalLayout addButtonLayout = new HorizontalLayout(newBankButton);
        add(MainView.menuBar(), addButtonLayout, bankGrid, bankEditor);

        bankEditor.setChangeHandler(() -> {
            bankEditor.setVisible(false);
            loadBanks();
        });
    }

    public void loadBanks() {
        bankGrid.setItems(bankDao.getAll());
        bankGrid.setColumns("name");
        bankGrid.getColumnByKey("name").setHeader("Название");
    }
}
