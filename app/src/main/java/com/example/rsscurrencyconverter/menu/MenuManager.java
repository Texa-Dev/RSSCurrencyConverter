package com.example.rsscurrencyconverter.menu;

import android.content.Context;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.PopupMenu;

import com.example.rsscurrencyconverter.data.Currency;

import java.util.List;

public class MenuManager {


    public static void currencyPopUp(
            Context context,
            List<Currency> list,
            View currencyView,
            View valueView

    ) {
        PopupMenu popupMenu = new PopupMenu(context, currencyView, Gravity.END);
        Menu menu = popupMenu.getMenu();
        int id = 0;
        for (Currency currency : list) {
            menu.add(1, id++, Menu.NONE, currency.getCcy());
        }

        /*popupMenu.setOnMenuItemClickListener(menuItem ->{
            //java.util.Currency currency = new Currency();
        });*/
    }
}
