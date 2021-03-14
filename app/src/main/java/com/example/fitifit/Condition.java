package com.example.fitifit;

public class Condition {

    public String getCategory (float result) {
        String category;
        if (result < 15) {
            category = "Siddetli Zayiflik";
        } else if (result >=15 && result <= 16) {
            category = "Orta duzeyde incelik";
        } else if (result >16 && result <= 18.5) {
            category = "Hafif incelik";
        } else if (result >18.5 && result <= 25) {
            category = "Normal";
        } else if (result >25 && result <= 30) {
            category = "Kilolu";
        } else if (result >30 && result <= 35) {
            category = "I. derece obez";
        } else if (result >35 && result <= 40) {
            category = "II. derece obez";
        } else {
            category ="III. derece obez";
        }
        return category;
    }


}
