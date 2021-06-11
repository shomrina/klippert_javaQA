package com.example.carSale;

/*public class ClassicConfiguration {
    public String makeConfiguration() {
        return "Classic";
    }
}*/

public class ClassicConfiguration implements Configurable {

    @Override
    public String makeConfiguration() {
        return "Classic";
    }
}
