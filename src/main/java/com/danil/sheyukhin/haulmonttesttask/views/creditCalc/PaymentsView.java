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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

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
    private Grid<Offer.Payment> paymentGrid;
    private static Integer offerId;
    private Button calcButton;

    public PaymentsView(ClientDao clientDao, BankDao bankDao, CreditDao creditDao, OfferDao offerDao) {
        this.clientDao = clientDao;
        this.bankDao = bankDao;
        this.creditDao = creditDao;
        this.offerDao = offerDao;

        offer = offerDao.getById(offerId);
        credit = creditDao.getById(1);

        calcButton = new Button("Рассчитать кредит", VaadinIcon.PLUS.create());
//        calcButton.addClickListener(e -> loadPayments());

        paymentGrid = new Grid<>(Offer.Payment.class);
        loadPayments();
        paymentGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        add(MainView.menuBar(), calcButton, paymentGrid);
    }

    private void loadPayments() {
        paymentGrid.setItems(offer.getPayments(credit.getPercentage()));
        paymentGrid.setColumns("paymentDate", "paymentSum", "bodySum", "percentageSum");
        paymentGrid.getColumnByKey("paymentDate").setHeader("Дата платежа");
        paymentGrid.getColumnByKey("paymentSum").setHeader("Сумма платежа");
        paymentGrid.getColumnByKey("bodySum").setHeader("Сумма гашения тела кредита");
        paymentGrid.getColumnByKey("percentageSum").setHeader("Сумма гашения процентов");
    }

    public static void setOfferId(Integer offerId) {
        PaymentsView.offerId = offerId;
    }
}
