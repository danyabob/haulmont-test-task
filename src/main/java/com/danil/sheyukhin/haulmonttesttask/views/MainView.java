package com.danil.sheyukhin.haulmonttesttask.views;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
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

    public static MenuBar menuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setOpenOnHover(true);
        MenuItem clients = menuBar.addItem("Клиенты", e -> menuBar.getUI().ifPresent(ui -> ui.navigate("clients")));
        MenuItem banks = menuBar.addItem("Банки", e -> menuBar.getUI().ifPresent(ui -> ui.navigate("banks")));
        MenuItem credits = menuBar.addItem("Кредиты", e -> menuBar.getUI().ifPresent(ui -> ui.navigate("credits")));
        return menuBar;
    }
}
