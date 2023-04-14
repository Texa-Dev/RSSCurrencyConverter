package com.example.rsscurrencyconverter.menu;

import android.content.Context;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.rsscurrencyconverter.data.Currency;

import java.util.LinkedList;
import java.util.List;

public class MenuManager {


    public static void currencyPopUp(
            Context context,
            List<Currency> list,
            TextView currencyView,
            View valueView

    ) {
        PopupMenu popupMenu = new PopupMenu(context, currencyView, Gravity.END);
        Menu menu = popupMenu.getMenu();
        LinkedList<Currency> linkedList = new LinkedList<>(list);
        linkedList.addFirst(Currency.BASE);

        int id = 0;
        for (Currency currency : linkedList) {
            menu.add(1, id++, Menu.NONE, currency.getCcy());
        }

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            Currency currency = linkedList.get(menuItem.getItemId());
            currencyView.setTag(currency);
            currencyView.setText(String.format("%s %s",currency.getCcy(), currency.getBuy()));
            return true;
        });
        popupMenu.show();
    }
}
