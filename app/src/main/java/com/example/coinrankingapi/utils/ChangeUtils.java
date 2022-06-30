package com.example.coinrankingapi.utils;

import android.graphics.Color;

public class ChangeUtils {

    /**
     * Retourne le change du coin formaté
     *
     * @param change Le champ change du coin
     * @return Le change du coin formaté
     */
    public static String formattedChange(final String change) {
        return change + " %";
    }

    /**
     * Retourne une couleur en fonction de la valeur du change
     *
     * @param change Le champ change du coin
     * @return La couleur du texte
     */
    public static int getChangeColor(final String change) {
        int color = 0;

        if (change != null) {
            if (Float.parseFloat(change) < 0) {
                color = Color.RED;
            } else {
                color = Color.GREEN;
            }
        }

        return color;
    }
}