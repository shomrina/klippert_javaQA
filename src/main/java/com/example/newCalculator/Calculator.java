package com.example.newCalculator;

public class Calculator {
    public Integer sum(Integer val1, Integer val2) {
        return val1 + val2;
    }

    public Integer subtraction(Integer val1, Integer val2) {
        return val1 - val2;
    }

    public Integer multiply(Integer val1, Integer val2) {
        return val1 * val2;
    }

    public Integer division(Integer val1, Integer val2) {
        return val1 / val2;
    }

    //HW
    public Integer power(Integer number, Integer powValue) {
        return (int) Math.pow(number, powValue);
    }

    public Integer rootOfNumber(Integer val1) {
        return (int) Math.sqrt(val1);
    }
}
