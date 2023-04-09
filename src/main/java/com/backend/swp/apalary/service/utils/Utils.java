package com.backend.swp.apalary.service.utils;

public class Utils {
    private static final int MONEY_IN_TAX_DECREASE_BY_SELF = 11000000;
    private static final int MONEY_INT_TAX_DECREASE_BY_DEPENDENT = 4400000;
    public static String generateIdWithPrefix(String maxExistId) {

        String result = maxExistId.substring(0, 2);
        result = result.toUpperCase();
        int number= Integer.parseInt(maxExistId.substring(2));
        result += String.format("%04d", number + 1);
        return result;
    }

    public static Integer calculateAssurances(int base) {
        int baseCalculate = Math.min(base, 29800000);
        return (int) (baseCalculate * 0.01 * 10.5);
    }

    public static Integer calculateTax(int base, int numberOfDependent) {
        int assurances = calculateAssurances(base);
        int actualBaseInTax = Math.max(0, base - MONEY_IN_TAX_DECREASE_BY_SELF - numberOfDependent * MONEY_INT_TAX_DECREASE_BY_DEPENDENT - assurances);
        int tax;
        int moneyDecreaseOnTax = 0;
        if (actualBaseInTax <= 5000000) {
            tax = 5;
        }
        else if (actualBaseInTax <= 10000000) {
            tax = 10;
        }
        else if (actualBaseInTax <= 18000000) {
            tax = 15;
        }
        else if (actualBaseInTax <= 32000000) {
            tax = 20;
        } else if (actualBaseInTax <= 52000000) {
            tax = 25;
        } else if (actualBaseInTax <= 80000000) {
            tax = 30;
        } else {
            tax = 35;
        }
        moneyDecreaseOnTax = moneyDecreaseBaseOnTax(tax);
        System.out.println(tax);
        System.out.println(actualBaseInTax);
        return (int) (tax * 0.01 * actualBaseInTax) - moneyDecreaseOnTax;
    }

    private static int moneyDecreaseBaseOnTax(int tax) {
        double money;
        if (tax == 5) {
            money = 0;
        } else if (tax == 10) {
            money = 0.25;

        } else if (tax == 15) {
            money = 0.75;

        } else if (tax == 20) {
            money = 1.65;

        } else if (tax == 25) {
            money = 3.25;

        } else if (tax == 30) {
            money = 5.85;

        } else {
            money = 9.85;
        }
        return (int) (money * 1000000);
    }
}
