package com.example.carSale;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

@SpringBootTest
class CarSaleApplicationTests {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

    @Test
    public void contextLoads() {
        Car superCar = context.getBean("sportCar", Car.class);
        String result = superCar.readyForSale();

        Assert.isTrue(result.equals("Car Ready in: Sport"), "Error config description");
    }

    //HW
    @Test
    public void retroConfigTest() {
        Car superRetro = context.getBean("retroCar", Car.class);
        String res = superRetro.readyForSale();
        Assert.isTrue(res.equals("Car Ready in: Retro"), "Error config description");
    }

    @Test
    public void vanConfigTest() {
        Car superVan = context.getBean("van", Car.class);
        String res = superVan.readyForSale();
        Assert.isTrue(res.equals("Car Ready in: Van"), "Error config description");
    }

}
