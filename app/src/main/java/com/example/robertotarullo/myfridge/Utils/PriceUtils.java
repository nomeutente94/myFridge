package com.example.robertotarullo.myfridge.Utils;

import java.text.DecimalFormat;

public abstract class PriceUtils {
    // Ritorna il valore con due decimali, sotto forma di stringa
    public static String getFormattedPrice(float price){
        if(price==0)
            return "";
        String priceAsString = new DecimalFormat("##.##").format(price).replace('.', ',');
        int commaPos = priceAsString.indexOf(',');
        if(commaPos>-1){
            String decimals = priceAsString.substring(commaPos);
            if(decimals.length()==2)
                return priceAsString + "0";
            else
                return priceAsString;
        } else
            return priceAsString + ",00";
    }

    // Ritorna il valore senza virgola e decimali, sotto forma di stringa
    public static String getFormattedWeight(float weight){
        if(weight==0)
            return "";
        else
            return String.valueOf(Math.round(weight));
    }
}
