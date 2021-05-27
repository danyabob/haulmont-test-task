package com.danil.sheyukhin.haulmonttesttask.views.banksAndCredits;

import com.danil.sheyukhin.haulmonttesttask.dao.BankDao;
import com.danil.sheyukhin.haulmonttesttask.dao.CreditDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Bank;
import com.danil.sheyukhin.haulmonttesttask.entities.Credit;
import com.danil.sheyukhin.haulmonttesttask.views.clients.ClientsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.stream.Stream;

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
    private TextField bankFilterTextField;
    private Button editBankButton;
    private Button newCreditButton;
    private TextField creditFilterTextField;
    private Integer bankId = null;

    public BanksAndCreditsView(BankDao bankDao, CreditDao creditDao, BankEditor bankEditor, CreditEditor creditEditor) {
        this.bankDao = bankDao;
        this.creditDao = creditDao;
        this.bankEditor = bankEditor;
        this.creditEditor = creditEditor;

        newBankButton = new Button("Добавить банк", VaadinIcon.PLUS.create());
        newBankButton.getElement().getThemeList().add("primary");
        newBankButton.addClickListener(e -> bankEditor.editBank(new Bank(null, "")));

        bankFilterTextField = new TextField();
        bankFilterTextField.setPlaceholder("Быстрый поиск");
        bankFilterTextField.setValueChangeMode(ValueChangeMode.EAGER);
        bankFilterTextField.addValueChangeListener(e -> bankGrid.setItems(fetchBank(e.getValue())));

        editBankButton = new Button("Изменить банк", VaadinIcon.REFRESH.create());
        editBankButton.setEnabled(false);
        editBankButton.addClickListener(e -> bankEditor.editBank(bankDao.getById(bankId)));
        HorizontalLayout addBankButtonLayout = new HorizontalLayout(newBankButton, bankFilterTextField, editBankButton);
        addBankButtonLayout.setWidthFull();

        bankGrid = new Grid<>(Bank.class);
        bankGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        loadBanks();

        bankGrid.asSingleSelect().addValueChangeListener(e -> {
            if (e.getValue() != null) {
                bankId = e.getValue().getId();
                editBankButton.setEnabled(true);
                newCreditButton.setEnabled(true);
                creditFilterTextField.setEnabled(true);
                loadCredits();
            } else {
                creditGrid.setItems();
                editBankButton.setEnabled(false);
                newCreditButton.setEnabled(false);
                creditFilterTextField.setEnabled(false);
            }
        });

        bankEditor.setChangeHandler(() -> {
            bankEditor.setVisible(false);
            loadBanks();
            loadCredits();
        });

        VerticalLayout banksLayout = new VerticalLayout(addBankButtonLayout, bankGrid, bankEditor);
        banksLayout.setSizeFull();
        banksLayout.setMargin(false);
        banksLayout.setPadding(false);

        newCreditButton = new Button("Добавить кредит", VaadinIcon.PLUS.create());
        newCreditButton.getElement().getThemeList().add("primary");
        newCreditButton.setEnabled(false);
        newCreditButton.addClickListener(e -> creditEditor.editCredit(new Credit(null, bankId, "", 0, 0)));

        creditFilterTextField = new TextField();
        creditFilterTextField.setPlaceholder("Быстрый поиск");
        creditFilterTextField.setValueChangeMode(ValueChangeMode.EAGER);
        creditFilterTextField.setEnabled(false);
        creditFilterTextField.addValueChangeListener(e -> creditGrid.setItems(fetchCredit(e.getValue())));

        HorizontalLayout addCreditButtonLayout = new HorizontalLayout(newCreditButton, creditFilterTextField);
        addCreditButtonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        addCreditButtonLayout.setWidthFull();

        creditGrid = new Grid<>(Credit.class);
        creditGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        loadCredits();

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

        HorizontalLayout banksAndCreditsLayout = new HorizontalLayout(banksLayout, creditsLayout);
        banksAndCreditsLayout.setSizeFull();
        banksAndCreditsLayout.setMargin(false);
        banksAndCreditsLayout.setPadding(false);
        banksAndCreditsLayout.setSpacing(true);

        setSizeFull();
        add(ClientsView.menuBar(), banksAndCreditsLayout);
    }

    public void loadBanks() {
        bankGrid.setItems(bankDao.getAll());
        bankGrid.setColumns("name");
        bankGrid.getColumnByKey("name").setHeader("Название банка");
    }

    private void loadCredits() {
        creditGrid.setItems(creditDao.getAllByBankId(bankId));
        creditGrid.setColumns("name", "limit", "percentage");
        creditGrid.getColumnByKey("name").setHeader("Тип кредита");
        creditGrid.getColumnByKey("limit").setHeader("Лимит кредита, руб");
        creditGrid.getColumnByKey("percentage").setHeader("Процентная ставка, %");
    }

    public Stream<Bank> fetchBank(String filter) {
        return bankDao.getAll().stream()
                .filter(bank -> filter == null ||
                        bank.getName().toLowerCase().startsWith(filter.toLowerCase()));
    }

    public Stream<Credit> fetchCredit(String filter) {
        return creditDao.getAllByBankId(bankId).stream()
                .filter(credit -> filter == null ||
                        credit.getName().toLowerCase().startsWith(filter.toLowerCase()));
    }
}
