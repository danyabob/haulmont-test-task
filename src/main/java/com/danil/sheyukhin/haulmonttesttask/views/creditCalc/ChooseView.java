package com.danil.sheyukhin.haulmonttesttask.views.creditCalc;

import com.danil.sheyukhin.haulmonttesttask.dao.BankDao;
import com.danil.sheyukhin.haulmonttesttask.dao.ClientDao;
import com.danil.sheyukhin.haulmonttesttask.dao.CreditDao;
import com.danil.sheyukhin.haulmonttesttask.dao.OfferDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Bank;
import com.danil.sheyukhin.haulmonttesttask.entities.Client;
import com.danil.sheyukhin.haulmonttesttask.entities.Credit;
import com.danil.sheyukhin.haulmonttesttask.entities.Offer;
import com.danil.sheyukhin.haulmonttesttask.views.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.List;

@Route("choose")
@Theme(value = Lumo.class)
public class ChooseView extends VerticalLayout {
    private ClientDao clientDao;
    private Client client;
    private BankDao bankDao;
    private Bank bank;
    private CreditDao creditDao;
    private Credit credit;
    private OfferDao offerDao;
    private Offer offer;
    private List<Client> clients;
    private List<Bank> banks;
    private List<Credit> credits;
    private ListBox<Client> clientListBox = new ListBox<>();
    private ListBox<Bank> bankListBox = new ListBox<>();
    private ListBox<Credit> creditListBox = new ListBox<>();
    private IntegerField limit = new IntegerField("Укажите сумму кредита, руб");
    private IntegerField duration = new IntegerField("Укажите срок кредита, мес");
    private Button calcButton = new Button("Рассчитать");
    private Binder<Offer> binder = new Binder<>(Offer.class);
    private boolean clientIsChoosed = false;
    private boolean bankIsChoosed = false;
    private boolean creditIsChoosed = false;

    public ChooseView(ClientDao clientDao, BankDao bankDao, CreditDao creditDao, OfferDao offerDao) {
        this.clientDao = clientDao;
        this.bankDao = bankDao;
        this.creditDao = creditDao;
        this.offerDao = offerDao;

        H6 clientLabel = new H6("Выберите клиента:");
        clients = clientDao.getAll();
        clientListBox.setItems(clients);
        clientListBox.addValueChangeListener(e -> {
            client = e.getValue();
            clientIsChoosed = true;
            calcButtonEnableListener();
        });
        VerticalLayout clientLayout = new VerticalLayout(clientLabel, clientListBox);
        clientLayout.setPadding(false);
        clientLayout.setMargin(false);

        H6 bankLabel = new H6("Выберите банк:");
        banks = bankDao.getAll();
        bankListBox.setItems(banks);
        bankListBox.addValueChangeListener(event -> {
                bank = event.getValue();
                credits = creditDao.getAllByBankId(bank.getId());
                creditListBox.setItems(credits);
                creditListBox.setVisible(true);
                bankIsChoosed = true;
                creditIsChoosed = false;
                calcButtonEnableListener();
        });
        VerticalLayout bankLayout = new VerticalLayout(bankLabel, bankListBox);
        bankLayout.setSizeFull();
        bankLayout.setPadding(false);
        bankLayout.setMargin(false);

        H6 creditLabel = new H6("Выберите доступный кредит:");
        creditListBox.setVisible(false);
        creditListBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                credit = e.getValue();
                creditIsChoosed = true;
                calcButtonEnableListener();
                limit.setMax(credit.getLimit());
                limit.setValue(credit.getLimit());
            }
        });
        VerticalLayout creditLayout = new VerticalLayout(creditLabel, creditListBox);
        creditLayout.setSizeFull();
        creditLayout.setPadding(false);
        creditLayout.setMargin(false);

        HorizontalLayout bankAndCreditLayout = new HorizontalLayout(bankLayout, creditLayout);
        bankAndCreditLayout.setSizeFull();

        VerticalLayout leftVerticalLayout = new VerticalLayout(clientLayout, bankAndCreditLayout);
        leftVerticalLayout.setSizeFull();

        limit.setWidthFull();
        limit.setMin(0);
        duration.setWidthFull();
        duration.setValue(12);
        duration.setMax(360);
        duration.setMin(0);
        calcButton.setEnabled(false);
        calcButton.addClickListener(e -> {
            if (limit.getValue() > 0 && limit.getValue() <= limit.getMax() && duration.getValue() > 0 && duration.getValue() <= duration.getMax()) {
                Integer offerId = offerDao.create(new Offer(null, client.getId(), credit.getId(), limit.getValue(), duration.getValue()));
                offer = offerDao.getById(offerId);
                offer.setClientName(client.getName());
                PaymentsView.setTempOffer(offer);
                UI.getCurrent().navigate("payments");
            }
        });

        VerticalLayout rightVerticalLayout = new VerticalLayout(limit, duration, calcButton);
        rightVerticalLayout.setWidth("40%");

        HorizontalLayout mainLayout = new HorizontalLayout(leftVerticalLayout, rightVerticalLayout);
        mainLayout.setSizeFull();
        mainLayout.setPadding(false);
        mainLayout.setMargin(false);

        add(MainView.menuBar(), mainLayout);
    }

    private void calcButtonEnableListener() {
        if (clientIsChoosed && bankIsChoosed && creditIsChoosed) {
            calcButton.setEnabled(true);
        } else {
            calcButton.setEnabled(false);
        }
    }

}
