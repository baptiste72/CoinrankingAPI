package com.example.coinrankingapi.utils;

import java.util.Locale;

public class PriceUtils {

    /**
     * Arrondi le prix d'un coin à trois décimales
     *
     * @param price Le prix du coin
     * @return Le prix du coin formaté
     */
    public static String formattedPrice(final String price) {
        return String.format(Locale.ENGLISH, "%.3f $", Float.parseFloat(price));
    }
}