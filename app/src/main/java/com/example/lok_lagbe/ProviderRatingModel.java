package com.example.lok_lagbe;

public class ProviderRatingModel {

    String Name,Rate;

    ProviderRatingModel(){}

    public ProviderRatingModel(String name, String rate) {
        Name = name;
        Rate = rate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }
}
