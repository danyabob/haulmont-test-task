package com.danil.sheyukhin.haulmonttesttask.views.creditCalc;

import com.danil.sheyukhin.haulmonttesttask.dao.BankDao;
import com.danil.sheyukhin.haulmonttesttask.dao.ClientDao;
import com.danil.sheyukhin.haulmonttesttask.dao.CreditDao;
import com.danil.sheyukhin.haulmonttesttask.dao.OfferDao;
import com.danil.sheyukhin.haulmonttesttask.entities.Offer;
import com.danil.sheyukhin.haulmonttesttask.views.MainMenu;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.List;
import java.util.stream.Stream;

@Route("offers")
@Theme(value = Lumo.class)
public class OffersView extends VerticalLayout {
    private ClientDao clientDao;
    private BankDao bankDao;
    private CreditDao creditDao;
    private OfferDao offerDao;
    private List<Offer> offers;
    private Grid<Offer> offerGrid;
    private TextField offerFilterTextField;

    public OffersView(ClientDao clientDao, BankDao bankDao, CreditDao creditDao, OfferDao offerDao) {
        this.clientDao = clientDao;
        this.bankDao = bankDao;
        this.creditDao = creditDao;
        this.offerDao = offerDao;

        offerFilterTextField = new TextField();
        offerFilterTextField.setPlaceholder("Быстрый поиск");
        offerFilterTextField.setValueChangeMode(ValueChangeMode.EAGER);
        offerFilterTextField.addValueChangeListener(e -> offerGrid.setItems(fetchOffer(e.getValue())));

        offerGrid = new Grid<>(Offer.class);
        loadOffers();
        offerGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        offerGrid.addItemClickListener(e -> {
            PaymentsView.setTempOffer(e.getItem());
            UI.getCurrent().navigate("payments");
        });

        add(MainMenu.menuBar(), offerFilterTextField, offerGrid);
    }

    public void loadOffers() {
        offers = offerDao.getAll();
        for (Offer offer : offers) {
            offer.setClientName(clientDao.getById(offer.getClientId()).getName());
            offer.setBankName(bankDao.getById(creditDao.getById(offer.getCreditId()).getBankId()).getName());
            offer.setCreditName(creditDao.getById(offer.getCreditId()).getName());
            offer.setPercentage(creditDao.getById(offer.getCreditId()).getPercentage());
        }
        offerGrid.setItems(offers);
        offerGrid.setColumns("clientName", "bankName", "creditName", "summa", "percentage", "duration");
        offerGrid.getColumnByKey("clientName").setHeader("ФИО клиента").setAutoWidth(true);
        offerGrid.getColumnByKey("bankName").setHeader("Название банка");
        offerGrid.getColumnByKey("creditName").setHeader("Тип кредита");
        offerGrid.getColumnByKey("summa").setHeader("Сумма, руб");
        offerGrid.getColumnByKey("percentage").setHeader("Ставка, %");
        offerGrid.getColumnByKey("duration").setHeader("Срок, мес");
    }

    public Stream<Offer> fetchOffer(String filter) {
        return offers.stream()
                .filter(offer -> filter == null ||
                        offer.getClientName().toLowerCase().startsWith(filter.toLowerCase()) ||
                        offer.getBankName().toLowerCase().startsWith(filter.toLowerCase()) ||
                        offer.getCreditName().toLowerCase().startsWith(filter.toLowerCase()));
    }
}
