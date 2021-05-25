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
        menuBar.addItem("Список клиентов", e -> menuBar.getUI().ifPresent(ui -> ui.navigate("clients")));
        menuBar.addItem("Банки и доступные кредиты", e -> menuBar.getUI().ifPresent(ui -> ui.navigate("banks")));
        return menuBar;
    }
}
