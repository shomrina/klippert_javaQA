package com.example.carSale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.carSale") //надо передать путь проекта - именно в этой папке спринг будет сканировать компоненты
// (будет собирать контейнер из бинов и компонентов)
public class SpringConfig {
    //здесь будем реализовывать все бины

    //бины по конфигуроации автомобилей
    @Bean
    public ClassicConfiguration classicConfiguration() {
        return new ClassicConfiguration();
    } //при вызове этого бина будет возвращено классическая конфиг

    @Bean
    public SportConfiguration sportConfiguration() {
        return new SportConfiguration();
    }

    //HW
    @Bean
    public RetroConfiguration retroConfiguration() {
        return new RetroConfiguration();
    }

    @Bean
    public VanConfiguration vanConfiguration() {
        return new VanConfiguration();
    }

    //бины автомобилей
    @Bean
    public Car classicCar() {
        return new Car(classicConfiguration()); //передаем сюда бин классик-конфиг/ это тоже dependency injection внедрение зависимостей на бинах
    }

    @Bean
    public Car sportCar() {
        return new Car(sportConfiguration());
    }

    //HW
    @Bean
    public Car retroCar() {
        return  new Car(retroConfiguration());
    }

    @Bean
    public Car van() {
        return new Car(vanConfiguration());
    }


}
