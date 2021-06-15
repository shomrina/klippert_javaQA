package com.example.newCalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
//TDD

@SpringBootTest
class NewCalculatorApplicationTests {

    private Calculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("test sum")
    void testSum() {
        Integer first = 3;
        Integer second = 7;
        Integer expected = 10;

        assert calculator.sum(first, second).equals(expected);
    }

    @Test
    @DisplayName("test subtraction")
    void testSubtraction() {
        Integer first = 12;
        Integer second = 4;
        Integer expected = 8;

        assert calculator.subtraction(first, second).equals(expected);
    }

    @Test
    @DisplayName("test multiply")
    void testMultiply() {
        Integer first = 5;
        Integer second = 3;
        Integer expected = 15;

        assert calculator.multiply(first, second).equals(expected);
    }

    @Test
    @DisplayName("test division")
    void testDivision() {
        Integer first = 30;
        Integer second = 6;
        Integer expected = 5;

        assert calculator.division(first, second).equals(expected);
    }

    //HW
    @Test
    @DisplayName("test power")
    void testPower() {
        Integer number = 5;
        Integer expValue = 2;
        Integer expected = 25;

        assert calculator.power(number, expValue).equals(expected);
    }

    @Test
    @DisplayName("test root of a number")
    void testRootOfNumber() {
        Integer value = 36;
        Integer expected = 6;

        assert calculator.rootOfNumber(value).equals(expected);
    }

}
