package com.danil.sheyukhin.haulmonttesttask.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route
@Theme(value = Lumo.class)
public class MainView extends VerticalLayout {

    public MainView() {
        add(menuBar());
    }

    public static HorizontalLayout menuBar() {

        Button clientButton = new Button("Список клиентов", VaadinIcon.USER.create());
        clientButton.addClickListener(e -> UI.getCurrent().navigate("clients"));
        Button bankButton = new Button("Банки и доступные кредиты", VaadinIcon.BOOK_PERCENT.create());
        bankButton.addClickListener(e -> UI.getCurrent().navigate("banks"));
        Button offerButton = new Button("Действующие кредиты", VaadinIcon.MONEY.create());
        offerButton.addClickListener(e -> UI.getCurrent().navigate("offers"));
        Button calcButton = new Button("Рассчитать кредит", VaadinIcon.CALC.create());
        calcButton.addClickListener(e -> UI.getCurrent().navigate("choose"));
        calcButton.getElement().getStyle().set("margin-left", "auto");

        HorizontalLayout horizontalLayout = new HorizontalLayout(clientButton, bankButton, offerButton, calcButton);
        horizontalLayout.setWidthFull();
        return horizontalLayout;
    }
}
