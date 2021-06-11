package com.example.carSale;

/*public class SportConfiguration {
    public String makeConfiguration() {
        return "Sport";
    }
}*/

public class SportConfiguration implements Configurable {

    @Override
    public String makeConfiguration() {
        return "Sport";
    }
}