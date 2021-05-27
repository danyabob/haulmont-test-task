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
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.stream.Stream;

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
    private ComboBox<Client> clientComboBox = new ComboBox<>();
    private ComboBox<Bank> bankComboBox = new ComboBox<>();
    private ComboBox<Credit> creditComboBox = new ComboBox<>();
    private IntegerField limit = new IntegerField("Укажите сумму кредита, руб");
    private IntegerField duration = new IntegerField("Укажите срок кредита, мес");
    private Button calcButton = new Button("Рассчитать", VaadinIcon.CALC.create());
    private boolean clientIsChoosed = false;
    private boolean bankIsChoosed = false;
    private boolean creditIsChoosed = false;

    public ChooseView(ClientDao clientDao, BankDao bankDao, CreditDao creditDao, OfferDao offerDao) {
        this.clientDao = clientDao;
        this.bankDao = bankDao;
        this.creditDao = creditDao;
        this.offerDao = offerDao;

        H6 clientLabel = new H6("Выберите клиента:");
        clientComboBox.setDataProvider(this::fetchClient, this::fetchCountClient);
        clientComboBox.setWidthFull();
        clientComboBox.addValueChangeListener(e -> {
            client = e.getValue();
            clientIsChoosed = true;
            calcButtonEnableListener();
        });
        VerticalLayout clientLayout = new VerticalLayout(clientLabel, clientComboBox);
        clientLayout.setPadding(false);
        clientLayout.setMargin(false);

        H6 bankLabel = new H6("Выберите банк:");
        bankComboBox.setDataProvider(this::fetchBank, this::fetchCountBank);
        bankComboBox.addValueChangeListener(event -> {
            bank = event.getValue();
            creditComboBox.setDataProvider(this::fetchCredit, this::fetchCountCredit);
            creditComboBox.setVisible(true);
            bankIsChoosed = true;
            creditIsChoosed = false;
            calcButtonEnableListener();
        });
        VerticalLayout bankLayout = new VerticalLayout(bankLabel, bankComboBox);
        bankLayout.setSizeFull();
        bankLayout.setPadding(false);
        bankLayout.setMargin(false);

        H6 creditLabel = new H6("Выберите доступный кредит:");
        creditComboBox.setVisible(false);
        creditComboBox.setWidthFull();
        creditComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                credit = e.getValue();
                creditIsChoosed = true;
                calcButtonEnableListener();
                limit.setMax(credit.getLimit());
                limit.setValue(credit.getLimit());
                limit.setErrorMessage("1 - " + limit.getMax() + " руб");
            }
        });
        VerticalLayout creditLayout = new VerticalLayout(creditLabel, creditComboBox);
        creditLayout.setSizeFull();
        creditLayout.setPadding(false);
        creditLayout.setMargin(false);

        HorizontalLayout bankAndCreditLayout = new HorizontalLayout(bankLayout, creditLayout);
        bankAndCreditLayout.setSizeFull();

        VerticalLayout leftVerticalLayout = new VerticalLayout(clientLayout, bankAndCreditLayout);
        leftVerticalLayout.setSizeFull();

        limit.setWidthFull();
        limit.setMin(1);

        duration.setWidthFull();
        duration.setValue(12);
        duration.setMax(360);
        duration.setMin(1);
        duration.setErrorMessage("1 - 360 мес");

        calcButton.setEnabled(false);
        calcButton.getElement().getThemeList().add("primary");
        calcButton.addClickListener(e -> {
            if (limit.getValue() > 0 && limit.getValue() <= limit.getMax() && duration.getValue() > 0 && duration.getValue() <= duration.getMax()) {
                Integer offerId = offerDao.create(new Offer(null, client.getId(), credit.getId(), limit.getValue(), duration.getValue()));
                offer = offerDao.getById(offerId);
                offer.setClientName(client.getName());
                PaymentsView.setTempOffer(offer);
                UI.getCurrent().navigate("payments");
            }
        });

        HorizontalLayout calcButtonLayout = new HorizontalLayout(calcButton);
        calcButtonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        calcButtonLayout.setWidthFull();

        VerticalLayout rightVerticalLayout = new VerticalLayout(limit, duration, calcButtonLayout);
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

    public Stream<Client> fetchClient(String filter, int offset, int limit) {
        return clientDao.getAll().stream()
                .filter(client -> filter == null ||
                        client.getName().toLowerCase().startsWith(filter.toLowerCase()) ||
                        client.getPhone().toLowerCase().startsWith(filter.toLowerCase()) ||
                        client.getEmail().toLowerCase().startsWith(filter.toLowerCase()) ||
                        client.getPassport().toLowerCase().startsWith(filter.toLowerCase()))
                .skip(offset).limit(limit);
    }

    public int fetchCountClient(String filter) {
        return (int) clientDao.getAll().stream()
                .filter(client -> filter == null ||
                        client.getName().toLowerCase().startsWith(filter.toLowerCase()) ||
                        client.getPhone().toLowerCase().startsWith(filter.toLowerCase()) ||
                        client.getEmail().toLowerCase().startsWith(filter.toLowerCase()) ||
                        client.getPassport().toLowerCase().startsWith(filter.toLowerCase()))
                .count();
    }

    public Stream<Bank> fetchBank(String filter, int offset, int limit) {
        return bankDao.getAll().stream()
                .filter(bank -> filter == null ||
                        bank.getName().toLowerCase().startsWith(filter.toLowerCase()))
                .skip(offset).limit(limit);
    }

    public int fetchCountBank(String filter) {
        return (int) bankDao.getAll().stream()
                .filter(bank -> filter == null ||
                        bank.getName().toLowerCase().startsWith(filter.toLowerCase()))
                .count();
    }

    public Stream<Credit> fetchCredit(String filter, int offset, int limit) {
        return creditDao.getAllByBankId(bank.getId()).stream()
                .filter(credit -> filter == null ||
                        credit.getName().toLowerCase().startsWith(filter.toLowerCase()))
                .skip(offset).limit(limit);
    }

    public int fetchCountCredit(String filter) {
        return (int) creditDao.getAllByBankId(bank.getId()).stream()
                .filter(credit -> filter == null ||
                        credit.getName().toLowerCase().startsWith(filter.toLowerCase()))
                .count();
    }

}
