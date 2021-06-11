package com.example.carSale;

public class Car {
    //пример сильной зависимости
/*  //  private ClassicConfiguration classicConfiguration;
    private SportConfiguration sportConfiguration;

    public String readyForSale() {
       //classicConfiguration = new ClassicConfiguration();
       // sportConfiguration = new SportConfiguration();
       // return "Car Ready in: " + classicConfiguration.makeConfiguration();
       // return "Car Ready in: " + sportConfiguration.makeConfiguration();

       //HW 1) strong dep
       //retroConfiguration = new RetroConfiguration();
       //return "Car Ready in: " + retroConfiguration.makeConfiguration();
       vanConfiguration = new VanConfiguration();
       return "Car Ready in: " + vanConfiguration.makeConfiguration();

    }*/

    //пример ослабленной зависимости
/*    private Configurable configuration;
    public String readyForSale() {
//        configuration = new ClassicConfiguration();
 //       configuration = new SportConfiguration();

 // HW 2. ослабление зависимости
 //       configuration = new RetroConfiguration();
        configuration = new VanConfiguration();
        return  "Car Ready in: " + configuration.makeConfiguration();
    }*/

    //IoC (inversion of control) ослабленная зависимость
    private Configurable configuration;

    public Car(Configurable configuration) {
        this.configuration = configuration;
    }

    public String readyForSale() {
        return "Car Ready in: " + configuration.makeConfiguration();
    }
}
