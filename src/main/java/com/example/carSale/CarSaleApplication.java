package com.example.carSale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class CarSaleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarSaleApplication.class, args);

        //для первых двух примеров
		/*Car carForFamily = new Car(configuration);
		System.out.println(carForFamily.readyForSale());

		Car carForYoung = new Car(configuration);
		System.out.println(carForYoung.readyForSale());*/

        //HW, 1) example strong dependency
		/*Car retroCar = new Car(configuration);
		System.out.println(retroCar.readyForSale());*/

		/*Car van = new Car(configuration);
		System.out.println(van.readyForSale());*/

        //пример dependency Injection на простой Java (без Спринга) / внедрение зависимостей
	/*	Car carForFamily = new Car(new ClassicConfiguration());
		System.out.println(carForFamily.readyForSale());

		Car carForYoung = new Car(new SportConfiguration());
		System.out.println(carForYoung.readyForSale());*/

        //HW. 2
//		Car retroCar = new Car(new SportConfiguration());
//		System.out.println(retroCar.readyForSale());

//		Car van = new Car(new VanConfiguration());
//		System.out.println(van.readyForSale());

        //работа с бинами (use Spring, springConfig)
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class); //куда обращаться чтобы через контекст их контейнера получить необж данные

        Car newCarForFamily = context.getBean("classicCar", Car.class); //bean получаем по названию. сообщаем контейнеру класс
        Car newCarForStudent = context.getBean("sportCar", Car.class);
        System.out.println(newCarForFamily.readyForSale());
        System.out.println(newCarForStudent.readyForSale());

        //HW 3. dependency inj
        Car newRetroCar = context.getBean("retroCar", Car.class);
        Car newVan = context.getBean("van", Car.class);
        System.out.println(newRetroCar.readyForSale());
        System.out.println(newVan.readyForSale());

    }



}
