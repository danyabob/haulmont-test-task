package com.danil.sheyukhin.haulmonttesttask.views.banksAndCredits;

import com.danil.sheyukhin.haulmonttesttask.dao.BankDao;
import com.danil.sheyukhin.haulmonttesttask.dao.CreditDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Bank;
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

@Route("banks")
@Theme(value = Lumo.class)
public class BanksAndCreditsView extends VerticalLayout {
    private BankDao bankDao;
    private CreditDao creditDao;
    private BankEditor bankEditor;
    private CreditEditor creditEditor;
    private Grid<Bank> bankGrid;
    private Grid<Credit> creditGrid;
    private Button newBankButton;
    private Button editBankButton;
    private Button newCreditButton;
    private Integer bankId = null;

    public BanksAndCreditsView(BankDao bankDao, CreditDao creditDao, BankEditor bankEditor, CreditEditor creditEditor) {
        this.bankDao = bankDao;
        this.creditDao = creditDao;
        this.bankEditor = bankEditor;
        this.creditEditor = creditEditor;

        newBankButton = new Button("Добавить банк", VaadinIcon.PLUS.create());
        newBankButton.addClickListener(e -> bankEditor.editBank(new Bank(null, "")));
        editBankButton = new Button("Изменить банк", VaadinIcon.REFRESH.create());
        editBankButton.setEnabled(false);
        editBankButton.addClickListener(e -> bankEditor.editBank(bankDao.getById(bankId)));
        HorizontalLayout addBankButtonLayout = new HorizontalLayout(newBankButton, editBankButton);

        bankGrid = new Grid<>(Bank.class);
        loadBanks();

        bankGrid.setHeight("300px");
        bankGrid.asSingleSelect().addValueChangeListener(e -> {
            if (e.getValue() != null) {
                bankId = e.getValue().getId();
                editBankButton.setEnabled(true);
                newCreditButton.setEnabled(true);
                loadCredits();
            } else {
                editBankButton.setEnabled(false);
                newCreditButton.setEnabled(false);
            }
        });

        bankEditor.setChangeHandler(() -> {
            bankEditor.setVisible(false);
            loadBanks();
        });

        VerticalLayout banksLayout = new VerticalLayout(addBankButtonLayout, bankGrid, bankEditor);
        banksLayout.setMargin(false);
        banksLayout.setPadding(false);
        banksLayout.setWidth("30%");

        newCreditButton = new Button("Добавить кредит", VaadinIcon.PLUS.create());
        newCreditButton.setEnabled(false);
        newCreditButton.addClickListener(e -> creditEditor.editCredit(new Credit(null, bankId, 0, 0)));

        HorizontalLayout addCreditButtonLayout = new HorizontalLayout(newCreditButton);

        creditGrid = new Grid<>(Credit.class);
        loadCredits();

        creditGrid.setHeight("300px");
        creditGrid.asSingleSelect().addValueChangeListener(e -> {
            creditEditor.editCredit(e.getValue());
        });

        creditEditor.setChangeHandler(() -> {
            creditEditor.setVisible(false);
            loadCredits();
        });

        VerticalLayout creditsLayout = new VerticalLayout(addCreditButtonLayout, creditGrid, creditEditor);
        creditsLayout.setMargin(false);
        creditsLayout.setPadding(false);
        creditsLayout.setWidth("60%");

        HorizontalLayout banksAndCreditsLayout = new HorizontalLayout(banksLayout, creditsLayout);
        banksAndCreditsLayout.setSizeFull();
        banksAndCreditsLayout.setMargin(false);
        banksAndCreditsLayout.setPadding(false);
        banksAndCreditsLayout.setSpacing(true);

        add(MainView.menuBar(), banksAndCreditsLayout);
    }

    public void loadBanks() {
        bankGrid.setItems(bankDao.getAll());
        bankGrid.setColumns("name");
        bankGrid.getColumnByKey("name").setHeader("Название");
    }

    private void loadCredits() {
        creditGrid.setItems(creditDao.getAllByBankId(bankId));
        creditGrid.setColumns("limit", "percentage");
        creditGrid.getColumnByKey("limit").setHeader("Лимит кредита");
        creditGrid.getColumnByKey("percentage").setHeader("Процентная ставка");
    }
}
