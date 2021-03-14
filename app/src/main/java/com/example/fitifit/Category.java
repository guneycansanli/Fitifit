package com.example.fitifit;

public class Category {

    public String getCategory (float result) {
        String category;
        if (result < 15) {
            category = "Cok ciddi sekilde zayif";
        } else if (result >=15 && result <= 16) {
            category = "Ciddi zayif";
        } else if (result >16 && result <= 18.5) {
            category = "Zayif";
        } else if (result >18.5 && result <= 25) {
            category = "Normal (Saglikli agirlik)";
        } else if (result >25 && result <= 30) {
            category = "Kilolu";
        } else if (result >30 && result <= 35) {
            category = "Orta derece obez";
        } else if (result >35 && result <= 40) {
            category = "Ciddi Obez";
        } else {
            category ="Cok ciddi obez";
        }
        return category;
    }

}
