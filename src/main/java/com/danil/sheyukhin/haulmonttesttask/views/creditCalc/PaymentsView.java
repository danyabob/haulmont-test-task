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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.text.DecimalFormat;
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
    private H6 clientName;
    private H6 creditSum;
    private H6 fullPaymentSum;
    private H6 fullPercentageSum;
    private H6 creditDuration;
    private H6 percentage;
    private Button deleteOfferButton = new Button("Удалить кредитное предложение", VaadinIcon.TRASH.create());
    private Grid<Offer.Payment> paymentGrid;
    private List<Offer.Payment> payments;
    private static Offer tempOffer;
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public PaymentsView(ClientDao clientDao, BankDao bankDao, CreditDao creditDao, OfferDao offerDao) {
        this.clientDao = clientDao;
        this.bankDao = bankDao;
        this.creditDao = creditDao;
        this.offerDao = offerDao;
        offer = tempOffer();
        credit = creditDao.getById(offer.getCreditId());
        payments = offer.getPayments(credit.getPercentage());

        clientName = new H6("Заёмщик: " + offer.getClientName() + ",");
        creditSum = new H6("Сумма кредита: " + offer.getSumma() + " руб,");
        creditDuration = new H6("Срок кредита: " + offer.getDuration() + " мес");
        fullPaymentSum = new H6("Сумма с процентами: " + decimalFormat.format(getFullPaymentSum()) + " руб,");
        fullPercentageSum = new H6("Переплата: " + decimalFormat.format(getFullPercentageSum()) + " руб,");
        percentage = new H6("Процентная ставка: " + credit.getPercentage() + " %");

        deleteOfferButton.getElement().getThemeList().add("error");
        deleteOfferButton.getElement().getStyle().set("margin-left", "auto");
        deleteOfferButton.addClickListener(e -> {
            offerDao.delete(offer.getId());
            UI.getCurrent().navigate("offers");
        });

        HorizontalLayout clientNameCreditSumCreditDuration = new HorizontalLayout(clientName, creditSum, creditDuration, deleteOfferButton);
        clientNameCreditSumCreditDuration.setWidthFull();
        HorizontalLayout fullPaymentSumFullPercentageSumPercentage = new HorizontalLayout(fullPaymentSum, fullPercentageSum, percentage);
        VerticalLayout clientInfoLayout = new VerticalLayout(clientNameCreditSumCreditDuration, fullPaymentSumFullPercentageSumPercentage);
        clientInfoLayout.setMargin(false);
        clientInfoLayout.setPadding(false);

        paymentGrid = new Grid<>(Offer.Payment.class);
        loadPayments();
        paymentGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        add(MainView.menuBar(), clientInfoLayout, paymentGrid);
    }

    private void loadPayments() {
        paymentGrid.setItems(payments);
        paymentGrid.setColumns("paymentDate", "paymentSumString", "bodySumString", "percentageSumString");
        paymentGrid.getColumnByKey("paymentDate").setHeader("Дата платежа");
        paymentGrid.getColumnByKey("paymentSumString").setHeader("Сумма платежа, руб");
        paymentGrid.getColumnByKey("bodySumString").setHeader("Сумма гашения тела кредита, руб");
        paymentGrid.getColumnByKey("percentageSumString").setHeader("Сумма гашения процентов, руб");
    }

    private double getFullPercentageSum() {
        double sum = 0;
        for (Offer.Payment payment : payments) {
            sum += payment.getPercentageSum();
        }
        return sum;
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
