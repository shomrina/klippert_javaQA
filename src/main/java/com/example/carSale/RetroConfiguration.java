package com.example.carSale;

/*
public class RetroConfiguration {

    public String makeConfiguration() {
        return "Retro";
    }
}
*/


public class RetroConfiguration implements Configurable {

    @Override
    public String makeConfiguration() {
        return "Retro";
    }
}