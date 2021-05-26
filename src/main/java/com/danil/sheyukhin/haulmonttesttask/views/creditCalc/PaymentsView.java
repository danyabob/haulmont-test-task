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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.List;

@Route("payments")
@Theme(value = Lumo.class)
public class PaymentsView extends VerticalLayout {
    private ClientDao clientDao;
    private Client client;
    private BankDao bankDao;
    private Bank bank;
    private CreditDao creditDao;
    private Credit credit;
    private OfferDao offerDao;
    private Offer offer;
    private H4 clientName;
    private H4 creditSum;
    private H4 fullPaymentSum;
    private H4 fullPercentageSum;
    private Grid<Offer.Payment> paymentGrid;
    private List<Offer.Payment> payments;
    private static Offer tempOffer;

    public PaymentsView(ClientDao clientDao, BankDao bankDao, CreditDao creditDao, OfferDao offerDao) {
        this.clientDao = clientDao;
        this.bankDao = bankDao;
        this.creditDao = creditDao;
        this.offerDao = offerDao;
        offer = tempOffer();
        credit = creditDao.getById(offer.getCreditId());
        payments = offer.getPayments(credit.getPercentage());

        clientName = new H4("Заёмщик: " + offer.getClientName() + ",");
        creditSum = new H4("Сумма кредита: " + offer.getSumma() + " руб");
        fullPaymentSum = new H4("Сумма с процентами: " + getFullPaymentSum() + " руб,");
        fullPercentageSum = new H4("Переплата: " + getFullPercentageSum() + " руб");

        HorizontalLayout clientNameCreditSum = new HorizontalLayout(clientName, creditSum);
        HorizontalLayout fullPaymentSumFullPercentageSum = new HorizontalLayout(fullPaymentSum, fullPercentageSum);
        VerticalLayout clientInfoLayout = new VerticalLayout(clientNameCreditSum, fullPaymentSumFullPercentageSum);

        paymentGrid = new Grid<>(Offer.Payment.class);
        loadPayments();
        paymentGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        add(MainView.menuBar(), clientInfoLayout, paymentGrid);
    }

    private void loadPayments() {
        paymentGrid.setItems(payments);
        paymentGrid.setColumns("paymentDate", "paymentSum", "bodySum", "percentageSum");
        paymentGrid.getColumnByKey("paymentDate").setHeader("Дата платежа");
        paymentGrid.getColumnByKey("paymentSum").setHeader("Сумма платежа");
        paymentGrid.getColumnByKey("bodySum").setHeader("Сумма гашения тела кредита");
        paymentGrid.getColumnByKey("percentageSum").setHeader("Сумма гашения процентов");
    }

    private double getFullPercentageSum() {
        double sum = 0;
        double scale = Math.pow(10, 2);
        for (Offer.Payment payment : payments) {
            sum += payment.getPercentageSum();
        }
        return Math.ceil(sum * scale) / scale;
    }

    private double getFullPaymentSum() {
        return offer.getSumma() + getFullPercentageSum();
    }

    public static void setTempOffer(Offer tempOffer) {
        PaymentsView.tempOffer = tempOffer;
    }

    private Offer tempOffer() {
        offer = tempOffer;
        tempOffer = null;
        return offer;
    }
}
